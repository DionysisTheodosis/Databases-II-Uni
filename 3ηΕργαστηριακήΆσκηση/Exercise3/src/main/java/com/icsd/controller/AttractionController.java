package com.icsd.controller;

import com.icsd.dao.Attraction;
import com.icsd.dao.AttractionReviews;
import com.icsd.dao.DatabaseTransactions;
import com.icsd.dao.OperationTime;
import com.icsd.dao.PicturesAttrraction;
import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;
import com.icsd.util.ImageUtils;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;

public class AttractionController {

    //Arxikopoihsh metavlitwn apo to fxml file
    @FXML
    private Label attractionNameLabel;
    @FXML
    private Label attractionTypeLbl;
    @FXML
    private HBox emailHbox;
    @FXML
    private Label entranceFeeLabel;
    @FXML
    private WebView mapView;
    @FXML
    private VBox operationTimeVBox;
    @FXML
    private Label placeLabel;
    @FXML
    private VBox reviewsVBox;
    @FXML
    private Label uploaderLabel;
    @FXML
    private HBox HboxPictures;
    @FXML
    private Hyperlink websiteLink;
    @FXML
    private ImageView largeImage;

    private Image profilePic;//metavliti exw apo to fxml file
    @FXML
    private HBox telephoneHbox;
    @FXML
    private Hyperlink userHyper;
    @FXML
    private Label numberOfReviews;
    @FXML
    private ImageView starRating;

    //METHODOS AFOTOU GINEI H ARXIKOPOIHSH TOU FXML
    @FXML
    void initialize() {
        setUserLink(); // KALSESMA THS SYNARTHSHS GIA THN ARXIKOPOIHSH ME THN DHMIOURGIA TOU HYPERLINK GIA TO ONOMA TOU XRHSTH
    }

