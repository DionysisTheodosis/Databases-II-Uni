package com.icsd.controller;

import com.icsd.dao.DatabaseTransactions;
import com.icsd.dao.Review;
import com.icsd.dao.User;
import com.icsd.util.ImageUtils;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;

public class UserDialogController {

    private final Dialog userDialog;
    private String username;
 
    public UserDialogController() {

        userDialog = new Dialog();
        userDialog.setTitle("Προεπισκόπηση προφίλ χρήστη");
        userDialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
        userDialog.initModality(Modality.APPLICATION_MODAL);

    }

    public void setUser(String username) {
        this.username = username;
        try {
            User user = DatabaseTransactions.getUser(username);
            Review[] reviews = DatabaseTransactions.getReviewsBsdUser(username);

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/icsd/main/view/userProfile.fxml"));
            AnchorPane userProfileRoot = loader.load(); // Create a new Dialog
            UserProfileController userProfileC = loader.getController();
            userProfileC.getUsername().setText(user.getUsername());
            userProfileC.getName().setText(user.getName());

            userProfileC.getLastName().setText(user.getLastname());
            try {

                userProfileC.getProfileImageView().setImage(ImageUtils.initImageFromExternal(user.getProfilePic()));
                for (Review review : reviews) {
                    String[] reviewPictures = DatabaseTransactions.getReviewPictures(review.getAttractionNameS(), review.getUsernameS());

                    FXMLLoader loaderR = new FXMLLoader(getClass().getResource("/com/icsd/main/view/userProfileReview.fxml"));
                    VBox userProfileRRoot = loaderR.load(); // Create a new Dialog
                    UserProfileReController userProfileRC = loaderR.getController();
                    userProfileRC.getAttractionNameHyperLink().setText(review.getAttractionNameS());
                   

                    userProfileRC.getcreationTimeLabel().setText(review.getCreationTime());
                    setRatingImageReview(review.getRating(), userProfileRC.getRatingImageView());
                    if (reviewPictures.length > 0) {
                        userProfileRC.getImagePane().getChildren().add(getUserReviewPics(review));
                    }
                    if (review.getComment() != null) {
                        FXMLLoader loaderCom = new FXMLLoader(getClass().getResource("/com/icsd/main/view/reviewS.fxml"));
                        ScrollPane reviewCommentRoot = loaderCom.load(); // Create a new Dialog
                        ReviewCommentController reviewCommentC = loaderCom.getController();
                        reviewCommentC.setReviewComment(review.getComment());

                        userProfileRC.getcommentPane().getChildren().add(reviewCommentRoot);
                    }
                    userProfileC.getReviewsVbox().getChildren().add(userProfileRRoot);

                  
                }

            } catch (Exception e) {
               System.err.println(e.getMessage());
            }

            userDialog.getDialogPane().setContent(userProfileRoot);

        } catch (IOException ex) {
           System.err.println(ex.getMessage());
        }
    }

    public void showDialog() {
        userDialog.showAndWait();
    }

    private ScrollPane getUserReviewPics(Review review) {
        try {
            FXMLLoader loaderPics = new FXMLLoader(getClass().getResource("/com/icsd/main/view/userReviewPics.fxml"));
            ScrollPane userReviewPicsRoot = loaderPics.load();
            UserReviewPicsController userReviewPicsC = loaderPics.getController();
            String[] reviewPictures = DatabaseTransactions.getReviewPictures(review.getAttractionNameS(), review.getUsernameS());
            if (reviewPictures != null) {

                userReviewPicsC.addPicture(reviewPictures);

            }
            userReviewPicsRoot.setVisible(true);
            return userReviewPicsRoot;
        } catch (IOException ex) {

          System.err.println(ex.getMessage());
            return null;
        }

    }

    private void setRatingImageReview(double rating, ImageView ratingImageView) {
        double roundedRating = (double) Math.round((rating - 0.1) * 2) / 2.0;
        String imageName = String.valueOf(roundedRating).replace(".", ",") + "stars.png";
        String imagePath = "/com/icsd/pictures/starRating/" + imageName;
        ratingImageView.setImage(ImageUtils.initImageFromInternal(imagePath));
        Tooltip tooltip = new Tooltip(String.valueOf(rating));
        Tooltip.install(ratingImageView, tooltip);

    }

  

}
