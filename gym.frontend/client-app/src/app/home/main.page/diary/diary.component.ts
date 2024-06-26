import { AfterViewInit, Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { MaterialModule } from '../../../material/material.module';
import { UserService } from '../../../user.service';
import { FormBuilder, FormControl, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { DateAdapter, provideNativeDateAdapter } from '@angular/material/core';
import { MatDatepickerControl, MatDatepickerPanel } from '@angular/material/datepicker';
import { environment } from '../../../../environments/environment';
import { FitnessProgramTypeService } from '../fitness-program-type.service';
import { FitnessProgramType } from '../fitness-program-type';
import { CompletedExercise } from './completed.exercise';
import { CompletedExerciseService } from './completed.exercise.service';
import { Chart, ChartData, Point, registerables, scales } from 'chart.js/auto';
import { BaseChartDirective, provideCharts, withDefaultRegisterables } from 'ng2-charts';
import { jsPDF } from 'jspdf';
import html2canvas from 'html2canvas';

@Component({
  selector: 'app-diary',
  standalone: true,
  imports: [MaterialModule, FormsModule, ReactiveFormsModule, BaseChartDirective],
  templateUrl: './diary.component.html',
  styleUrl: './diary.component.css',
  providers: [UserService, FitnessProgramTypeService, CompletedExerciseService, provideNativeDateAdapter(), provideCharts(withDefaultRegisterables())]
})
export class DiaryComponent implements OnInit, AfterViewInit{

  dateControl = new FormControl();
  private _locale: string;
  controlGroup: FormGroup;
  apiUrl: string;
  fitnessProgramTypes: FitnessProgramType[] = [];
  public completedExerciseForUser: CompletedExercise[] = [];
  public filteredExerciseForUser: CompletedExercise[] = [];

  weightLossChartData: ChartData<"line",(number|Point|null)[],unknown>|undefined;
  timeSpentChartData: ChartData<"line",(number|Point|null)[],unknown>|undefined;
  numberOfSelectedDays = 7; // will be used to plot charts based on how many days are selected

  @ViewChild('pdfContainer', {static: false}) el!: ElementRef;


  constructor(private userService: UserService, private dateAdapter: DateAdapter<Date>, 
    private _fb: FormBuilder, private fitnessProgramTypeService: FitnessProgramTypeService,
    private completedExerciseService: CompletedExerciseService)
  {
    this._locale = "en-EN"
    this.dateAdapter.setLocale(this._locale);
    this.apiUrl = environment.apiBaseUrl;

    this.controlGroup = this._fb.group({ //grouping all the controls on this page
      completedCategory: [``, [Validators.required]],
      completedDuration: [``, [Validators.required]],
      completedIntensity: [``, [Validators.required, Validators.min(1), Validators.max(3)]],
      completedWeightLoss: [``,[Validators.required]],
      completedDescription: [``,[]],
      dateControl: [``,[]] //unused in code but good to have grouped
    });
  }

  ngOnInit(): void {
      this.fitnessProgramTypeService.getAllFitnessProgramTypes().subscribe(response => {
        this.fitnessProgramTypes = response;
      })
      this.completedExerciseService.getCompletedExerciseForUserId(this.userService.getCurrentUser()?.id as number).subscribe(response => {
        this.completedExerciseForUser = response;
        this.filteredExerciseForUser = [...this.completedExerciseForUser];
        this.drawCharts();
      })
  }

  // Here is an improved version of the 'ngAfterViewInit' method:

  ngAfterViewInit(): void {
   
  }

  dateInputMethod(event: any)
  {
    if (this.dateControl.value) {
      const selectedDate = new Date(this.dateControl.value);
      const year = selectedDate.getFullYear();
      const month = (selectedDate.getMonth() + 1).toString().padStart(2, '0'); // Months are zero-based
      const day = selectedDate.getDate().toString().padStart(2, '0');
      const formattedDate = `${year}-${month}-${day}`;
    }
  }

  formatDate(dateString: string): string
  {
    let formattedDate;
    if (dateString) {
      const selectedDate = new Date(dateString);
      const year = selectedDate.getFullYear();
      const month = (selectedDate.getMonth() + 1).toString().padStart(2, '0'); // Months are zero-based
      const day = selectedDate.getDate().toString().padStart(2, '0');
      formattedDate = `${year}-${month}-${day}`;
    }
    else
    {
      formattedDate = '';
    }
    return formattedDate;
  }

  formatDateObject(date: Date): string
  {
    let formattedDate;
    if (date) {
      const selectedDate = date;
      const year = selectedDate.getFullYear();
      const month = (selectedDate.getMonth() + 1).toString().padStart(2, '0'); // Months are zero-based
      const day = selectedDate.getDate().toString().padStart(2, '0');
      formattedDate = `${year}-${month}-${day}`;
    }
    else
    {
      formattedDate = '';
    }
    return formattedDate;
  }

  onClick(event: any): void
  {
    let completedExerciseToAdd : CompletedExercise = {} as CompletedExercise;

    completedExerciseToAdd.type = this.controlGroup.get("completedCategory")?.value;
    completedExerciseToAdd.duration = this.controlGroup.get("completedDuration")?.value;
    completedExerciseToAdd.intensity= this.controlGroup.get("completedIntensity")?.value;
    completedExerciseToAdd.weightLoss = this.controlGroup.get("completedWeightLoss")?.value;

    completedExerciseToAdd.dayOfCompletion = this.controlGroup.get("dateControl")?.value;
    if(this.dateControl.value)
      {
        completedExerciseToAdd.dayOfCompletion = this.formatDate(this.dateControl.value);
      }
    else
    {
      completedExerciseToAdd.dayOfCompletion = "";
    }

    completedExerciseToAdd.resultDescription = this.controlGroup.get("completedDescription")?.value;
    if(!completedExerciseToAdd.resultDescription)
      {
        completedExerciseToAdd.resultDescription = "";
      }
    
    completedExerciseToAdd.userId = this.userService.getCurrentUser()?.id as number;
    this.completedExerciseService.addCompletedExercise(completedExerciseToAdd).subscribe(result => {
      this.completedExerciseForUser.push(result);
      this.onClickAllTime();
      alert("Successfully added exercise to diary!");
    });

  }

  onClick1Week(event: any)
  {
    // Get today's date
    const today = new Date();

    // Get the date one week ago
    const oneWeekAgo = new Date();
    oneWeekAgo.setDate(oneWeekAgo.getDate() - 7);

  // Filter completedExercises based on dayOfCompletion within today's date and one week before
    this.filteredExerciseForUser = this.completedExerciseForUser.filter(exercise => {
    const exerciseDate = new Date(exercise.dayOfCompletion);
    this.numberOfSelectedDays = 7;
    return exerciseDate >= oneWeekAgo && exerciseDate <= today;
  });
  this.drawCharts();
  }
  onClick1Month(event: any)
  {
    // Get today's date
    const today = new Date();

    // Get the date one month ago
    const oneMonthAgo = new Date();
    oneMonthAgo.setMonth(oneMonthAgo.getMonth() - 1);

  // Filter completedExercises based on dayOfCompletion within today's date and one month ago
    this.filteredExerciseForUser = this.completedExerciseForUser.filter(exercise => {
    const exerciseDate = new Date(exercise.dayOfCompletion);
    this.numberOfSelectedDays = 30;
    return exerciseDate >= oneMonthAgo && exerciseDate <= today;
  });
  
  this.drawCharts();
  }
  onClick1Year(event: any)
  {
    // Get today's date
    const today = new Date();
    // Get the date one year ago
    const oneYearAgo = new Date();
    oneYearAgo.setFullYear(oneYearAgo.getFullYear() - 1);

  // Filter completedExercises based on dayOfCompletion within today's date and one year ago
    this.filteredExerciseForUser = this.completedExerciseForUser.filter(exercise => {
    let exerciseDate = new Date(exercise.dayOfCompletion);
    this.numberOfSelectedDays = 366;
    return exerciseDate >= oneYearAgo && exerciseDate <= today;
});
  this.drawCharts();
  }
  onClickAllTime()
  {
    this.numberOfSelectedDays = -1;
    this.filteredExerciseForUser = [...this.completedExerciseForUser];
    this.drawCharts();
  }
  weightLossChartOptions: any;
  timeSpentChartOptions: any;
  public drawWeightLossChart() {
    let labelsForThisChart: string[] = this.getDateLabels();
    let valuesForThisChart: number[] = this.getWeightLossData(this.filteredExerciseForUser);

    
    this.weightLossChartData = {
      labels: [...labelsForThisChart],
      datasets: [
        {
          label: "Weight lost (kg)",
          data: [...valuesForThisChart],
          backgroundColor: 'red',
          fill: true
        }

      ]
    };
    this.weightLossChartOptions = {
      responsive: true,
      scale: 1.1
    }
  }

  public drawTimeSpentChart() {
    let labelsForThisChart: string[] = this.getDateLabels();
    let valuesForThisChart: number[] = this.getTimeSpentData(this.filteredExerciseForUser);

    
    this.timeSpentChartData = {
      labels: [...labelsForThisChart],
      datasets: [
        {
          label: "Duration (mins)",
          data: [...valuesForThisChart],
          backgroundColor: 'orange',
          fill: true
        }

      ]
    };
    this.timeSpentChartOptions = {
      responsive: true,
      scale: 1.1
    }
  }

  public getDateLabels(): string[]
  {
    const today = new Date();
    const pastInterval = new Date();
    pastInterval.setDate(pastInterval.getDate() - this.numberOfSelectedDays);

    let formattedDaysToReturn: string[] = [] as string[];
    let step: number;
    if(this.numberOfSelectedDays == -1)
      {
        step = 1;
        pastInterval.setDate(pastInterval.getDate() - 366); //default to one year
      }
    else if(this.numberOfSelectedDays <= 10)
      {
        step = 1;
      }
      else if(this.numberOfSelectedDays > 10 && this.numberOfSelectedDays <= 50)
        {
          step = 1;
        }
        else
        {
          step = 1;
        }
    for(let i = today; i >= pastInterval; i.setDate(i.getDate() - step))
      {
        formattedDaysToReturn.push(this.formatDateObject(i));
      }
    return formattedDaysToReturn;
  }

  getWeightLossData(completedExerciseArray: CompletedExercise[]): number[]
  {
    let allDateLabels: string[] = this.getDateLabels();
    let allValuesToReturn: number[] = [] as number[];
    let valueToPush = 0
    for(let dateLabel of allDateLabels)
      {
        valueToPush = 0;
        for(let j = 0; j < this.filteredExerciseForUser.length; ++j)
          {
            if(this.isSameDate(new Date(this.filteredExerciseForUser[j].dayOfCompletion),new Date(dateLabel)))
              {
                valueToPush += this.filteredExerciseForUser[j].weightLoss;
              }
          }
          allValuesToReturn.push(valueToPush);
      }
      return allValuesToReturn;
  }

  getTimeSpentData(completedExerciseArray: CompletedExercise[]): number[]
  {
    let allDateLabels: string[] = this.getDateLabels();
    let allValuesToReturn: number[] = [] as number[];
    let valueToPush = 0
    for(let dateLabel of allDateLabels)
      {
        valueToPush = 0;
        for(let j = 0; j < this.filteredExerciseForUser.length; ++j)
          {
            if(this.isSameDate(new Date(this.filteredExerciseForUser[j].dayOfCompletion),new Date(dateLabel)))
              {
                valueToPush += this.filteredExerciseForUser[j].duration;
              }
          }
          allValuesToReturn.push(valueToPush);
      }
      return allValuesToReturn;
  }

  isSameDate(date1: Date, date2: Date)
  {
  if(date1.getDate() == date2.getDate() && date1.getMonth() == date2.getMonth() && date1.getFullYear() == date2.getFullYear())
  {
    return true;
  }
  else{
    return false;
  }
}

drawCharts()
{
  this.drawWeightLossChart();
  this.drawTimeSpentChart();
}

makePdf(event: any) {
  const element = this.el.nativeElement;

  // Use html2canvas to capture the content of the div as an image
  html2canvas(document.querySelector("#pdf-container") as HTMLElement).then(canvas => {
    // Initialize jsPDF
    const pdf = new jsPDF();

    // Calculate width and height to maintain aspect ratio
    const width = pdf.internal.pageSize.getWidth();
    const height = canvas.height * (width / canvas.width);

    // Add the image captured from html2canvas to the PDF
    const imgData = canvas.toDataURL('image/png');
    pdf.addImage(imgData, 'PNG', 0, 0, width, height);

    // Save the PDF
    pdf.save("myDiary.pdf");
  });
}

}


