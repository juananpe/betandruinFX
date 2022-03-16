package uicontrollers;

import java.net.URL;
import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneId;
import java.util.*;

import businessLogic.BlFacade;
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

public class BrowseQuestionsController implements Controller {

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

    private Node popupContent;

    private BlFacade businessLogic;

    public BrowseQuestionsController(BlFacade bl) {
        businessLogic = bl;
    }

    @FXML
    void onClose(ActionEvent event) {

    }

    private LocalDate convertToLocalDateViaSqlDate(Date dateToConvert) {
        return new java.sql.Date(dateToConvert.getTime()).toLocalDate();
    }

    private int getMonthNumber(String monthName) {
        return Month.valueOf(monthName.toUpperCase()).getValue();
    }

    private void setEvents(int year, int month) {
        //default time zone
        ZoneId defaultZoneId = ZoneId.systemDefault();

        LocalDate localDate = LocalDate.of(year, month, 1);
        Date date = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());

        for (Date day : businessLogic.getEventsMonth(date)) {
            holidays.add(convertToLocalDateViaSqlDate(day));
        }
    }

    private void setEventsPrePost(int year, int month) {
        LocalDate date = LocalDate.of(year, month, 1);
        setEvents(date.getYear(), date.getMonth().getValue());
        setEvents(date.plusMonths(1).getYear(), date.plusMonths(1).getMonth().getValue());
        setEvents(date.plusMonths(-1).getYear(), date.plusMonths(-1).getMonth().getValue());
    }

    @FXML
    void initialize() {

        setEventsPrePost(LocalDate.now().getYear(), LocalDate.now().getMonth().getValue());

        datepicker.setOnMouseClicked(e -> {
            // get a reference to datepicker inner content
            // attach a listener to the  << and >> buttons
            // mark events for the (prev, current, next) month and year shown
            DatePickerSkin skin = (DatePickerSkin) datepicker.getSkin();
            skin.getPopupContent().lookupAll(".button").forEach(node -> {
                node.setOnMouseClicked(event -> {
                    List<Node> labels = skin.getPopupContent().lookupAll(".label").stream().toList();
                    int month = getMonthNumber(((Label) (labels.get(0))).getText());
                    int year = Integer.parseInt(((Label) (labels.get(1))).getText());
                    setEventsPrePost(year, month);
                });
            });


        });

        datepicker.setDayCellFactory(new Callback<DatePicker, DateCell>() {
            @Override
            public DateCell call(DatePicker param) {
                return new DateCell() {
                    @Override
                    public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);

                        if (!empty && item != null) {
                            if (holidays.contains(item)) {
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

        tblEvents.getItems().add(new Event(1, "Atlético - Athletic", null));
        tblEvents.getItems().add(new Event(2, "Eibar - Barcelona", null));

        qc1.setCellValueFactory(new PropertyValueFactory<>("questionNumber"));
        qc2.setCellValueFactory(new PropertyValueFactory<>("question"));

        tblQuestions.getItems().add(new Question(1, "Who will win the match?", 1.0f, null));
        tblQuestions.getItems().add(new Question(2, "How many goals will be scored in the match?", 1.0f, null));

    }

    @Override
    public void setMainApp(MainGUI mainGUI) {
        this.mainGUI = mainGUI;
    }
}
