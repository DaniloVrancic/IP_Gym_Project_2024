import { Component, OnInit } from '@angular/core';
import { MaterialModule } from '../../../../material/material.module';
import { ParsedData } from './parsed.data';
import { RssReaderService } from '../rss.reader.service';
import { environment } from '../../../../../environments/environment';

@Component({
  selector: 'app-rss-news',
  standalone: true,
  imports: [MaterialModule],
  templateUrl: './rss.news.component.html',
  styleUrl: './rss.news.component.css',
  providers: [RssReaderService]
})
export class RssNewsComponent implements OnInit{

  rssItems: ParsedData[] | null = null;

  constructor(private rssReader: RssReaderService) {  }

  ngOnInit(): void {
    const jsonString = sessionStorage.getItem(environment.rssFeedKeyString)
    if(jsonString)
    {
      const parsedData = JSON.parse(jsonString);
      this.rssItems = parsedData; //If there is some data existing, then parse that data and assign it to rssItems
      
    }
    else
    {
      this.rssReader.getRssFeed().subscribe(returnedData => {
        this.rssItems = returnedData;
        sessionStorage.setItem(environment.rssFeedKeyString, JSON.stringify(returnedData));
      }); //if the data didn't exist in sessionStorage, then do a Get of the data, and cache it in sessionStorage
    }
  }
}
