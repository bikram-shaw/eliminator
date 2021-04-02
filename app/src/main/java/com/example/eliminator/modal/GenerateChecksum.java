package com.example.eliminator.modal;

public class GenerateChecksum {
private String order_id,checksum;

    public GenerateChecksum(String order_id, String checksum) {
        this.order_id = order_id;
        this.checksum = checksum;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getChecksum() {
        return checksum;
    }

    public void setChecksum(String checksum) {
        this.checksum = checksum;
    }

    @Override
    public String toString() {
        return "GenerateChecksum{" +
                "order_id='" + order_id + '\'' +
                ", checksum='" + checksum + '\'' +
                '}';
    }
}
