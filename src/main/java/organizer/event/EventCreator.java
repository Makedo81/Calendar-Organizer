package organizer.event;

import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

public class EventCreator {

    private CsvReader csvReader = new CsvReader();

    public List<Event> createEventFromCSV(String filePath) throws IOException {
        List<Event> events = csvReader.reader(filePath);
        return events;
    }

    public Event createEvent(String summary, String location, String startDate, String description,String url, String user) throws IOException, GeneralSecurityException {
        AttachmentCreator attachmentCreator = new AttachmentCreator();
        DateTime startDateTime = new DateTime(startDate);
        EventDateTime start = new EventDateTime()
                .setDate(startDateTime);

        Event event = new Event()
                .setSummary(summary)
                .setLocation(location)
                .setStart(start)
                .setEnd(start)
                .setDescription(description)
                .setAttachments(attachmentCreator.createAttachment(url,user));
        return event;
    }
}
