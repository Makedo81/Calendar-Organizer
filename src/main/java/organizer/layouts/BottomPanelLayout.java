package organizer.layouts;

import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.CalendarListEntry;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;

public class BottomPanelLayout {

    private ButtonsActionBottomPanel buttonsActionBottomPanel = new ButtonsActionBottomPanel();
    private ObservableList<String> data = FXCollections.observableArrayList();
    private ListView<String> listCalendars = new ListView<>(data);
    private ListView<String> gmailList = new ListView<>();
    private TextField mailText = new TextField();
    private FieldsController fieldsController = new FieldsController();
    private List<CalendarListEntry> calendars = new ArrayList<>();
    private static String gmailAccount = "";
    private Calendar calendarId = null;
    private static String id = "" ;


    public HBox createBottomPanel(){
        HBox bottomPanel = new HBox();
        bottomPanel.setMaxHeight(200);
        bottomPanel.setPadding(new Insets(30, 12, 30, 12));
        bottomPanel.setSpacing(50);
        bottomPanel.setStyle("-fx-background-color: #998e7d;");
        Button register = new Button("Register");
        register.setPrefSize(150, 20);
        Button buttonSwitchAccount = new Button("Switch account");
        buttonSwitchAccount.setPrefSize(150, 20);
        Button buttonSwitchCalendar = new Button("Switch calendar");
        buttonSwitchCalendar.setPrefSize(150, 20);
        mailText.setPromptText("email@gmail.com");
        HBox hbox1 = new HBox(gmailList);
        hbox1.setMaxHeight(70);
        HBox hbox3 = new HBox(listCalendars);
        hbox3.setMaxHeight(70);
        Button button = new Button("refresh");
        bottomPanel.getChildren().addAll(register, mailText,buttonSwitchAccount,hbox1,buttonSwitchCalendar,hbox3,button);
        button.setOnAction(event3 -> {
            try {
                buttonsActionBottomPanel.switchAccount(listCalendars, gmailList,data);
            } catch (IOException | GeneralSecurityException e) {
                e.printStackTrace();
            }
        });

        register.setOnAction(event1 -> {
            try {
                if (calendarId == null) {
                    buttonsActionBottomPanel.addNewUser(mailText.getText(), gmailList);
                    mailText.clear();
                }else
                    buttonsActionBottomPanel.createCalendarsListView(mailText.getText(), gmailList,id,data,listCalendars);
            } catch (IOException | GeneralSecurityException e) {
                e.printStackTrace();
            }
        });

        buttonSwitchCalendar.setOnAction(event1 -> {
            try {
                id = buttonsActionBottomPanel.switchCalendar(listCalendars,gmailAccount);
            } catch (IOException | GeneralSecurityException e) {
                e.printStackTrace();
            }
        });

        buttonSwitchAccount.setOnAction(event2 -> {
            try {
                removeItem();
                calendars.clear();
                gmailAccount=buttonsActionBottomPanel.switchAccount( listCalendars, gmailList,data);
                fieldsController.clearFieldsAfterAccountSwitched();
            } catch (IOException | GeneralSecurityException e) {
                e.printStackTrace();
            }
        });
        return bottomPanel;
    }

    public void removeItem(){
        ObservableList selectedIndices = listCalendars.getSelectionModel().getSelectedIndices();
        for(Object o : selectedIndices){
            int index = (Integer) o;
            listCalendars.getItems().get(index);
            listCalendars.getItems().remove(index);
        }
    }

    public String getId() {
        return id;
    }

    public String getUserAccount() {
        return gmailAccount;
    }
}
