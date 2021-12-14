package io.bigbear.covidTracker.services;

// Custom imports for the Commons CSV reader library
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.csv.CSVFormat;


import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

// class that will fetch the data from the raw JSON data from GitHub Repo.
@Service
public class CoronaVirusDataServices {

    private static String virusDataURL = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_confirmed_global.csv";

    @PostConstruct
    @Scheduled(cron = "* * 1 * * *")
    public void fetchVirusData() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(virusDataURL))
                .build();
        // Return the response as a String that gets stored in local variable
        HttpResponse<String> httpResponse = client.send(request, HttpResponse.BodyHandlers.ofString());

        // Needed to convert the String response to Reader format for the commons method
        StringReader csvBodyReader = new StringReader(httpResponse.body());

        // Below I have the library from Commons added into my Pom file
        Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(csvBodyReader);
        for (CSVRecord record : records) {
            String state = record.get("Province/State");
            System.out.print(state);

        }
    }
}
