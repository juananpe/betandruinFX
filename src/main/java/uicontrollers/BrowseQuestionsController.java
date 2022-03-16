package uicontrollers;

import java.net.URL;
import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneId;
import java.util.*;

import domain.Event;
import domain.Question;
import gui.CreateQuestionGUI;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.skin.DatePickerSkin;
import javafx.util.Callback;
import ui.MainGUI;

public class BrowseQuestionsController implements Controller{

        @FXML
        private ResourceBundle resources;

        @FXML
        private URL location;

        @FXML
        private Button btnClose;

        @FXML
        private DatePicker datepicker;

        @FXML
        private TableColumn<Event, Integer> ec1;

        @FXML
        private TableColumn<Event, String> ec2;

        @FXML
        private TableColumn<Event, Integer> qc1;

        @FXML
        private TableColumn<Event, Integer> qc2;

        @FXML
        private TableView<Event> tblEvents;

        @FXML
        private TableView<Question> tblQuestions;


        private MainGUI mainGUI;

        private List<LocalDate> holidays = new ArrayList<>();

        @FXML
        void onClose(ActionEvent event) {

        }

        private LocalDate convertToLocalDateViaSqlDate(Date dateToConvert) {
                return new java.sql.Date(dateToConvert.getTime()).toLocalDate();
        }

        private void setEvents(){
                //default time zone
                ZoneId defaultZoneId = ZoneId.systemDefault();

                //creating the instance of LocalDate using the day, month, year info

                LocalDate localDate = LocalDate.now();
                if (datepicker.getValue() != null){
                        datepicker.getValue();
                }

                //local date + atStartOfDay() + default time zone + toInstant() = Date
                Date date = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());

                for (Date day : mainGUI.getBusinessLogic().getEventsMonth(date)) {
                        holidays.add(convertToLocalDateViaSqlDate(day));
                }
        }

        @FXML
        void initialize() {


                DatePickerSkin datePickerSkin = new DatePickerSkin(datepicker);
                Node popupContent = datePickerSkin.getPopupContent();

                Button b = (Button) popupContent.lookup(".right-button");
                b.setOnAction(actionEvent -> {
                        System.out.println("Clickeeeeeeeeed");
                });

//                //To see day of the week nodes
//                for (Node node : popupContent.lookupAll("*")) {
//                        node.setOnMouseClicked((event) -> {
//                                DateCell dateCell = (DateCell) node;
//                                System.out.println("You clicked: " + dateCell.getText());
//                        });
//                }
//
//                // on calendar icon click
//                datepicker.showingProperty().addListener((observableValue, aBoolean, t1) -> {
//                        System.out.println("+++++++++++++++++++");
//                        setEvents();
//                });
//
//                // on day click
//                datepicker.setOnAction(actionEvent -> {
//                        System.out.println("***************************");
//                        setEvents();
//                });

                datepicker.setOnMouseClicked(e -> {
                        setEvents();
                });



                datepicker.setDayCellFactory(new Callback<DatePicker, DateCell>() {
                        @Override
                        public DateCell call(DatePicker param) {
                                return new DateCell(){
                                        @Override
                                        public void updateItem(LocalDate item, boolean empty) {
                                                super.updateItem(item, empty);

                                                if (!empty && item != null) {
                                                        if(holidays.contains(item)) {
                                                                this.setStyle("-fx-background-color: pink");
                                                        }
                                                }
                                        }
                                };
                        }
                });

                // load values into table

                ec1.setCellValueFactory(new PropertyValueFactory<>("eventNumber"));
                ec2.setCellValueFactory(new PropertyValueFactory<>("description"));

                tblEvents.getItems().add(new Event(1,"Atlético - Athletic", null));
                tblEvents.getItems().add(new Event(2,"Eibar - Barcelona", null));

                qc1.setCellValueFactory(new PropertyValueFactory<>("questionNumber"));
                qc2.setCellValueFactory(new PropertyValueFactory<>("question"));

                tblQuestions.getItems().add(new Question(1,"Who will win the match?", 1.0f, null));
                tblQuestions.getItems().add(new Question(2,"How many goals will be scored in the match?", 1.0f, null));

        }

        @Override
        public void setMainApp(MainGUI mainGUI) {
                this.mainGUI = mainGUI;
        }
}
