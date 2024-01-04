package com.icsd.controller;


import com.icsd.util.ImageUtils;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

public class ReviewController {

    @FXML
    private ImageView ratingImageReview;
    @FXML
    private Label reviewTimestamp;
    @FXML
    private AnchorPane commentAnch;
    @FXML
    private ImageView userReviewProfilePic;
    @FXML
    private Hyperlink usernameUserReviewProfile;
    @FXML
    private VBox reviewVBox;
    @FXML
    private AnchorPane imageRPane;

    @FXML
    void initialize() {

    }

    public void setRatingImageReview(double rating) {
        double roundedRating = (double) Math.round((rating - 0.1) * 2) / 2.0;
        String imageName = String.valueOf(roundedRating).replace(".", ",") + "stars.png";

        String imagePath = "/com/icsd/pictures/starRating/" + imageName;

        this.ratingImageReview.setImage(ImageUtils.initImageFromInternal(imagePath));

        Tooltip tooltip = new Tooltip(String.valueOf(rating));
        Tooltip.install(this.ratingImageReview, tooltip);

    }

    public void setUserProfilePic(String image_url) {
        image_url = image_url.substring(0, image_url.length() - 4);
        image_url += "_32x32.jpg";
        this.userReviewProfilePic.setImage(ImageUtils.initImageFromExternal(image_url));

    }

    public void setUsername(String username) {
        usernameUserReviewProfile.setText(username);
        usernameUserReviewProfile.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY) {
                UserDialogController userDialog = new UserDialogController();
                userDialog.setUser(usernameUserReviewProfile.getText());
                userDialog.showDialog();

            }
        });
    }

    public void setReviewTimestamp(String timestamp) {
        reviewTimestamp.setText(timestamp);
    }

    public VBox getReviewVBox() {
        return reviewVBox;
    }

    public void setCommentPane(ScrollPane commentPane) {
        commentAnch.getChildren().add(commentPane);
    }

    public void setImages(String[] images_url) {

        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/icsd/main/view/userReviewPics.fxml"));
            ScrollPane pRoot = loader.load();
            UserReviewPicsController pC = loader.getController();
            pC.addPicture(images_url);
            imageRPane.getChildren().add(pRoot);

        } catch (IOException ex) {
           System.err.println(ex.getMessage());
        }

    }

}
