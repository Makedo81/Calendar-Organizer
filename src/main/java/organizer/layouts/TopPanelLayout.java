package organizer.layouts;

import javafx.geometry.Insets;
import javafx.scene.effect.Lighting;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class TopPanelLayout {

    private static Text calendarName = new Text("");

    public HBox createTopPanel() {
        HBox topPanel = new HBox();
        Text title = new Text("Google calendar organiser");
        topPanel.setPadding(new Insets(50, 12, 50, 12));
        topPanel.setSpacing(100);
        topPanel.setId("top-panel");
        title.setId("title");
        Lighting lighting = new Lighting();
        lighting.getLight().setColor(Color.BLUEVIOLET);
        calendarName.setEffect(lighting);
        calendarName.setId("top-panel-calendar-name");
        topPanel.getChildren().addAll(title, calendarName);
        return topPanel;
    }

    public Text getCalendarName() {
        return calendarName;
    }
}
