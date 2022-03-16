package uicontrollers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import ui.MainGUI;

public class CreateQuestionController implements Controller {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private DatePicker eventDate;

    @FXML
    private ComboBox<?> listEvents;

    @FXML
    private TextField txtQuestion;

    @FXML
    private TextField txtMinBet;

    @FXML
    private Button btnCreateQuestion;

    private MainGUI mainGUI;

    @FXML
    void closeClick(ActionEvent event) {

    }

    @FXML
    void createQuestionClick(ActionEvent event) {

    }

    @FXML
    void initialize() {

    }

    @Override
    public void setMainApp(MainGUI mainGUI) {
        this.mainGUI = mainGUI;
    }
}
