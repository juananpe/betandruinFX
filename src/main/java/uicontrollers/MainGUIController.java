package uicontrollers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import ui.MainGUI;

public class MainGUIController implements Controller{

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
    void initialize() {

    }

    @Override
    public void setMainApp(MainGUI mainGUI) {
        this.mainGUI = mainGUI;
    }
}
