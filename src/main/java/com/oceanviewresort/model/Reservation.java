package com.oceanviewresort.model;
public class Reservation {
    private int reservation_id;
    private String guest_name;
    private String guest_address;
    private String contact_number;
    private String room_type;
    private String check_in;
    private String check_out;
    private double total_price;

    // Constructor
    public Reservation() {}

    public Reservation(String guest_name, String guest_address, String contact_number,
                       String room_type, String check_in, String check_out,double total_price) {
        this.guest_name = guest_name;
        this.guest_address = guest_address;
        this.contact_number = contact_number;
        this.room_type = room_type;
        this.check_in = check_in;
        this.check_out = check_out;
        this.total_price = total_price;
    }

    // Getters
    public int getReservation_id() {
        return reservation_id;
    }

    public String getGuest_name() {
        return guest_name;
    }

    public String getGuest_address() {
        return guest_address;
    }

    public String getContact_number() {
        return contact_number;
    }

    public String getRoom_type() {
        return room_type;
    }

    public String getCheck_in() {
        return check_in;
    }

    public String getCheck_out() {
        return check_out;
    }
    public double getTotal_price() {
        return total_price;
    }

    // Setters
    public void setReservation_id(int reservation_id) {
        this.reservation_id = reservation_id;
    }

    public void setGuest_name(String guest_name) {
        this.guest_name = guest_name;
    }

    public void setGuest_address(String guest_address) {
        this.guest_address = guest_address;
    }

    public void setContact_number(String contact_number) {
        this.contact_number = contact_number;
    }

    public void setRoom_type(String room_type) {
        this.room_type = room_type;
    }

    public void setCheck_in(String check_in) {
        this.check_in = check_in;
    }

    public void setCheck_out(String check_out) {
        this.check_out = check_out;
    }
     public void setTotal_price(double total_price) {
        this.total_price = total_price;
    }
}