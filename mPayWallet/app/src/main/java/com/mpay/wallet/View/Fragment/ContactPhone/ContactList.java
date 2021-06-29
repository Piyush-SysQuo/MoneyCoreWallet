package com.mpay.wallet.View.Fragment.ContactPhone;

import java.io.Serializable;

public class ContactList implements Comparable<ContactList> {

    private String name;
    private String number;
    private int images;

    public ContactList(String name, String number) {
        this.name = name;
        this.number = number;
//        this.images = images;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public int getImages() {
        return images;
    }

    public void setImages(int images) {
        this.images = images;
    }

    @Override
    public int compareTo(ContactList other) {
        return this.name.compareTo(other.name);
    }
}
