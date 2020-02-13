package organizer.event;

import com.google.api.services.calendar.model.CalendarListEntry;
import com.google.api.services.calendar.model.Event;
import organizer.client.CalendarClient;
import organizer.client.EventClient;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

public class TextBuilder {

    private EventClient eventClient = new EventClient();
    private CalendarClient calendarClient = new CalendarClient();

    public String createAllCalendarsEventsList(String user) throws IOException, GeneralSecurityException {
        StringBuilder stringBuilder = new StringBuilder();
        List<CalendarListEntry> list = calendarClient.getAllCalendars(user);
        for (CalendarListEntry calendar : list) {
            List<Event> eventsList = eventClient.showAllEvents(calendar.getId(), user);
            for (Event event : eventsList) {
                if (stringBuilder.length() > 0) {
                    stringBuilder.append("\n");
                }
                stringBuilder.append(event.getStart().getDate() + " , " +  event.getSummary() + " ," + event.getLocation() + " , " + event.getDescription());
            }
        } return stringBuilder.toString();
    }

    public String createCurrentEventsList(String id, String user) throws IOException, GeneralSecurityException {
        StringBuilder stringBuilder = new StringBuilder();
        List<Event> newList = eventClient.showAllEvents(id,user);
        for (Event event : newList) {
            if (stringBuilder.length() > 0) {
                stringBuilder.append("\n");
            }
            stringBuilder.append(event.getSummary()+ " ," +event.getStart().getDate()+ " ," + event.getLocation() +  " , " +  event.getDescription()  );
        }
        return stringBuilder.toString();
    }
}

