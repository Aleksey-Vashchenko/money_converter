package com.vashchenko.money_converter.Parsers;

import com.vashchenko.money_converter.MainSceneController;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

public class SBERParser extends Parser{
    @Override
    public void getaRates() throws IOException,CloneNotSupportedException {
        String url= "https://www.sber-bank.by/page/currency-exchange-cards";
        Document web_page = Jsoup.connect(url).get();
        Elements curAmount = web_page.select("span[class=nameOfCurrency]");
        Elements curcours = web_page.select("div[class=currency]");
        Currency currency= new Currency("EUR");
        currency.setCourse(Double.valueOf(curcours.get(0).text().substring(6).replace(',','.')));
        MainSceneController.curInfo.set(MainSceneController.curInfo.indexOf(currency),(Currency) currency.clone());
        currency.setName("RUB");
        currency.setCourse(Double.valueOf(curcours.get(1).text().substring(6).replace(',','.'))/100);
        MainSceneController.curInfo.set(MainSceneController.curInfo.indexOf(currency),(Currency) currency.clone());
        currency.setName("USD");
        currency.setCourse(Double.valueOf(curcours.get(2).text().substring(6).replace(',','.')));
        MainSceneController.curInfo.set(MainSceneController.curInfo.indexOf(currency),(Currency) currency.clone());
    }
}
