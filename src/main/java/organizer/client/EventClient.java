package organizer.client;

import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.Events;
import organizer.event.AttachmentCreator;
import organizer.event.EventCreator;
import organizer.layouts.FieldsController;
import organizer.service.CalendarServiceBuilder;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class EventClient {

    private EventCreator eventCreator = new EventCreator();
    private AttachmentCreator attachmentCreator = new AttachmentCreator();
    private CalendarServiceBuilder calendarServiceBuilder = new CalendarServiceBuilder();
    private FieldsController fieldsController = new FieldsController();

    public void postEvent(String summary, String location, String startDate, String description, String id, String user,String url) throws IOException, GeneralSecurityException {
        Calendar calendarClientService = calendarServiceBuilder.processCredentials(user);
        Event event = eventCreator.createEvent(summary, location, startDate, description,url,user);
        String calendarId = fieldsController.checkId(id);
        event = calendarClientService.events().insert(calendarId, event).setSupportsAttachments(true).execute();
        System.out.printf("Event created: %s\n", event.getHtmlLink());
    }

    public void deleteAllEvents(String id, String user) throws IOException, GeneralSecurityException {
        Calendar calendarClientService = calendarServiceBuilder.processCredentials(user);
        String calendarId = fieldsController.checkId(id);
        Events events = calendarClientService.events().list(calendarId)
                .setSingleEvents(true)
                .execute();
        List<Event> items;
        items = events.getItems();
        if (items.isEmpty()) {
            System.out.println("No upcoming events found.");
        } else {
            System.out.println("Upcoming events");
            for (Event event : items) {
                calendarClientService.events().delete(calendarId, event.getId()).execute();
            }
        }
    }

    public void deleteEvent(String id,String eventName, String user) throws IOException, GeneralSecurityException {
        Calendar calendarClientService = calendarServiceBuilder.processCredentials(user);
        String calendarId = fieldsController.checkId(id);
        String eventId = this.getEventId(id,eventName, user);
        calendarClientService.events().delete(calendarId, eventId).execute();
    }

    public String getEventId(String calendarId, String eventName, String user) throws IOException, GeneralSecurityException {
        Calendar calendarClientService = calendarServiceBuilder.processCredentials(user);
        List<String> oneEventList = new ArrayList<>();
        String selectedCalendarId = fieldsController.checkId(calendarId);
        Events events = calendarClientService.events().list(selectedCalendarId)
                .setSingleEvents(true)
                .execute();

        List<Event> items = events.getItems();
        List<Event> selectedEvent = items.stream().filter(e -> e.getSummary().equals(eventName)).collect(Collectors.toList());
        if (items.isEmpty()) {
            System.out.println("No upcoming events found.");
        } else {
            System.out.println("Upcoming events");
            for (Event event : selectedEvent) {
                oneEventList.add(event.getId());
            }
        }
        return oneEventList.get(0);
    }

    public List<Event> showAllEvents(String calendarId, String user1) throws IOException, GeneralSecurityException {
        String user = fieldsController.checkUserIfEmpty(user1);
        Calendar calendarClientService = calendarServiceBuilder.processCredentials(user);
        String calId = fieldsController.checkId(calendarId);
        Events events = calendarClientService.events().list(calId)
                .setSingleEvents(true)
                .execute();
        List<Event> items = events.getItems();
        if (items.isEmpty()) {
            System.out.println("No upcoming events found.");
        }
        return items;
    }

    public void postEventCSV(String id, String filePath, String user) throws IOException, GeneralSecurityException {
        System.out.println(filePath);
        Calendar calendarClientService = calendarServiceBuilder.processCredentials(user);
        List<Event> eventList = eventCreator.createEventFromCSV(filePath);
        for (int i = 0; i < eventList.size(); i++) {
            Event event = eventList.get(i);
            String calendarId = fieldsController.checkId(id);
            event = calendarClientService.events().insert(calendarId, event).execute();
            System.out.printf("Event created: %s\n", event.getId() + "  Event summary:  " + event.getSummary());
            System.out.printf("Event created: %s\n", event.getHtmlLink());
        }
    }

    public Event getEvent(String calendarId, String eventId, String userId) throws IOException, GeneralSecurityException {
        Calendar calendarClientService = calendarServiceBuilder.processCredentials(userId);
        Event eventCurrent = calendarClientService.events().get(calendarId, eventId).execute();
        System.out.printf("Event selected : %s\n", eventCurrent.getId() + "  Event summary:  " + eventCurrent.getSummary());
        return eventCurrent;
    }

    public Event updateEvent(String calendarId, String user, Event eventCurrent, Event event,String url) throws IOException, GeneralSecurityException {
        Calendar calendarClientService = calendarServiceBuilder.processCredentials(user);
        if (url.equals("") && event.getAttachments() == null) {
            System.out.println("No fileInput added");
            eventCurrent.setAttachments(null);
        }
        String id = eventCurrent.getId();
        eventCurrent.setSummary(event.getSummary());
        eventCurrent.setDescription(event.getDescription());
        eventCurrent.setLocation(event.getLocation());
        eventCurrent.setAttachments(attachmentCreator.createAttachment(url, user));
        Event updatedEvent = calendarClientService.events().update(calendarId, id, eventCurrent).setSupportsAttachments(true).execute();
        return updatedEvent;
    }
}
