package com.example.reading_teacher;

public class GroupsListItem {

    public String groupName;
    public String groupID;

    public GroupsListItem() {}
    public GroupsListItem(String groupName) {
        this.groupName = groupName;


    }

    public String getGroupID() {
        return groupID;
    }

    public void setGroupID(String groupID) {
        this.groupID = groupID;
    }

    public String getGroupName () {

        return groupName;
    }

    public void setGroupName (String groupName){

        this.groupName = groupName;
    }
}
