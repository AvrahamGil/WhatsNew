package com.gil.whatsnew.logic;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gil.whatsnew.exceptions.ApplicationException;
import com.gil.whatsnew.utils.JsonUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;


@Service
public class StockDataService {

    @Value("${finnhub.api.key}")
    private String apiKey;

    @Value("${stock.response.timeout}")
    private String time;

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public StockDataService() {
        this.restTemplate = new RestTemplate();
        this.objectMapper = new ObjectMapper();
    }

    private static final String API_URL = "https://finnhub.io/api/v1/quote";

    public ResponseEntity<String> getStockData()  {
        String symbol = "AAPL"; // Example: Apple Inc.
        String url = API_URL + "?symbol=" + symbol + "&token=" + apiKey;

        return getStockResponse(url);
    }


    private ResponseEntity<String> getStockResponse(String url)  {
        try {
            Date currentDate = new Date();
            RestTemplate restTemplate = new RestTemplate();
            Path filePath = Paths.get("C:", "Users", "Gil", "Desktop", "MyProject", "WhatsNew", "backend", "whatsnew", "target", "classes", "logs", "Info", "stockResponse.txt");

            if(System.getProperty("stock.response.timeout") == null) {
                long minutes = currentDate.getTime() / (60 * 1000);
                System.setProperty("stock.response.timeout",String.valueOf(minutes));
                ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
                JsonUtils.writeToAFile(filePath.toString(),response);
                return response;
            }

            String minutesProperty = System.getProperty("stock.response.timeout");
            long minutesProp = Long.parseLong(minutesProperty);

            long currentMinutes = currentDate.getTime() / (60 * 1000);
            long minutesPassed = currentMinutes - minutesProp;

            if(minutesPassed > 30) {
                ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
                JsonUtils.writeIntoJsonFile("infoLog", response, filePath.toString());
                return response;
            } else {

                return JsonUtils.readFromAFile(filePath.toString());
            }


        }catch(IOException  | ApplicationException e) {
            e.printStackTrace();
        }
        return null;
    }
}
