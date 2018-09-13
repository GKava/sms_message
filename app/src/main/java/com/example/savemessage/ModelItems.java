package com.example.savemessage;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ModelItems {
    boolean phoneUser;
    private String author;

    public ModelItems (String author, boolean phoneUser){
        this.author = author;
        this.phoneUser = phoneUser;
    }

    public String getAuthor(){
        return author;
    }

    public static List<ModelItems> getFakeItems(){
        ArrayList<ModelItems> itemList = new ArrayList<>();
//        itemList.add(new ModelItems("One message",false));
//        itemList.add(new ModelItems("Two message",true));
//        itemList.add(new ModelItems("Three message",false));
        return itemList;
    }
}
