package com.icsd.controller;


import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class OperationTimeController {
    
    @FXML
    private Label hoursLabel;
    @FXML 
    private Label dayLabel; 

    @FXML 
    void initialize() {
       

    }
    public void setDayLabel(String day){
        dayLabel.setText(day);
    }
    public void setHourRange(String opens,String closing){
        hoursLabel.setText(opens+"-"+closing);
    }
   

}
