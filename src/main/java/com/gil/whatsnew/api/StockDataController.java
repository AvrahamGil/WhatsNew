package com.gil.whatsnew.api;

import com.gil.whatsnew.exceptions.ApplicationException;
import com.gil.whatsnew.logic.StockDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rest/api/stock")
public class StockDataController {

    @Autowired
    private StockDataService stockDataService;

    @RequestMapping(value="/data" , method = RequestMethod.GET)
    public ResponseEntity<String> getStockData() throws ApplicationException {
        return stockDataService.getStockData();

    }
}
