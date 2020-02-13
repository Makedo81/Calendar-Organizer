package organizer.layouts;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import organizer.client.CalendarClient;
import organizer.event.TextBuilder;
import java.io.IOException;
import java.security.GeneralSecurityException;

public class SecondPanel {

    private BottomPanelLayout bottomPanelLayout = new BottomPanelLayout();
    private TopPanelLayout topPanelLayout = new TopPanelLayout();
    private static TextField calendarName = new TextField();
    private static TextField calendarTime = new TextField();
    private static TextArea currentEventsList = new TextArea();
    private CalendarClient calendarClient = new CalendarClient();
    private TextBuilder textCreator = new TextBuilder();

    public GridPane createPanelTwoGrid() {
        Button show = new Button("Show current events");
        GridPane calendarInputsGrid = new GridPane();
        calendarInputsGrid.setMaxHeight(1000);
        calendarInputsGrid.setMaxWidth(600);
        Label label = new Label("Current calendar events");
        label.setId("label");
        label.setPadding(new Insets(80,1,15,1));
        currentEventsList.setMaxSize(300.00,500.00);
        currentEventsList.setId("events");

        calendarInputsGrid.setPadding(new Insets(0, 100, 0, 100));
        calendarInputsGrid.add(createInputsCalendar(),1,1,1,1);
        calendarInputsGrid.add(label,1,2,1,1);
        calendarInputsGrid.add(currentEventsList,1,3,1,1);
        calendarInputsGrid.add(show,1,4,1,1);

        show.setOnAction(event5 -> {
            currentEventsList.clear();
            try {
                if(topPanelLayout.getCalendarName().getText().equals("")){
                    topPanelLayout.getCalendarName().setText("calendar not selected");
                }else{
                    String id = calendarClient.getCalendarId(topPanelLayout.getCalendarName().getText(),bottomPanelLayout.getUserAccount());
                    String textEventsCurrent = textCreator.createCurrentEventsList(id,bottomPanelLayout.getUserAccount());
                    currentEventsList.setText(textEventsCurrent); }
            } catch (IOException | GeneralSecurityException e) {
                e.printStackTrace();
            }
        });
        return calendarInputsGrid;
    }

    public VBox createInputsCalendar() {
        VBox calendarInputs = new VBox();
        calendarInputs.setPadding(new Insets(50,0,50,0));
        calendarInputs.setSpacing(20);
        Label summaryLabel = new Label();
        summaryLabel.setText("*Calendar summary");
        Label timeZone = new Label();
        timeZone.setText("Time zone");
        calendarTime.setPromptText("Europe/Berlin");
        calendarInputs.getChildren().add(summaryLabel);
        calendarInputs.getChildren().add(calendarName);
        calendarInputs.getChildren().add(timeZone);
        calendarInputs.getChildren().add(calendarTime);
        return calendarInputs;
    }

    public TextArea getCurrentEventsList() {
        return currentEventsList;
    }

    public TextField getCalendarName() {
        return calendarName;
    }

    public TextField getCalendarTime() {
        return calendarTime;
    }
}
