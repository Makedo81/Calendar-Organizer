package organizer.layouts;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import organizer.client.CalendarClient;
import organizer.event.TextBuilder;
import java.io.IOException;
import java.security.GeneralSecurityException;

public class FirstPanel {

    private String calendarSummary;
    private Text title = new Text("Options");
    private VBox calendarButtons = new VBox();
    private CalendarClient calendarClient = new CalendarClient();
    private TopPanelLayout topPanelLayout = new TopPanelLayout();
    private SecondPanel secondPanel = new SecondPanel();
    private static TextArea listEventTable = new TextArea();
    private BottomPanelLayout bottomPanelLayout = new BottomPanelLayout();
    private TextBuilder textBuilder = new TextBuilder();

    public VBox createCalendarButtons() {
        calendarButtons.setPadding(new Insets(50));
        calendarButtons.setSpacing(25);
        title.setId("label");
        calendarButtons.getChildren().add(title);

        Button options[] = new Button[] {
                new Button("Add new calendar"),
                new Button("Delete calendar")};
        for (int i=0; i<2; i++) {
            options[i].setMaxWidth(180);
            VBox.setMargin(options[i], new Insets(0, 0, 0, 8));
            calendarButtons.getChildren().add(options[i]);
        }
        Button addCalendar = options[0];
        Button deleteCalendar = options[1];

        addCalendar.setOnAction(event1 -> {
            try {
                calendarClient.addCalendar(secondPanel.getCalendarName().getText(), secondPanel.getCalendarTime().getText(),bottomPanelLayout.getUserAccount());
                clearCalendarFields();
            } catch (IOException | GeneralSecurityException e) {
                e.printStackTrace();
            }
        });

        deleteCalendar.setOnAction(event2 -> {
            try {
                calendarSummary = topPanelLayout.getCalendarName().getText();
                topPanelLayout.getCalendarName().setText("");
                calendarClient.deleteCalendar(calendarSummary,bottomPanelLayout.getUserAccount());
                bottomPanelLayout.removeItem();
            } catch (IOException | GeneralSecurityException e) {
                e.printStackTrace();
            }
        });

        calendarButtons.getChildren().add(createPanelOneGrid());
        return calendarButtons;
    }

    private void clearCalendarFields() {
        secondPanel.getCalendarName().clear();
        secondPanel.getCalendarTime().clear();
    }

    public GridPane createPanelOneGrid() {
        Button show = new Button("Show all events");
        GridPane calendarInputsGrid = new GridPane();
        calendarInputsGrid.setMaxHeight(1000);
        calendarInputsGrid.setMaxWidth(600);
        Label label = new Label("All calendar events ");
        label.setId("label");
        label.setPadding(new Insets(135,1,15,1));
        listEventTable.setMaxSize(300.00,500.00);
        listEventTable.setId("events");

        show.setOnAction(event2 -> {
            listEventTable.clear();
            try {
                String calendarInfo = textBuilder.createAllCalendarsEventsList(bottomPanelLayout.getUserAccount());
                listEventTable.setText(calendarInfo);
            } catch (IOException | GeneralSecurityException e) {
                e.printStackTrace();
            }
        });

        calendarInputsGrid.add(label,1,2,1,1);
        calendarInputsGrid.add(listEventTable,1,3,1,1);
        calendarInputsGrid.add(show,1,4,1,1);

        return calendarInputsGrid;
    }

    public TextArea getListEventTable() {
        return listEventTable;
    }
}




