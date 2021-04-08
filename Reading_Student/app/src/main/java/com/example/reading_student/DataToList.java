package com.example.reading_student;



public class DataToList {
    public String planName;
    String groupName;

    public DataToList() {}
    public DataToList(String planName,String groupName) {
            this.planName = planName;
            this.groupName = groupName;


        }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getPlanName () {

            return planName;
        }

        public void setPlanName (String planName){

            this.planName = planName;
        }


}