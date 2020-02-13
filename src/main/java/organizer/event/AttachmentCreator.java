package organizer.event;

import com.google.api.services.calendar.model.EventAttachment;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import organizer.client.DriveClient;
import organizer.service.DriveServiceBuilder;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;

public class AttachmentCreator {

    public List<EventAttachment> createAttachment(String url, String user)throws IOException, GeneralSecurityException {
        DriveClient driveClient = new DriveClient();
        DriveServiceBuilder driveServiceBuilder = new DriveServiceBuilder();
        Drive service = driveServiceBuilder.processCredentials(user);
        List<File> filesList = new ArrayList<>();
        List<EventAttachment> attachments = new ArrayList<>();
        if(url.equals("")){
            System.out.println("Empty url field");
            attachments = new ArrayList<>();
        }else {
            List<File> list = driveClient.getDriveFileList(user);
            System.out.println(list.size());
            for (File file : list) {
                System.out.println("(?i).*" + file.getId());
                if (url.matches("(?i).*" + file.getId())|| url.matches("(?i).*" + file.getId() + "(.*)")) {
                    System.out.println("adding file to list");
                    filesList.add(file);
                }else System.out.println(" no matching file found");
            }
            String fileId = filesList.get(0).getId();
            File file = service.files().get(fileId).execute();
            attachments.add(setAttachment(file));
            System.out.println("create attachment with file id " + attachments.get(0).getFileId());
        }
        return attachments;
    }

    public EventAttachment setAttachment(File file){
        return new EventAttachment()
                .setFileUrl(file.getAlternateLink())
                .setMimeType(file.getMimeType())
                .setIconLink(file.getIconLink())
                .setTitle(file.getDescription());
    }
}
