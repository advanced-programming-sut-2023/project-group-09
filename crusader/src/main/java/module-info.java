module crusader {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    requires org.apache.commons.codec;
    requires com.google.gson;
    requires java.desktop;


    exports view;
    opens view to javafx.fxml;
    exports view.menus;
    opens view.menus to javafx.fxml;
    exports view.menus.profile;
    opens view.menus.profile to javafx.fxml;
    exports model;
    opens model to com.google.gson;

    exports enumeration;
    opens enumeration to com.google.gson;

    exports enumeration.dictionary;
    opens enumeration.dictionary to com.google.gson;

    exports model.goods;
    opens model.goods to com.google.gson;

    exports server;
    opens server to com.google.gson;

    exports model.game;
    opens model.game to com.google.gson;

    exports model.activity;
    opens model.activity to com.google.gson;

    exports model.tools;
    opens model.tools to com.google.gson;

    exports model.buildinghandler;
    opens model. buildinghandler to com.google.gson;

    exports model.human;
    opens model.human to com.google.gson;

    exports model.human.civilian;
    opens model.human.civilian to com.google.gson;

    exports model.human.military;
    opens model.human.military to com.google.gson;

    exports model.building;
    opens model.building to com.google.gson;

    exports model.building.castlebuildings;
    opens model.building.castlebuildings to com.google.gson;

    exports model.building.producerbuildings;
    opens model.building.producerbuildings to com.google.gson;

    exports model.building.storagebuildings;
    opens model.building.storagebuildings to com.google.gson;
}