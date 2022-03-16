package ui;

import businessLogic.BlFacade;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.kordamp.bootstrapfx.BootstrapFX;
import uicontrollers.BrowseQuestionsController;
import uicontrollers.Controller;
import uicontrollers.MainGUIController;

import java.io.IOException;

public class MainGUI {

  private Window mainLag, createQuestionLag, browseQuestionsLag;

  private BlFacade businessLogic;

  public BlFacade getBusinessLogic(){
    return businessLogic;
  }

  public void setBusinessLogic (BlFacade afi){
    businessLogic = afi;
  }

  public MainGUI(BlFacade bl) {
    Platform.startup( () -> {
      try {
        setBusinessLogic(bl);
        init(new Stage());
      } catch (IOException e) {
        e.printStackTrace();
      }
    });
  }


  class Window {
    Controller c;
    Parent ui;
  }

  private Window load(String fxmlfile) throws IOException {
    Window lag = new Window();
    FXMLLoader loader = new FXMLLoader(MainGUI.class.getResource(fxmlfile));
    loader.setControllerFactory(controllerClass -> {

     if (controllerClass == BrowseQuestionsController.class) {
        return new BrowseQuestionsController(businessLogic);
      } else {
        // default behavior for controllerFactory:
        try {
          return controllerClass.getDeclaredConstructor().newInstance();
        } catch (Exception exc) {
          exc.printStackTrace();
          throw new RuntimeException(exc); // fatal, just bail...
        }
      }


    });
    lag.ui = loader.load();
    ((Controller)loader.getController()).setMainApp(this);
    lag.c = loader.getController();
    return lag;
  }

  public void init(Stage stage) throws IOException {
    mainLag = load("/MainGUI.fxml");
    browseQuestionsLag = load("/BrowseQuestions.fxml");
    createQuestionLag = load("/CreateQuestion.fxml" );

    setupScene(stage, mainLag.ui, "Bet&Ruin Project", 320, 350);
  }

//  public void start(Stage stage) throws IOException {
//      init(stage);
//  }

  public void showBrowseQuestions() {
    Stage stage = new Stage();
    setupScene(stage, browseQuestionsLag.ui, "Browse Questions", 1000, 500);

  }

  public void showCreateQuestion() {
    Stage stage = new Stage();
    setupScene(stage, createQuestionLag.ui, "Create Question", 550, 400);

  }

  private void setupScene(Stage stage, Parent ui, String title, int width, int height) {
    Scene scene = new Scene(ui, width, height);
    scene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
    stage.setTitle(title);
    stage.setScene(scene);
    stage.show();
  }

//  public static void main(String[] args) {
//    launch();
//  }
}
