package io.bigbear.covidTracker.services;


import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

// class that will fetch the data from the raw JSON data from GitHub Repo.
@Service
public class CoronaVirusDataServices {

    private static String virusDataURL = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_confirmed_global.csv";

    @PostConstruct
    public void fetchVirusData() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(virusDataURL))
                .build();
        // Return the response as a String that gets stored in local variable
        HttpResponse<String> httpResponse = client.send(request, HttpResponse.BodyHandlers.ofString());
        // Print out resposne from GH repo to ensure we receive data
        System.out.println(httpResponse.body());

        
        Iterable<CSVRecord> records = CSVFormat.RFC4180.withFirstRecordAsHeader().parse(in);
        for (CSVRecord record : records) {
            String id = record.get("ID");
            String customerNo = record.get("CustomerNo");
            String name = record.get("Name");
        }
    }


}
