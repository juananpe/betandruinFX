package uicontrollers;

import java.net.URL;
import java.util.HashMap;
import java.util.Locale;
import java.util.ResourceBundle;

import businessLogic.BlFacade;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import ui.MainGUI;

public class MainGUIController implements Controller{

    @FXML
    private Label selectOptionLbl;

    @FXML
    private Button browseQuestionsBtn;

    @FXML
    private Button createQuestionBtn;


    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    private MainGUI mainGUI;

    @FXML
    void browseQuestions(ActionEvent event) {
        mainGUI.showBrowseQuestions();
    }

    @FXML
    void createQuestion(ActionEvent event) {
        mainGUI.showCreateQuestion();
    }

    @FXML
    private RadioButton radioEus;

    @FXML
    private ToggleGroup langGroup;

    @FXML
    private RadioButton radioEs;

    @FXML
    private RadioButton radioEn;

    @FXML
    void changeLang(ActionEvent event) {
        HashMap<String, String> langs = new HashMap<>(); // English, Castellano, Euskara
        langs.put("English", "en");
        langs.put("Castellano", "es");
        langs.put("Euskara", "eus");

        String selectedLang = ((RadioButton)event.getSource()).getText();

        Locale.setDefault(new Locale(langs.get(selectedLang)));
        System.out.println("Locale: " + Locale.getDefault());
        redraw();
    }

    private void redraw() {
        selectOptionLbl.setText(ResourceBundle.getBundle("Etiquetas").
            getString("SelectUseCase"));
        browseQuestionsBtn.setText(ResourceBundle.getBundle("Etiquetas").
            getString("BrowseQuestions"));
        createQuestionBtn.setText(ResourceBundle.getBundle("Etiquetas").
            getString("CreateQuestion"));

        if (selectOptionLbl.getScene()!=null){
            Stage stage = ((Stage)selectOptionLbl.getScene().getWindow());
            stage.setTitle(ResourceBundle.getBundle("Etiquetas").getString("MainTitle"));
        }

    }

    @FXML
    void initialize() {
      redraw();
    }

    @Override
    public void setMainApp(MainGUI mainGUI) {
        this.mainGUI = mainGUI;
    }
}
