package com.vashchenko.money_converter.Parsers;

import com.vashchenko.money_converter.Main;
import com.vashchenko.money_converter.MainSceneController;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.*;

public class BNBParser extends Parser{

    public BNBParser(String name) {
        super(name);
    }

    public void getaRates() throws IOException,CloneNotSupportedException {
        String url= "https://www.nbrb.by/statistics/rates/ratesdaily.asp";
        Document web_page = Jsoup.connect(url).get();
        Elements curAmount = web_page.select("td[class=curAmount]");
        Elements curcours = web_page.select("td[class=curcours]");
        Currency currency = new Currency("");
        for(int i=0;i<curAmount.size();i++){
            currency.setName(curAmount.get(i).text().split(" ")[1]);
            if(MainSceneController.curInfo.contains(currency)){
                currency.setCourse(Double.valueOf(curcours.get(i).text().replace(',','.'))/
                        Integer.valueOf(curAmount.get(i).text().split(" ")[0]));
                MainSceneController.curInfo.set(MainSceneController.curInfo.indexOf(currency),(Currency) currency.clone());
            }
        }
    }
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
