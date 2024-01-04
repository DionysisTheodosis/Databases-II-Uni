package com.icsd.dao;


public class OperationTime {
   private String day;
   private String starts;
   private String ends;

    public OperationTime(String day, String starts, String ends) {
        this.day = day;
        this.starts = starts;
        this.ends = ends;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getStarts() {
        return starts;
    }

    public void setStarts(String starts) {
        this.starts = starts;
    }

    public String getEnds() {
        return ends;
    }

    public void setEnds(String ends) {
        this.ends = ends;
    }
   
}
