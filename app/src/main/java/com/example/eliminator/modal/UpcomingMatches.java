package com.example.eliminator.modal;

public class UpcomingMatches {
   private String id,is_join,join_spot,category,entry_fee,winning_prize,map,type,per_kill,spots,date,time;

    public UpcomingMatches(String id, String is_join, String join_spot, String category, String entry_fee, String winning_prize, String map, String type, String per_kill, String spots, String date, String time) {
        this.id = id;
        this.is_join = is_join;
        this.join_spot = join_spot;
        this.category = category;
        this.entry_fee = entry_fee;
        this.winning_prize = winning_prize;
        this.map = map;
        this.type = type;
        this.per_kill = per_kill;
        this.spots = spots;
        this.date = date;
        this.time = time;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIs_join() {
        return is_join;
    }

    public void setIs_join(String is_join) {
        this.is_join = is_join;
    }

    public String getJoin_spot() {
        return join_spot;
    }

    public void setJoin_spot(String join_spot) {
        this.join_spot = join_spot;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getEntry_fee() {
        return entry_fee;
    }

    public void setEntry_fee(String entry_fee) {
        this.entry_fee = entry_fee;
    }

    public String getWinning_prize() {
        return winning_prize;
    }

    public void setWinning_prize(String winning_prize) {
        this.winning_prize = winning_prize;
    }

    public String getMap() {
        return map;
    }

    public void setMap(String map) {
        this.map = map;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPer_kill() {
        return per_kill;
    }

    public void setPer_kill(String per_kill) {
        this.per_kill = per_kill;
    }

    public String getSpots() {
        return spots;
    }

    public void setSpots(String spots) {
        this.spots = spots;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "UpcomingMatches{" +
                "id='" + id + '\'' +
                ", is_join='" + is_join + '\'' +
                ", join_spot='" + join_spot + '\'' +
                ", category='" + category + '\'' +
                ", entry_fee='" + entry_fee + '\'' +
                ", winning_prize='" + winning_prize + '\'' +
                ", map='" + map + '\'' +
                ", type='" + type + '\'' +
                ", per_kill='" + per_kill + '\'' +
                ", spots='" + spots + '\'' +
                ", date='" + date + '\'' +
                ", time='" + time + '\'' +
                '}';
    }
}
