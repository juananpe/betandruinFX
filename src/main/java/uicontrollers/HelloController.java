package uicontrollers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import ui.MainGUI;

public class HelloController implements Controller {
    @FXML
    private Label welcomeText;

    private MainGUI mainGUI;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    @Override
    public void setMainApp(MainGUI mainGUI) {
        this.mainGUI = mainGUI;
    }
}
