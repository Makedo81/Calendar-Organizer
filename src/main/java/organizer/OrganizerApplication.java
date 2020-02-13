package organizer;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import organizer.layouts.*;

public class OrganizerApplication extends Application {

    private GridPane centerPanel = new GridPane();
    private BorderPane border = new BorderPane();
    private SecondPanel secondPanel = new SecondPanel();
    private FourthPanel fourthPanel = new FourthPanel();
    private GridPane gridInputsEvent = fourthPanel.createPanelFourGrid();
    private GridPane gridInputsCalendar = secondPanel.createPanelTwoGrid();

    @Override
    public void start(Stage primaryStage) {
        FirstPanel firstPanel = new FirstPanel();
        BottomPanelLayout bottomPanelLayout = new BottomPanelLayout();
        TopPanelLayout topPanelLayout = new TopPanelLayout();
        ThirdPanel thirdPanel = new ThirdPanel();
        HBox hboxBottom = bottomPanelLayout.createBottomPanel();
        HBox hboxTop = topPanelLayout.createTopPanel();
        VBox vBoxEvents = thirdPanel.createEventsButtons();
        VBox vBoxCalendar = firstPanel.createCalendarButtons();

        centerPanel.add(vBoxEvents,2,1,1,1);
        centerPanel.add(gridInputsCalendar,1,1,1,1);

        border.setTop(hboxTop);
        border.setBottom(hboxBottom);
        border.setLeft(vBoxCalendar);
        border.setCenter(centerPanel);
        border.setRight(gridInputsEvent);
        border.getRight().setId("border");

        primaryStage.setTitle("Calendar organiser");
        Scene scene = new Scene(border,300,250);
        scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}



