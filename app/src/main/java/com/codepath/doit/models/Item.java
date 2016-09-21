package com.codepath.doit.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table(name = "Items")
public class Item extends Model implements Comparable<Item> {
    @Column(name = "Subject")
    public String subject;

    @Column(name = "DueDate")
    public String dueDate;

    @Column(name = "DueTime")
    public String dueTime;

    @Column(name = "Priority")
    public Priority priority;

    // Make sure to have a default constructor for every ActiveAndroid model
    @SuppressWarnings("unused")
    public Item(){
        super();
    }

    public Item(String subject){
        super();
        this.subject = subject;
    }

    @Override
    public int compareTo(Item item) {
        return this.priority.getValue()- item.priority.getValue();
    }
}
