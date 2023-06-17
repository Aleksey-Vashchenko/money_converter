package com.vashchenko.money_converter.Parsers;

import com.vashchenko.money_converter.MainSceneController;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

public class BSBParser extends Parser {
    @Override
    public void getaRates() throws IOException, CloneNotSupportedException {
        String url= "https://www.bsb.by/private/currency/all-rates/currency-exchange/";
        Document web_page = Jsoup.connect(url).get();
        Elements curAmount = web_page.select("td[class=_1 first]");
        Elements curcours = web_page.select("td[class=_4 last]");
        Currency currency = new Currency("");
        for(int i=0;i<curAmount.size();i++){
            currency.setName(curAmount.get(i).text().split(" ")[1].split("/")[0]);
            if(MainSceneController.curInfo.contains(currency)&&curAmount.get(i).text().split(" ")[1].split("/")[1].equals("BYN")){
                currency.setCourse(Double.valueOf(curcours.get(i).text().replace(',','.'))/
                        Integer.valueOf(curAmount.get(i).text().split(" ")[0]));
                MainSceneController.curInfo.set(MainSceneController.curInfo.indexOf(currency),(Currency) currency.clone());
            }
        }
    }
}
