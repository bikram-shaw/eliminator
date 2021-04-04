package com.example.eliminator.modal;

public class RoomDetails {
    String room_id,password;

    public String getRoom_id() {
        return room_id;
    }

    public void setRoom_id(String room_id) {
        this.room_id = room_id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public RoomDetails(String room_id, String password) {
        this.room_id = room_id;
        this.password = password;
    }
}
