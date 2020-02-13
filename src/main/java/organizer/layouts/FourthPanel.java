package organizer.layouts;

import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class FourthPanel {

    private static TextField summary = new TextField();
    private static TextField location = new TextField();
    private static TextField dateStart = new TextField();
    private static TextArea description = new TextArea();
    private static TextField fileInput = new TextField();
    private static ImageView imageView = new ImageView();

    public VBox createEventInputs(){

        VBox eventInputs = new VBox();
        FlowPane pane = new FlowPane();
        dateStart.setPromptText("yyyy-mm-dd");
        eventInputs.setPadding(new Insets(50,0,0,0));
        eventInputs.setSpacing(20);
        Label summaryLabel = new Label();
        summaryLabel.setText("*Event summary");
        Label dateLabel = new Label();
        dateLabel.setText("*Start date");
        Label locationLabel = new Label();
        locationLabel.setText("Location");
        Label descriptionLabel = new Label();
        descriptionLabel.setText("Description");

        Label fileLabel = new Label();
        fileLabel.setText("GDrive attachment");
        pane.setOrientation(Orientation.HORIZONTAL);
        imageView.setFitHeight(30);
        imageView.setFitWidth(30);
        BorderPane border = new BorderPane();
        border.setLeft(imageView);
        border.setCenter(fileInput);

        eventInputs.getChildren().add(summaryLabel);
        eventInputs.getChildren().add(summary);
        eventInputs.getChildren().add(locationLabel);
        eventInputs.getChildren().add(location);
        eventInputs.getChildren().add(dateLabel);
        eventInputs.getChildren().add(dateStart);
        eventInputs.getChildren().add(descriptionLabel);
        eventInputs.getChildren().add(description);
        eventInputs.getChildren().add(fileLabel);
        eventInputs.getChildren().add(border);

        return eventInputs;
    }

    public GridPane createPanelFourGrid() {
        GridPane eventsInputGrid = new GridPane();
        eventsInputGrid.setMaxHeight(1000);
        eventsInputGrid.setMaxWidth(600);
        eventsInputGrid.setPadding(new Insets(0, 100, 0, 100));
        eventsInputGrid.add(createEventInputs(),1,1,1,1);
        return eventsInputGrid;
    }

    public  TextField getSummary() {
        return summary;
    }

    public TextField getLocation() {
        return location;
    }

    public TextField getDateStart() {
        return dateStart;
    }

    public TextArea getDescription() {
        return description;
    }

    public TextField getFileInput() {
        return fileInput;
    }

    public ImageView getImageView() {
        return imageView;
    }
}
