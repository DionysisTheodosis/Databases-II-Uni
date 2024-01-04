package com.icsd.controller;

import com.icsd.util.ImageUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

public class AttractionTypeController {

    @FXML
    private ImageView attrTypeImage;

    @FXML
    private Label attrTypeName1;
    
    @FXML
    void initialize() {
       

    }
    
    public void setName(String attraction_name){
        this.attrTypeName1.setText(attraction_name);
    }
    
    public void setImage(String image_url){
         this.attrTypeImage.setImage(ImageUtils.initImageFromExternal(image_url));
    }
    public String getName(){
        return this.attrTypeName1.getText();
    }
}

    