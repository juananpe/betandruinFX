package ui;


import businessLogic.BlFacade;
import businessLogic.BlFacadeImplementation;
import javafx.application.Application;

public class ApplicationLauncher {

  public static void main(String[] args) {

      BlFacade bl = new BlFacadeImplementation();
      MainGUI mainGUI = new MainGUI(bl);

  }


}
