package org.unibl.etf.onlinefitnessmanager.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/rss")
public class RSSReaderController {

    @GetMapping("/get-feed")
    public String getRssFeed()
    {
        String rssFeedUrl = "https://feeds.feedburner.com/AceFitFacts";

        RestTemplate restTemplate = new RestTemplate();

        String rssFeed = restTemplate.getForObject(rssFeedUrl, String.class);
        return rssFeed;
    }
}
