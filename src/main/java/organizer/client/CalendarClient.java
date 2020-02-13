package organizer.client;

import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.CalendarList;
import com.google.api.services.calendar.model.CalendarListEntry;
import organizer.layouts.TopPanelLayout;
import organizer.service.CalendarServiceBuilder;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CalendarClient {

    private CalendarServiceBuilder calendarServiceBuilder = new CalendarServiceBuilder();
    private TopPanelLayout topPanelLayout = new TopPanelLayout();

    public com.google.api.services.calendar.model.Calendar addCalendar(String summary, String location, String user) throws IOException, GeneralSecurityException {
        Calendar service = calendarServiceBuilder.processCredentials(user);
        com.google.api.services.calendar.model.Calendar calendar = new com.google.api.services.calendar.model.Calendar();
        calendar.setSummary(summary);
        if(location.equals("")){
            calendar.setTimeZone("Europe/Warsaw");
        }else
            calendar.setTimeZone(location);
        com.google.api.services.calendar.model.Calendar createdCalendar = service.calendars().insert(calendar).execute();
        System.out.println(createdCalendar.getSummary()+ " " + createdCalendar.getId() );
        return createdCalendar;
    }

    public void deleteCalendar(String name,String user) throws IOException, GeneralSecurityException {
        Calendar service = calendarServiceBuilder.processCredentials(user);
        service.calendars().delete(this.getCalendarId(name,user)).execute();
    }

    public String getCalendarId(String name,String user) throws IOException, GeneralSecurityException{
        Calendar service = calendarServiceBuilder.processCredentials(user);
        String id;
        List<CalendarListEntry> calendarListEntry ;
        String pageToken = null;
        do {
            CalendarList calendarList = service.calendarList().list().setPageToken(pageToken).execute();
            List<CalendarListEntry> items = calendarList.getItems();
            calendarListEntry = calendarList.getItems().stream().
                    filter(e->e.getSummary().equals(name)).filter((e->!e.getSummary().equals("Week Numbers"))).collect(Collectors.toList());
            System.out.println(calendarListEntry.size());
            if(calendarListEntry.size()==0){
                topPanelLayout.getCalendarName().setText( name + " don't exists");
                topPanelLayout.getCalendarName().setId("top-panel-text-error");
            }id = calendarListEntry.get(0).getId();
            for (CalendarListEntry list : items) {
                System.out.println(list.getId() + " " + list.getSummary());
            }
            pageToken = calendarList.getNextPageToken();
        } while (pageToken != null);
        topPanelLayout.getCalendarName().setId("top-panel-calendar-name");
        return id;
    }

    public List<CalendarListEntry> getAllCalendars(String user) throws IOException, GeneralSecurityException{
        List<CalendarListEntry> calendars = new ArrayList<>();
        Calendar service = calendarServiceBuilder.processCredentials(user);
        String pageToken = null;
        do {
            CalendarList calendarList = service.calendarList().list().setPageToken(pageToken).execute();
            List<CalendarListEntry> items = calendarList.getItems().stream().filter((e->!e.getSummary().equals("Week Numbers"))).collect(Collectors.toList());
            calendars.addAll(items);
            for (CalendarListEntry calendarListEntry : items) {
                System.out.println(calendarListEntry.getSummary());
            }
            pageToken = calendarList.getNextPageToken();
        } while (pageToken != null);
        return calendars;
    }
}
