package com.example.eliminator.modal;

public class Wallet {
    private String wal_bal,win_bal;

    public Wallet(String wal_bal, String win_bal) {
        this.wal_bal = wal_bal;
        this.win_bal = win_bal;
    }

    public String getWal_bal() {
        return wal_bal;
    }

    public void setWal_bal(String wal_bal) {
        this.wal_bal = wal_bal;
    }

    public String getWin_bal() {
        return win_bal;
    }

    public void setWin_bal(String win_bal) {
        this.win_bal = win_bal;
    }

    @Override
    public String toString() {
        return "Wallet{" +
                "wal_bal='" + wal_bal + '\'' +
                ", win_bal='" + win_bal + '\'' +
                '}';
    }
}

