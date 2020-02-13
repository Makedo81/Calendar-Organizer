package organizer.client;

import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;
import organizer.service.DriveServiceBuilder;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;

public class DriveClient {

    private DriveServiceBuilder driveServiceBuilder = new DriveServiceBuilder();

    public List<File> getDriveFileList(String user)throws IOException, GeneralSecurityException {
        Drive service = driveServiceBuilder.processCredentials(user);
        String pageToken = null;
        List<File> driveFileList = new ArrayList<>();
        do {
            FileList result = service.files().list()
                    .setFields("*")
                    .setPageToken(pageToken)
                    .execute();
            driveFileList.addAll(result.getItems());
            for (File file : result.getItems()) {
                System.out.println(file.getAlternateLink());
                System.out.println(file.getDownloadUrl());
                System.out.printf("Found fileInput: %s (%s)\n", file.getTitle(), file.getId(), file.getMimeType(), file.getAlternateLink());
            }
            pageToken = result.getNextPageToken();
        } while (pageToken != null);
        return driveFileList;
    }
}
