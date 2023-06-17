package com.vashchenko.money_converter;
import com.vashchenko.money_converter.Parsers.BNBParser;
import com.vashchenko.money_converter.Parsers.Currency;
import com.vashchenko.money_converter.Parsers.Parser;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;

public class MainSceneController {
    private ArrayList<TextField> fields = new ArrayList<>();
    private DecimalFormat decimalFormat = new DecimalFormat("#.##");
    public static ArrayList<Currency> curInfo = new ArrayList<>
            (Arrays.asList(new Currency("USD"),new Currency("EUR"),new Currency("RUB"),new Currency("UAH")));
    private Parser parser;
    private boolean isInput=true;

    @FXML
    private Text warningText;
    @FXML
    private TextField BYN;

    @FXML
    private TextField EUR;

    @FXML
    private TextField RUB;

    @FXML
    private TextField UAH;

    @FXML
    private TextField USD;

    @FXML
    void keyPressed(KeyEvent event) {
        TextField field = (TextField) event.getSource();
        updateFields(field);
    }

    @FXML
    void initialize() throws CloneNotSupportedException, IOException {
        BNBParser parser = new BNBParser("BNB");
        try{
            parser.getaRates();
        }
        catch (Exception e){
            //view of exception
            System.out.println("exeption");
            parser.getSavedRates();
        }
        Currency byn = new Currency("BYN");
        byn.setCourse(1);
        MainSceneController.curInfo.add(byn);
        warningText.setVisible(false);
        fields.add(BYN);
        fields.add(EUR);
        fields.add(RUB);
        fields.add(UAH);
        fields.add(USD);
    }


    private int isCorrectInput(String text){
        if(text.length()>8){
            warningText.setText("Слишком большое значение");
            return -1;
        }
        try {
             Double.valueOf(text);
             return 1;
        }
        catch (NumberFormatException e){
            if (text.isEmpty()){
                return 0;
                }
            }
        warningText.setText("Проверьте правильноть ввода");
        return -1;
    }

    private void updateFields(TextField field){
        String newValue = field.getText();
        int valid = isCorrectInput(newValue);
        if (valid > 0) {
            warningText.setVisible(false);
            Currency cur = curInfo.get(curInfo.indexOf(new Currency(field.getId())));
            for (TextField textField : fields) {
                if(!textField.getId().equals(field.getId())){
                    int index = curInfo.indexOf(new Currency(textField.getId()));
                    Currency secCur = curInfo.get(index);
                    textField.setText(decimalFormat.format(Double.valueOf(newValue) * cur.getCourse() / secCur.getCourse()));
                }
            }
            return;
        } else if (valid == 0) {
            warningText.setVisible(false);
            for (TextField textField : fields) {
                textField.setText("");
            }
        } else {
            warningText.setVisible(true);
        }
    }
}




