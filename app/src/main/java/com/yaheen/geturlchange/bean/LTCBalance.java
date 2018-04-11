package com.yaheen.geturlchange.bean;

public class LTCBalance {

    private String network;

    private String address;

    private String confirmed_balance;

    private String unconfirmed_balance;

    public String getNetwork() {
        return network;
    }

    public void setNetwork(String network) {
        this.network = network;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getConfirmed_balance() {
        return confirmed_balance;
    }

    public void setConfirmed_balance(String confirmed_balance) {
        this.confirmed_balance = confirmed_balance;
    }

    public String getUnconfirmed_balance() {
        return unconfirmed_balance;
    }

    public void setUnconfirmed_balance(String unconfirmed_balance) {
        this.unconfirmed_balance = unconfirmed_balance;
    }
}
