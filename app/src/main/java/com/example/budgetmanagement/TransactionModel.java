package com.example.budgetmanagement;

public class TransactionModel {
    public TransactionModel(String id, String note, String amount, String type,String date) {
        this.id = id;
        this.note = note;
        this.amount = amount;
        this.type = type;
        this.date = date;
    }

    public  TransactionModel(){}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    private String id;
    private String note;
    private String amount;
    private String type;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    private String date;
}
