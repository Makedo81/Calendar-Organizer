package organizer.layouts;

import com.google.api.services.calendar.model.Event;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import organizer.client.EventClient;
import organizer.event.EventCreator;
import organizer.event.TextBuilder;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

public class ButtonsActionThirdPanel {

    private BottomPanelLayout bottomPanelLayout = new BottomPanelLayout();
    private FourthPanel fourthPanel = new FourthPanel();
    private FieldsController fieldsController = new FieldsController();
    private EventCreator eventCreator = new EventCreator();
    private EventClient eventClient = new EventClient();
    private SecondPanel secondPanel = new SecondPanel();
    private TextBuilder textBuilder = new TextBuilder();

    public void update(ListView<String> eventListView)throws IOException, GeneralSecurityException {
        String user = bottomPanelLayout.getUserAccount();
        Event eventCurrent = getSelectedEvent(eventListView);
        String gDrive = fourthPanel.getFileInput().getText();
        System.out.println(gDrive);
        Event newEvent = eventCreator.createEvent(fourthPanel.getSummary().getText(), fourthPanel.getLocation().getText(),
                fourthPanel.getDateStart().getText(), fourthPanel.getDescription().getText(), gDrive,
                user);
        eventClient.updateEvent(bottomPanelLayout.getId(), bottomPanelLayout.getUserAccount(), eventCurrent, newEvent, gDrive);
        fieldsController.clearFields();
        createEventsList(bottomPanelLayout.getId(), bottomPanelLayout.getUserAccount(),eventListView);
        String textEventsCurrent = textBuilder.createCurrentEventsList(bottomPanelLayout.getId(), bottomPanelLayout.getUserAccount());
        secondPanel.getCurrentEventsList().setText(textEventsCurrent);
        createEventsList(bottomPanelLayout.getId(), bottomPanelLayout.getUserAccount(), eventListView);
    }

    public void delete(ListView<String> eventListView)throws IOException, GeneralSecurityException{

        String eventName = this.getSelectedEvent(eventListView).getSummary();
        eventClient.deleteEvent(bottomPanelLayout.getId(),eventName, bottomPanelLayout.getUserAccount());
        fieldsController.clearFields();
        SecondPanel secondPanel = new SecondPanel();
        TextBuilder textBuilder = new TextBuilder();
        String textEventsCurrent = textBuilder.createCurrentEventsList(bottomPanelLayout.getId(),bottomPanelLayout.getUserAccount());
        secondPanel.getCurrentEventsList().setText(textEventsCurrent);
        createEventsList(bottomPanelLayout.getId(),bottomPanelLayout.getUserAccount(), eventListView);
    }

    public Event getSelectedEvent(ListView<String> eventListView)throws IOException, GeneralSecurityException{
        String eventName = "";
        ObservableList selectedIndices = eventListView.getSelectionModel().getSelectedIndices();
        for (Object o : selectedIndices) {
            int index = (Integer) o;
            eventName = eventListView.getItems().get(index);
            System.out.println(eventName + " event name selected ");
        }
        String eventId = eventClient.getEventId(bottomPanelLayout.getId(),eventName,bottomPanelLayout.getUserAccount());
        Event eventCurrent = eventClient.getEvent(bottomPanelLayout.getId(),eventId,bottomPanelLayout.getUserAccount());
        return eventCurrent;
    }

    public List<Event> createEventsList(String calendarId, String user, ListView<String> eventListView) throws IOException, GeneralSecurityException {
        String userChecked = fieldsController.checkUserIfEmpty(user);
        eventListView.getItems().clear();
        List<Event> events = eventClient.showAllEvents(calendarId, userChecked);
        for (Event event : events) {
            eventListView.getItems().add(event.getSummary());
        }
        return  events;
    }
}

