package organizer.event;

import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;
import com.opencsv.CSVReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class CsvReader {

    public List<Event> reader(String filePath) throws IOException {
        List<Event> eventsList = new ArrayList<>();
        try (
                Reader reader = Files.newBufferedReader(Paths.get(filePath));
                CSVReader csvReader = new CSVReader(reader)) {
            String[] nextRecord;
            while ((nextRecord = csvReader.readNext()) != null) {
                System.out.println("Summary : "+ nextRecord[0] +"Location : " + nextRecord[1] + "StartDate : " + nextRecord[2] +"Description : " + nextRecord[3]);
                Event event = createEvent(nextRecord);
                eventsList.add(event);
            }
            return eventsList;
        }
    }

    private Event createEvent (String[]data){
        return new Event()
                .setSummary(data[0])
                .setLocation(data[1])
                .setStart(createDate(data[2]))
                .setEnd(createDate(data[2]))
                .setDescription(data[3]);
    }

    private EventDateTime createDate(String date){
        DateTime startDateTime = new DateTime(date);
        return new EventDateTime()
                .setDate(startDateTime);
    }
}


