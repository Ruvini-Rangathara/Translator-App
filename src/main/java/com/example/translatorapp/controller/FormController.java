package com.example.translatorapp.controller;

import com.example.translatorapp.Language;
import com.example.translatorapp.util.Config;
import com.google.cloud.translate.Translate;
import com.google.cloud.translate.TranslateOptions;
import com.google.cloud.translate.Translation;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FormController implements Initializable {
    private String sourceLanguage;
    private  Translate translate;

    //Define the list of language names
    private final String[] languages = {
            "English", "French", "Spanish", "German", "Italian", "Portuguese", "Dutch", "Russian",
            "Chinese (Simplified)", "Japanese", "Korean", "Arabic", "Hindi", "Turkish", "Greek",
            "Swedish", "Norwegian", "Danish", "Finnish", "Polish", "Romanian", "Thai", "Vietnamese",
            "Malay", "Indonesian", "Hebrew", "Ukrainian", "Czech", "Hungarian", "Bulgarian", "Croatian",
            "Estonian", "Filipino", "Icelandic", "Latvian", "Lithuanian", "Macedonian", "Maltese", "Persian",
            "Serbian", "Slovak", "Slovenian", "Swahili", "Welsh", "Yiddish", "Urdu", "Bengali", "Tamil", "Telugu"
    };

    @FXML
    private AnchorPane context;
    @FXML
    private ComboBox<String> comboSource;
    @FXML
    private ComboBox<String> comboTranslator;
    @FXML
    private TextArea txtEnterText;
    @FXML
    private TextArea txtTranslationText;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            translate = TranslateOptions.newBuilder().setApiKey(Config.getInstance().getKey()).build().getService();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        txtEnterText.setStyle("-fx-control-inner-background: #dbd7d2; -fx-background-insets: 0;-fx-background-radius: 0;-fx-border-width: 0;");
        txtTranslationText.setStyle("-fx-control-inner-background: #b2beb5; -fx-background-insets: 0;-fx-background-radius: 0;-fx-border-width: 0;");
        comboTranslator.setStyle("-fx-background-color: #c4c3d0; -fx-background-insets: 0;-fx-background-radius: 0;-fx-border-width: 0;");
        comboSource.setStyle("-fx-background-color: #c4c3d0; -fx-background-insets: 0;-fx-background-radius: 0;-fx-border-width: 0;");

        // Add the list of languages to the ComboBox
        List<String> collect = Arrays.stream(languages).toList().stream().sorted().toList();
        comboSource.setItems(FXCollections.observableArrayList(collect));
        comboTranslator.setItems(FXCollections.observableArrayList(collect));

    }

    @FXML
    void comboSourceOnAction(ActionEvent event) {
        String sourceLanguageCode = Language.getLanguageCode(String.valueOf(comboSource.getValue()));
        sourceLanguage = translate.detect(sourceLanguageCode).getLanguage();
    }

    @FXML
    void comboTranslatorOnAction(ActionEvent event) {
        String translateLanguageCode = Language.getLanguageCode(String.valueOf(comboTranslator.getValue()));
        Translation translation = translate.translate(
                txtEnterText.getText(),
                com.google.cloud.translate.Translate.TranslateOption.sourceLanguage(sourceLanguage),
                com.google.cloud.translate.Translate.TranslateOption.targetLanguage(translateLanguageCode) // Corrected here
        );
        txtTranslationText.setText(translation.getTranslatedText());
    }

    @FXML
    void btnExitOnAction(ActionEvent event) {
        System.exit(0);
    }

}

