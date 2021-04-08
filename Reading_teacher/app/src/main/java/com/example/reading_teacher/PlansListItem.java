package com.example.reading_teacher;

public class PlansListItem {
    public String planName;
    public String planID;

    public PlansListItem(){}
    public PlansListItem(String planName){
        this.planName = planName;
    }

    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }

    public String getPlanID() {
        return planID;
    }

    public void setPlanID(String planID) {
        this.planID = planID;
    }
}
