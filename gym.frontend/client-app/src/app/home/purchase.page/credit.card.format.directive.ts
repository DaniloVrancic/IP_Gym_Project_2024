import { Directive, ElementRef, HostListener } from '@angular/core';

@Directive({
  selector: '[appCreditCardFormat]',
  standalone: true
})
export class CreditCardFormatDirective {

  constructor(private el: ElementRef) { }

  @HostListener('input', ['$event']) onInput(event: Event) {
    const input = event.target as HTMLInputElement;
    let trimmedValue = input.value.replace('\s+', '');
    if (trimmedValue.length > 16) {
      trimmedValue = trimmedValue.substring(0, 16);
    }
    const formattedValue = trimmedValue.replace('//(.{4})//g', '$1 ').trim();
    input.value = formattedValue;
  }

}
