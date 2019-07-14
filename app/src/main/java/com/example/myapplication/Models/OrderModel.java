package com.example.myapplication.Models;

public class OrderModel {
    private String _id;
    private String name, contactno;
    private String deliverydate;
    private String quantity;
    private String deliverylocation, otherdetails, user_email;

    public OrderModel(String name, String contactno, String deliverydate, String quantity, String deliverylocation, String otherdetails, String user_email) {
        this.name = name;
        this.contactno = contactno;
        this.deliverydate = deliverydate;
        this.quantity = quantity;
        this.deliverylocation = deliverylocation;
        this.otherdetails = otherdetails;
        this.user_email = user_email;
    }

    public OrderModel(String _id, String name, String contactno, String deliverydate, String quantity, String deliverylocation, String otherdetails, String user_email) {
        this._id = _id;
        this.name = name;
        this.contactno = contactno;
        this.deliverydate = deliverydate;
        this.quantity = quantity;
        this.deliverylocation = deliverylocation;
        this.otherdetails = otherdetails;
        this.user_email = user_email;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContactno() {
        return contactno;
    }

    public void setContactno(String contactno) {
        this.contactno = contactno;
    }

    public String getDeliverydate() {
        return deliverydate;
    }

    public void setDeliverydate(String deliverydate) {
        this.deliverydate = deliverydate;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getDeliverylocation() {
        return deliverylocation;
    }

    public void setDeliverylocation(String deliverylocation) {
        this.deliverylocation = deliverylocation;
    }

    public String getOtherdetails() {
        return otherdetails;
    }

    public void setOtherdetails(String otherdetails) {
        this.otherdetails = otherdetails;
    }

    public String getUser_email() {
        return user_email;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }
}