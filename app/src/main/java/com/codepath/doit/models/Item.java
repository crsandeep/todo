package com.codepath.doit.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table(name = "Items")
public class Item extends Model {
    @Column(name = "Subject")
    public String subject;

    // Make sure to have a default constructor for every ActiveAndroid model
    @SuppressWarnings("unused")
    public Item(){
        super();
    }

    public Item(String subject){
        super();
        this.subject = subject;
    }
}
