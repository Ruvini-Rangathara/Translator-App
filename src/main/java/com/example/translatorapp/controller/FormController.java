package com.example.translatorapp.controller;

import com.example.translatorapp.Language;
import com.google.cloud.translate.Translate;
import com.google.cloud.translate.TranslateOptions;
import com.google.cloud.translate.Translation;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;

import java.net.URL;
import java.util.ResourceBundle;

public class FormController implements Initializable {

    @FXML
    private ComboBox<String> comboSource;

    @FXML
    private ComboBox<String> comboTranslator;

    @FXML
    private TextArea txtEnterText;

    @FXML
    private TextArea txtTranslationText;

    String sourceLanguage;
    Translate translate;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        translate = TranslateOptions.newBuilder().setApiKey("AIzaSyDGKNpBerPd8RiVu2gCrNDDIMKXOXyXK_M").build().getService();

          txtEnterText.setStyle("-fx-control-inner-background: #c4c3d0;");
          txtTranslationText.setStyle("-fx-control-inner-background: #c4c3d0;");
          comboSource.setStyle("-fx-control-inner-background: #c4c3d0;");

        // Define the list of language names
        String[] languages = {
                "English", "French", "Spanish", "German", "Italian", "Portuguese", "Dutch", "Russian",
                "Chinese (Simplified)", "Japanese", "Korean", "Arabic", "Hindi", "Turkish", "Greek",
                "Swedish", "Norwegian", "Danish", "Finnish", "Polish", "Romanian", "Thai", "Vietnamese",
                "Malay", "Indonesian", "Hebrew", "Ukrainian", "Czech", "Hungarian", "Bulgarian", "Croatian",
                "Estonian", "Filipino", "Icelandic", "Latvian", "Lithuanian", "Macedonian", "Maltese",
                "Persian", "Serbian", "Slovak", "Slovenian", "Swahili", "Welsh", "Yiddish", "Urdu",
                "Bengali", "Tamil", "Telugu"
        };

        // Add the list of languages to the ComboBox
        comboSource.setItems(FXCollections.observableArrayList(languages));
        comboTranslator.setItems(FXCollections.observableArrayList(languages));

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

