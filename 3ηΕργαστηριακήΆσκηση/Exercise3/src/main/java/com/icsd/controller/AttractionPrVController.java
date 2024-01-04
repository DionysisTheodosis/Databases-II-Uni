package com.icsd.controller;

import com.icsd.util.ImageUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class AttractionPrVController {

    @FXML
    private Label attractionName;
    @FXML
    private AnchorPane photoAnchor1;
    @FXML
    private ImageView photoImage;
    @FXML
    private Label ratingId;
    @FXML
    private ImageView ratingStar;

    void initialize() {

    }

    public void setAttractionPicture(String pictureUrl) {
        photoImage.setImage(ImageUtils.initImageFromExternal(pictureUrl));
    }

    public void setAttractionName(String attractionName) {
        this.attractionName.setText(attractionName);
    }

    public void setSumReviews(int SumReviews) {
        this.ratingId.setText("Αρ.Κριτικών: (" + String.valueOf(SumReviews) + ")");
    }

    public void setRating(double rating) {

        double roundedRating = (double) Math.round((rating - 0.1) * 2) / 2;
        String imageName = String.valueOf(roundedRating);
        imageName = imageName.replace('.', ',') + "stars.png";
        this.ratingStar.setImage(ImageUtils.initImageFromInternal("/com/icsd/pictures/starRating/" + imageName));
        Tooltip tooltip = new Tooltip(String.valueOf(rating));
        Tooltip.install(this.ratingStar, tooltip);

}

public AnchorPane getAnchorPane() {
        return photoAnchor1;
    }

    public String getAttractionName() {
        return this.attractionName.getText();
    }

}
