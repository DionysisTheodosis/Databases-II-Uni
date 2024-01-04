package com.icsd.util;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Paths;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;

public class ImageUtils {

    private ImageUtils() {

    }

    public static Image initImageFromInternal(String image_path) {
        InputStream inputStream = ImageUtils.class.getResourceAsStream(image_path);
        Image image = null;
        if (inputStream != null) {
            image = new Image(inputStream);
        } else {
            System.err.println("Image not found: " + image_path);
        }
        return image;
    }

    public static Image initImageFromExternal(String Image_path) {
        Image image = null;
        try {

            String projectDirectory = System.getProperty("user.dir");
            String imagePath = Paths.get(projectDirectory, Image_path).toString();
            File imageFile = new File(imagePath);

            if (imageFile.exists()) {
                image = new Image(imageFile.toURI().toString());

            } else {
                System.err.println("Image file not found: " + imagePath);
                Alert notFoundAlert = new Alert(Alert.AlertType.NONE);
                notFoundAlert.getButtonTypes().setAll(ButtonType.OK);
                notFoundAlert.setTitle("Δεν βρέθηκε η εικόνα");
                notFoundAlert.setContentText("Σιγουρευτείτε ότι υπάρχει η εικόνα : " + imageFile.getName() + " και βρίσκεται στον φάκελο photos και ο οποίος να βρίσκεται στον ίδιο φάκελο με το εκτελέσιμο αρχείο");
                notFoundAlert.setOnCloseRequest(eve -> {
                    notFoundAlert.hide();
                });
                notFoundAlert.show();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return image;
    }
}
