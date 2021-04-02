package com.example.eliminator.modal;

import java.util.ArrayList;

public class TransactionResponse {
    private String count,page_size;
    private ArrayList<Transactions> results;

    public TransactionResponse(String count, String page_size, ArrayList<Transactions> results) {
        this.count = count;
        this.page_size = page_size;
        this.results = results;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getPage_size() {
        return page_size;
    }

    public void setPage_size(String page_size) {
        this.page_size = page_size;
    }

    public ArrayList<Transactions> getResults() {
        return results;
    }

    public void setResults(ArrayList<Transactions> results) {
        this.results = results;
    }
}
