package com.codepath.doit.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by sraveesh on 9/5/16.
 */
@Table(name = "Items")
public class Item extends Model {
    @Column(name = "Subject")
    public String subject;

    // Make sure to have a default constructor for every ActiveAndroid model
    public Item(){
        super();
    }

    public Item(String subject){
        super();
        this.subject = subject;
    }
}
