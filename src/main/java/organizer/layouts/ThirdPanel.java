package organizer.layouts;

import com.google.api.services.calendar.model.Event;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import organizer.client.EventClient;
import java.io.IOException;
import java.security.GeneralSecurityException;

public class ThirdPanel {

    private BorderPane border = new BorderPane();
    private GridPane gridEvents = new GridPane();
    private VBox mainButtonPanel = new VBox();
    private HBox hbox3 = new HBox(border);
    private Text text = new Text("Options event");
    private Label label = new Label("Update event");
    private ListView<String> eventListView = new ListView<>();
    private EventClient eventClient = new EventClient();
    private BottomPanelLayout bottomPanelLayout = new BottomPanelLayout();
    private FieldsController fieldsController = new FieldsController();
    private FourthPanel fourthPanel = new FourthPanel();
    private TextField textField = new TextField();
    private ButtonsActionThirdPanel buttonsActionThirdPanel = new ButtonsActionThirdPanel();

    public VBox createEventsButtons(){
        textField.setPromptText("*.csv");
        label.setPadding(new Insets(25,1,15,1));
        mainButtonPanel.setPadding(new Insets(50));
        mainButtonPanel.setSpacing(25);
        eventListView.setMaxHeight(90);
        hbox3.setMaxHeight(70);
        text.setId("label");
        label.setId("label");

        mainButtonPanel.getChildren().add(text);
        Button options[] = new Button[] {
                new Button("Add event"),
                new Button("Delete all events"),
                new Button("Update event"),
                new Button("Add event from *.csv"),
                new Button("Show events"),
                new Button("Add changes")
        };
        for (int i=0; i<5; i++) {
            VBox.setMargin(options[i], new Insets(0, 0, 0, 8));
            options[i].setMaxWidth(180);
            mainButtonPanel.getChildren().add(options[i]);
        }
        Button add = options[0];
        Button deleteAll = options[1];
        Button update = options[2];
        Button addFromCsv = options[3];
        Button showUpdateEvent = options[4];
        Button setFieldsButton = options[5];

        mainButtonPanel.getChildren().add(textField);
        mainButtonPanel.getChildren().add(hbox3);
        mainButtonPanel.getChildren().add(gridEvents);
        /**
         * creating update section
         */
        border.setTop(label);
        border.setLeft(showUpdateEvent);
        border.setCenter(setFieldsButton);
        border.setRight(update);
        border.setBottom(eventListView);

        BorderPane border1 = new BorderPane();
        border1.setTop(eventListView);
        Button deleteEvent = new Button("delete");
        deleteEvent.setMaxWidth(100);
        border1.setBottom(deleteEvent);
        border.setBottom(border1);

        add.setOnAction(event1 -> {
            FourthPanel fourthPanel = new FourthPanel();
            String text1 = fieldsController.getSummary();
            String text2 = fieldsController.getLocation();
            String text3 = fieldsController.getDate();
            String text4 = fieldsController.getDescription();
            try {
                if(fieldsController.checkField()) {
                    eventClient.postEvent(text1, text2, text3, text4, bottomPanelLayout.getId(), bottomPanelLayout.getUserAccount(), fourthPanel.getFileInput().getText());
                    fieldsController.clearFields();
                } else fieldsController.getSummary();
            } catch (IOException | GeneralSecurityException e) {
                e.printStackTrace();
            }
        });

        deleteAll.setOnAction(event2 -> {
            try {
                eventClient.deleteAllEvents(bottomPanelLayout.getId(),bottomPanelLayout.getUserAccount());
                fieldsController.clearFields();
            } catch (IOException | GeneralSecurityException e) {
                e.printStackTrace();
            }
        });

        update.setOnAction(event3 -> {
            try {
                buttonsActionThirdPanel.update(eventListView);
            } catch (IOException | GeneralSecurityException e) {
                e.printStackTrace();
            }
        });

        addFromCsv.setOnAction(event4 -> {
            try {
                try {
                    String filePath = textField.getText();
                    if(filePath.equals("")){
                        textField.setId("empty-text-field");
                    }
                    eventClient.postEventCSV(bottomPanelLayout.getId(),filePath,bottomPanelLayout.getUserAccount());
                } catch (GeneralSecurityException e) {
                    e.printStackTrace();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            textField.setStyle("");
            textField.clear();
        });

        showUpdateEvent.setOnAction(event6 -> {
            try {
                buttonsActionThirdPanel.createEventsList(bottomPanelLayout.getId(),bottomPanelLayout.getUserAccount(),eventListView);
            } catch (IOException | GeneralSecurityException e) {
                e.printStackTrace();
            }
        });

        setFieldsButton.setOnAction(event7 -> {
            try {
                setFieldsForModification();
            } catch (IOException | GeneralSecurityException e) {
                e.printStackTrace();
            }
        });

        deleteEvent.setOnAction(event2 -> {
            try {
                buttonsActionThirdPanel.delete(eventListView);
            } catch (IOException | GeneralSecurityException e) {
                e.printStackTrace();
            }
        });
        return mainButtonPanel;
    }

    private void setFieldsForModification()throws IOException, GeneralSecurityException{
        Event eventCurrent = buttonsActionThirdPanel.getSelectedEvent(eventListView);
        setFields(eventCurrent);
    }

    private void setFields(Event event){
        fourthPanel.getSummary().setText(event.getSummary());
        fourthPanel.getLocation().setText(event.getLocation());
        fourthPanel.getDateStart().setText(event.getStart().getDate().toString());
        fourthPanel.getDescription().setText(event.getDescription());
        fourthPanel.getFileInput().setText(event.getAttachments().get(0).getFileUrl());
        fourthPanel.getImageView().setImage(new Image(event.getAttachments().get(0).getIconLink()));
    }

    public ListView getEventListView() {
        return eventListView;
    }
}
