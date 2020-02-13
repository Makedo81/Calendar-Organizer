package organizer.layouts;

import javafx.scene.control.TextArea;

public class FieldsController {

    private FourthPanel fourthPanel = new FourthPanel();

    public boolean checkField() {
        if(fourthPanel.getSummary().getText().equals("")){
            fourthPanel.getSummary().setId("empty-text-field");
            fourthPanel.getSummary().setPromptText("field cannot be empty");
            System.out.println("Summary - Nothing to display");
            return false;
        }else
        {
            fourthPanel.getSummary().setStyle("");
            fourthPanel.getSummary().setPromptText("");
        }
        return true;
    }

    public String getSummary() {
        String summaryText = "";
        if(fourthPanel.getSummary().getText().equals("")){
            fourthPanel.getSummary().setId("empty-text-field");
            fourthPanel.getSummary().setPromptText("field cannot be empty");
            System.out.println("Summary - Nothing to display");
        }else
        { summaryText = fourthPanel.getSummary().getText(); }
        System.out.println(summaryText);
        return summaryText; }

    public String getLocation() {
        String locationText = "";
        if(fourthPanel.getLocation().getText().equals("")){
            System.out.println("Location - Nothing to display");
        }else
        { locationText = fourthPanel.getLocation().getText();
            System.out.println(locationText); }
        return locationText; }

    public String getDate() {
        String dateText = "";
        if(fourthPanel.getDateStart().getText().equals("")){
            System.out.println(" Date - Nothing to display");
        }else
        { dateText = fourthPanel.getDateStart().getText();
            System.out.println(dateText); }
        return dateText; }

    public String getDescription() {
        String descriptionText = "";
        if(fourthPanel.getDescription().getText().equals("")){
            System.out.println("Description - Nothing to display");
        }else
        { descriptionText = fourthPanel.getDescription().getText();
            System.out.println(descriptionText); }
        return descriptionText; }


    public String checkId(String id){
        String calendarId = id;
        if (id.equals("")) {
            calendarId = "primary";
        }return calendarId;
    }

    public String checkUserIfEmpty(String user){
        System.out.println("Current user:  " + user);
        if(user == null){
            System.out.println("No user assigned to application");
        }else
            System.out.println("User " + user);
        return user;
    }

    public void clearFields() {
        SecondPanel secondPanel = new SecondPanel();
        ThirdPanel thirdPanel = new ThirdPanel();
        fourthPanel.getSummary().clear();
        fourthPanel.getLocation().clear();
        fourthPanel.getDateStart().clear();
        fourthPanel.getDescription().clear();
        fourthPanel.getFileInput().clear();
        TextArea textArea = secondPanel.getCurrentEventsList();
        textArea.clear();
        thirdPanel.getEventListView().getItems().clear();
        fourthPanel.getFileInput().clear();
        if (fourthPanel.getImageView().getImage() != null) {
            fourthPanel.getImageView().setImage(null);
        }else System.out.println("No image found");
    }

    public void clearFieldsAfterAccountSwitched() {
        FirstPanel firstPanel = new FirstPanel();
        SecondPanel secondPanel = new SecondPanel();
        ThirdPanel thirdPanel = new ThirdPanel();
        TopPanelLayout topPanelLayout = new TopPanelLayout();
        TextArea textArea = secondPanel.getCurrentEventsList();
        textArea.clear();
        fourthPanel.getSummary().clear();
        fourthPanel.getLocation().clear();
        fourthPanel.getDateStart().clear();
        fourthPanel.getDescription().clear();
        fourthPanel.getFileInput().clear();
        fourthPanel.getFileInput().clear();
        if (fourthPanel.getImageView().getImage() != null) {
            fourthPanel.getImageView().setImage(null);
        }else System.out.println("No image found");
        topPanelLayout.getCalendarName().setText("");
        thirdPanel.getEventListView().getItems().clear();
        firstPanel.getListEventTable().clear();
    }
}
