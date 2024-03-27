import { Injectable } from '@angular/core';
import { DomSanitizer } from '@angular/platform-browser';
import { map } from 'rxjs/operators';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { ParsedData } from './rss.news/parsed.data';
import { environment } from '../../../../environments/environment';


@Injectable({
  providedIn: 'root'
})
export class RssReaderService {

  private rssURL = "https://feeds.feedburner.com/AceFitFacts";

  constructor(private http: HttpClient, private sanitizer: DomSanitizer) { }

  getRssFeed(): Observable<any> {
    return this.http.get(environment.apiBaseUrl + '/rss/get-feed', {responseType: 'text'}).pipe(
      map((xmlData: string) => {
        
        const parsedData = [];
        const parser = new DOMParser();
        const xmlDoc = parser.parseFromString(xmlData, 'application/xml');
        
        const items = xmlDoc.getElementsByTagName('item');
        
        for(let i = 0; i < items.length; ++i)
        {
          const item = items[i];
          
          const category = (item.getElementsByTagName('category') as HTMLCollection)[0].textContent as string;
          const title = (item.getElementsByTagName('title') as HTMLCollection)[0].textContent as string;
          const description = (item.getElementsByTagName('description') as HTMLCollection)[0].textContent as string;
          const link = (item.getElementsByTagName('link') as HTMLCollection)[0].textContent as string;
          let parsedItem: ParsedData = {category, title, description, link};
          
          parsedData.push(parsedItem);
        }
        return parsedData;
      })
    );
  }
}
