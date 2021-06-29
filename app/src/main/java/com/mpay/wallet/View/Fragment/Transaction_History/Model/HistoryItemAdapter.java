package com.mpay.wallet.View.Fragment.Transaction_History.Model;

public class HistoryItemAdapter {
    private String transactionId;
    private String payto;
    private int profilePic;
    private String date;
    private String amount;
    private String transaction_status;
    private String transaction_type;
    public String getPayto() {
        return payto;
    }

    public void setPayto(String payto) {
        this.payto = payto;
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

    public String getTransaction_status() {
        return transaction_status;
    }

    public void setTransaction_status(String transaction_status) {
        this.transaction_status = transaction_status;
    }

    public String getTransaction_type() {
        return transaction_type;
    }

    public void setTransaction_type(String transaction_type) {
        this.transaction_type = transaction_type;
    }
}
