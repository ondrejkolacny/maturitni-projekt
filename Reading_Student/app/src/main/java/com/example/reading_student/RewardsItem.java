package com.example.reading_student;

public class RewardsItem {

        private String name;
        private String desc;
        private int image;

        // Constructor
    public RewardsItem(){}
    public RewardsItem(String name, String desc,int image) {
            this.name = name;
            this.desc = desc;
            this.image = image;
        }

    public void setName(String name) {
        this.name = name;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    // Getters
        public String getHead() {
            return name;
        }
        public String getDesc() {
            return desc;
        }
    }

