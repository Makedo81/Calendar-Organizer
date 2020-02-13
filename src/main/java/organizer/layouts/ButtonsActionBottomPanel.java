package organizer.layouts;

import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.CalendarListEntry;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import organizer.client.CalendarClient;
import organizer.service.CalendarServiceBuilder;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

public class ButtonsActionBottomPanel {

    private FieldsController fieldsController = new FieldsController();
    private CalendarClient calendarClient = new CalendarClient();
    private TopPanelLayout topPanelLayout = new TopPanelLayout();
    private CalendarServiceBuilder calendarServiceBuilder = new CalendarServiceBuilder();

    public void addNewUser(String newUser, ListView<String> gmailList)throws IOException, GeneralSecurityException {
        calendarServiceBuilder.processCredentials(newUser);
        gmailList.getItems().add(newUser);
        calendarClient.getAllCalendars(newUser);
    }

    public void createCalendarsListView(String newUser, ListView<String> gmailList, String calendarId, ObservableList<String> data,
                                        ListView<String> listCalendars)throws IOException, GeneralSecurityException {
        gmailList.getItems().add(newUser);
        List<CalendarListEntry> calendars = calendarClient.getAllCalendars(calendarId);
        for (CalendarListEntry list : calendars) {
            data.add(list.getSummary());
            listCalendars.setItems(data);
        }
    }

    public String switchAccount(ListView<String> listCalendars,ListView<String> gmailList,ObservableList<String> data)throws IOException, GeneralSecurityException{
        String gmailAccount = null;
        listCalendars.getItems().clear();
        ObservableList selectedIndices = gmailList.getSelectionModel().getSelectedIndices();
        for(Object o : selectedIndices) {
            int index = (Integer) o;
            gmailAccount = gmailList.getItems().get(index);
            Calendar calendarId = calendarServiceBuilder.processCredentials(gmailAccount);
            System.out.println("Switched to calendar: " + calendarId);
            createListView(gmailAccount,listCalendars,data);

        }return gmailAccount;
    }

    public void createListView(String account,ListView<String> listCalendars,ObservableList<String> data)throws IOException, GeneralSecurityException {
        List<CalendarListEntry> calendars = calendarClient.getAllCalendars(account);
        for (CalendarListEntry list : calendars) {
            data.add(list.getSummary());
            listCalendars.setItems(data);
        }
    }

    public String switchCalendar(ListView<String> listCalendars, String gmailAccount)throws IOException, GeneralSecurityException{
        String calendarSummary=null;
        ObservableList selectedIndices = listCalendars.getSelectionModel().getSelectedIndices();
        for(Object o : selectedIndices){
            int index = (Integer) o;
            calendarSummary = listCalendars.getItems().get(index);
        }
        System.out.println("selected calendar : " + calendarSummary + " " + gmailAccount);
        String id = calendarClient.getCalendarId(calendarSummary, gmailAccount);
        topPanelLayout.getCalendarName().setText(calendarSummary);
        SecondPanel secondPanel = new SecondPanel();
        secondPanel.getCurrentEventsList().setText("");
        fieldsController.clearFields();
        return id;
    }
}


