
package com.mpay.wallet.View.Fragment.home.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RecentTransaction {
    private String transactionId;
    private String name;
    private int profilePic;
    private String date;
    private String amount;
    private String transaction_type;


    public RecentTransaction(String name, String date, String amount, String transaction_type, int profilePic) {
        this.name = name;
        this.profilePic = profilePic;
        this.date = date;
        this.amount = amount;
        this.transaction_type = transaction_type;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(int profilePic) {
        this.profilePic = profilePic;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }


    public String getTransaction_type() {
        return transaction_type;
    }

    public void setTransaction_type(String transaction_type) {
        this.transaction_type = transaction_type;
    }

}
