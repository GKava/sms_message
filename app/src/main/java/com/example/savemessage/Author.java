package com.example.savemessage;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;



    @Entity (tableName = "author")
    public class Author {

    @PrimaryKey
        private int id;
        private String author;
        private boolean phoneUser;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public boolean isPhoneUser() {
            return phoneUser;
        }

        public void setPhoneUser(boolean phoneUser) {
            this.phoneUser = phoneUser;
        }

    }


