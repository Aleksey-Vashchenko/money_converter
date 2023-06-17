package com.vashchenko.money_converter.Parsers;

import com.vashchenko.money_converter.MainSceneController;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public  class Parser {
    public Parser() {
    }
     public void getRates(){

     }

    public  void getaRates() throws IOException,CloneNotSupportedException{};

    public void getSavedRates() throws IOException,CloneNotSupportedException{
        FileReader inputStream = new FileReader(new File("src/main/java/saved.txt"));
        BufferedReader bufferedReader = new BufferedReader(inputStream);
        while (bufferedReader.ready()){
            String[] stringCurrency = bufferedReader.readLine().split(" ");
            Currency currency = new Currency();
            currency.setName(stringCurrency[0]);
            currency.setCourse(Double.valueOf(stringCurrency[1]));
            MainSceneController.curInfo.set(MainSceneController.curInfo.indexOf(currency),(Currency) currency.clone());
        }
    }
}
