package com.example.eliminator.modal;

public class UserDetails {
    String token,email,mobile,refer_code;

    public UserDetails(String token, String email, String mobile, String refer_code) {
        this.token = token;
        this.email = email;
        this.mobile = mobile;
        this.refer_code = refer_code;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getRefer_code() {
        return refer_code;
    }

    public void setRefer_code(String refer_code) {
        this.refer_code = refer_code;
    }
}
