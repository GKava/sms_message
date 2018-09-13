package com.example.savemessage;


import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;


    @Database(entities = {Author.class}, version = 1)
    public abstract class AppDatabase extends RoomDatabase {
        public abstract AuthorDao authorDao();
    }

