package com.example.savemessage;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface AuthorDao {

    @Insert
     void inserAllAuthor (Author author);

    @Query(" SELECT * FROM author") //tableName
     List<ModelItems> getAllAuthor();

    @Delete
    void deleteAll (Author author);


}
