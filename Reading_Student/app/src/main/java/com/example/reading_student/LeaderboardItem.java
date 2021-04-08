package com.example.reading_student;

public class LeaderboardItem {
    public String user;
    public String percent;

    public LeaderboardItem() {}

    public LeaderboardItem(String user, String percent) {
        this.user = user;
        this.percent = percent;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPercent() {
        return percent;
    }

    public void setPercent(String percent) {
        this.percent = percent;
    }
}
