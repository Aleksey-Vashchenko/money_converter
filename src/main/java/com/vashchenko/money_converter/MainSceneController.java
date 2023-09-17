package com.vashchenko.money_converter;
import com.vashchenko.money_converter.Parsers.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
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
    private ComboBoxListener formListener;

    private ObservableList<String> banks = FXCollections.observableArrayList("BNB-Bank","BSB-Bank",
            "SBER-Bank");


    @FXML
    private ComboBox<String> chooseBox;


    @FXML
    private Text warningText;

    @FXML
    private Text savedCur;
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
    private Text UAHText;
    @FXML
    void keyPressed(KeyEvent event) {
        TextField field = (TextField) event.getSource();
        updateFields(field);
    }


    @FXML
    void initialize(){
        UAH.setVisible(false);
        UAHText.setVisible(false);
        chooseBox.setItems(banks);
        formListener = new ComboBoxListener<>(chooseBox);
        Currency byn = new Currency("BYN");
        byn.setCourse(1);
        MainSceneController.curInfo.add(byn);
        warningText.setVisible(false);
        fields.add(BYN);
        fields.add(EUR);
        fields.add(RUB);
        fields.add(UAH);
        fields.add(USD);
        chooseBox.setOnAction(event -> {
            String bank = chooseBox.getValue();
            for (TextField textField : fields) {
                textField.setText("");
            }
            switch (bank){
                case "SBER-Bank" -> {
                    UAH.setVisible(false);
                    UAHText.setVisible(false);
                    parser = new SBERParser();
                    break;
                }
                case "BNB-Bank" -> {
                    parser=new BNBParser();
                    UAHText.setVisible(true);
                    UAH.setVisible(true);
                    break;
                }
                case "BSB-Bank" -> {
                    parser = new BSBParser();
                    UAHText.setVisible(true);
                    UAH.setVisible(true);
                    break;
                }
            }
            try{
                parser.getaRates();
                savedCur.setVisible(false);
            }
            catch (IOException | CloneNotSupportedException e1){
                Alert infoAlert = new Alert(Alert.AlertType.INFORMATION);
                infoAlert.setTitle("Ошибка парсинга");
                infoAlert.setHeaderText("Невозможно выполнить парсинг данных с сайта данного банка");
                infoAlert.setContentText("Будут использоваться сохраненные значения курсов валют");
                infoAlert.show();
                try {
                    parser.getSavedRates();
                    String answer="";
                    for(int i=0;i<curInfo.size();i++) {
                        if (!curInfo.get(i).equals(new Currency("BYN")))
                            answer = answer + curInfo.get(i).toString() + "   ";
                    }
                    savedCur.setText(answer);
                    savedCur.setVisible(true);
                }
                catch (IOException | CloneNotSupportedException e){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Приложение полностью накрылось");
                    alert.setTitle("Непонятно как ты это сделал");
                    alert.show();
                }
            }
        });
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
        warningText.setText("Проверьте правильность ввода");
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
                    textField.setText(decimalFormat.format(Double.valueOf(newValue) * cur.getCourse() / secCur.getCourse()).replace(',','.'));
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




