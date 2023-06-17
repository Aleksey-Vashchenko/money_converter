module com.vashchenko.money_converter {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.jsoup;

    opens com.vashchenko.money_converter to javafx.fxml;
    exports com.vashchenko.money_converter;
    exports com.vashchenko.money_converter.Parsers;
    opens com.vashchenko.money_converter.Parsers to javafx.fxml;
}