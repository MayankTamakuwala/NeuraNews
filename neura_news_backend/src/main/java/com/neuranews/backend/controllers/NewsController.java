package com.neuranews.backend.controllers;

import com.jzhangdeveloper.newsapi.net.NewsAPI;
import com.jzhangdeveloper.newsapi.net.NewsAPIClient;
import com.jzhangdeveloper.newsapi.params.TopHeadlinesParams;
import com.neuranews.backend.models.ResponseData;
import com.neuranews.backend.models.ResponseError;
import com.neuranews.backend.models.ResponseObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class NewsController {

    private final NewsAPIClient client;

    public NewsController(@Value("${news.key}") String apiKey){
        this.client = NewsAPI.newClientBuilder().setApiKey(apiKey).build();
    }

    @GetMapping("/test")
    @ResponseBody
    public ResponseObject test() {
        return new ResponseData("Mayank");
    }

    @GetMapping("/news")
    @ResponseBody
    public ResponseObject getNews() throws IOException, InterruptedException {
        try {
            Map<String, String> topHeadlineParams = TopHeadlinesParams.newBuilder()
                    .setCountry("us")
                    .setPageSize(10)
                    .build();

//            Map<String, String> everythingParams = EverythingParams.newBuilder()
//                    .setPageSize(10)
//                    .setSearchQuery("tech")
//                    .build();

            return new ResponseData(client.getTopHeadlines(topHeadlineParams).getBody());
//            return new ResponseData(client.getEverything(everythingParams).getBody());
        } catch (IOException | InterruptedException e) {
            return new ResponseError(e.getMessage());
        }
    }

}