    //METHODOS GIA THN ARXIKOPOIHSH TOU AXIOTHEATOU ME TIMES
    public void setAttractionName(String attractionName) {
        try {
            attractionNameLabel.setText(attractionName);
            //ARXIKOPOIHSH KAI DHMIOURGIA TOU AXIOTHEATOU ME TIMES APO THN VASH
            Attraction attraction = DatabaseTransactions.getAttraction(attractionName);
            setReviews(attraction);
            setAttractionType(attraction.getAttraction_type());
            setMap(attraction.getMap());
            setPlace(attraction.getPlace());
            setEntranceFee(attraction.getEntranceFee());
            setWebsiteLink(attraction.getWebsite());
            setOperationTime(attraction);
            setProfilePic(attraction.getAttr_ProfilePic());
            setEmail(attraction.getEmail());
            setTelephone(attraction.getTelephone());
            setRatingImageReview(attraction.getAverageRating(), starRating);
            setNumberOfReviews(attraction.getNumberOfReviews());
            largeImage.setImage(profilePic);
            uploaderLabel.setText("Φωτογραφία Προφίλ");
            userHyper.setVisible(false);
            //EPANALHPSH GIA THN PROVOLH OLWN TWN EIKONWN
            for (PicturesAttrraction image : attraction.getAllPictures()) {
                FXMLLoader loader3 = new FXMLLoader(getClass().getResource("/com/icsd/main/view/picktureReviewSmall.fxml"));
                AnchorPane pSRoot = loader3.load();
                SmallPictureController pSC = loader3.getController();
                pSC.setPicture(image.getPictureUrl());
                pSC.setUserUpload(image.getUsername());
                image.setPicture(pSC.getPicture());
                //DIAXEIRISH TWN SYMVANTWN PONTIKIOU GIA THN EPILOGH EIKONAS KAI PROVOLH SAN VASIKH ME TO ONOMA TOU XRHSTH H AN EINAI PROFIL
                pSRoot.setOnMouseClicked((MouseEvent event) -> {
                    if (event.getButton() == MouseButton.PRIMARY) {
                        Image temp = pSC.getPicture();
                        pSC.setPicture(largeImage.getImage());
                        largeImage.setImage(temp);
                        if (profilePic == largeImage.getImage()) {
                            uploaderLabel.setText("Φωτογραφία Προφίλ");
                            userHyper.setVisible(false);
                        } else {
                            uploaderLabel.setText("Ανεβασμένη από: ");
                            for (PicturesAttrraction image1 : attraction.getAllPictures()) {
                                if (largeImage.getImage() == image1.getPicture()) {
                                    userHyper.setText(image1.getUsername());
                                }
                            }
                            userHyper.setVisible(true);
                        }
                    }
                });

                HboxPictures.getChildren().add(pSRoot);

            }
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
    }

    //METHODOS GIA THN EISAGWGH TOU TUPOU 
    public void setAttractionType(String attractionType) {
        attractionTypeLbl.setText(attractionType);
    }

    //METHODOS GIA THN EISAGWGH TOU WRARIOU
    public void setOperationTime(Attraction attraction) {

        for (OperationTime dayTime : attraction.getOperationTime()) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/icsd/main/view/operationTime.fxml"));
                HBox operationTimeRoot = loader.load();
                OperationTimeController opTmC = loader.getController();
                opTmC.setDayLabel(dayTime.getDay());
                opTmC.setHourRange(dayTime.getStarts(), dayTime.getEnds());
                operationTimeVBox.getChildren().add(operationTimeRoot);
            } catch (IOException ex) {
                System.err.println(ex.getMessage());
            }
        }
    }

    //METHODOS GIA THN EISAGWGH TOU TELOS EISODOU
    public void setEntranceFee(double entranceFee) {
        System.out.println(entranceFee);

        if (entranceFee == 0) {

            this.entranceFeeLabel.setText("Δωρεάν");
        } else {
            this.entranceFeeLabel.setText(String.valueOf(entranceFee) + "€");

        }
    }

    //METHODOS GIA THN EISAGWGH TOU EMBEDED URL GIA TON XARTH KAI DISPLAY TOU XARTH
    public void setMap(String embeded_iframe) {
        mapView.getEngine().loadContent(embeded_iframe);
    }

    //METHODOS GIA THN EISAGWGH THS PERIOXHS
    public void setPlace(String place) {
        placeLabel.setText(place);
    }

    //METHODOS GIA THN EISAGWGH TWN KRITIKWN
    public void setReviews(Attraction attraction) {
        //PROSPELASH KATHE KRITIKHS
        for (AttractionReviews review : attraction.getAttrReviews()) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/icsd/main/view/review.fxml"));
                VBox reviewRoot = loader.load();
                ReviewController reviewC = loader.getController();
                reviewC.setRatingImageReview(review.getRating());
                reviewC.setUserProfilePic(review.getUserPicture());
                reviewC.setUsername(review.getUsername());
                String[] reviewImages = DatabaseTransactions.getReviewPictures(review.getAttractionName(), review.getUsername());
                if (reviewImages.length != 0) {
                    reviewC.setImages(reviewImages);
                }
                if (review.getComment() != null) {
                    FXMLLoader rloader = new FXMLLoader(getClass().getResource("/com/icsd/main/view/reviewS.fxml"));
                    ScrollPane reviewSRoot = rloader.load();
                    ReviewCommentController reviewSC = rloader.getController();
                    reviewSC.setReviewComment(review.getComment());
                    reviewC.setCommentPane(reviewSRoot);
                }

                reviewC.setReviewTimestamp(review.getCreationTime());

                reviewsVBox.getChildren().add(reviewRoot);

            } catch (IOException ex) {
                System.err.println(ex.getMessage());
            }
        }
    }

    //METHODOS GIA THN EISAGWGH TOU EMAIL
    public void setEmail(String[] emails) {
        for (String email : emails) {
            Hyperlink emailLink = new Hyperlink(email);
            emailLink.setFocusTraversable(false);
            emailLink.setVisible(true);
            emailLink.setOnMouseClicked(event -> {
                if (event.getButton() == MouseButton.PRIMARY) {
                    try {
                        Desktop.getDesktop().mail(new URI("mailto:" + email));
                    } catch (URISyntaxException | IOException ex) {
                        System.err.println(ex.getMessage());
                    }
                }
            });
            emailHbox.getChildren().add(emailLink);
        }

    }

    //METHODOS GIA THN EISAGWGH TOU THLEFWNOU
    public void setTelephone(String[] telephones) {
        for (String telephone : telephones) {
            Label telLabel = new Label(telephone);
            telLabel.getStyleClass().add("tel");
            telephoneHbox.getChildren().add(telLabel);

        }

    }

    //METHODOS GIA THN EISAGWGH THS ISTOSELIDAS
    public void setWebsiteLink(String website) {
        websiteLink.setText(website);
        websiteLink.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY) {
                try {
                    Desktop.getDesktop().browse(new URI(website));
                } catch (URISyntaxException | IOException ex) {
                    System.err.println(ex.getMessage());
                }
            }
        });
    }

    //METHODOS GIA THN EISAGWGH THS FWTOGRAFIAS PROFIL
    public void setProfilePic(String image_url) {
        this.profilePic = ImageUtils.initImageFromExternal(image_url);
    }

    //METHODOS GIA THN EISAGWGH TOU ARITHMOU TWN KRITIKWN
    public void setNumberOfReviews(int numberOfReviews) {
        this.numberOfReviews.setText("(" + String.valueOf(numberOfReviews) + ")");
    }

    //METHODOS GIA THN EISAGWGH TOU USERNAME
    private void setUserLink() {
        userHyper.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY) {
                UserDialogController userDialog = new UserDialogController();
                userDialog.setUser(userHyper.getText());
                userDialog.showDialog();

            }
        });
    }

    //METHODOS GIA THN EISAGWGH THS MESHS VATHMOLOGIAS
    private void setRatingImageReview(double rating, ImageView ratingImageView) {
        double roundedRating = (double) Math.round((rating - 0.1) * 2) / 2.0;
        String imageName = String.valueOf(roundedRating).replace(".", ",") + "stars.png";
        String imagePath = "/com/icsd/pictures/starRating/" + imageName;
        ratingImageView.setImage(ImageUtils.initImageFromInternal(imagePath));
        Tooltip tooltip = new Tooltip(String.valueOf(rating));
        Tooltip.install(ratingImageView, tooltip);
    }

}
