import { Component, OnInit } from '@angular/core';
import { MaterialModule } from '../../../../material/material.module';
import { ParsedData } from './parsed.data';
import { RssReaderService } from '../rss.reader.service';

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
      this.rssReader.getRssFeed().subscribe(returnedData => {
        this.rssItems = returnedData;
      })
  }
}
