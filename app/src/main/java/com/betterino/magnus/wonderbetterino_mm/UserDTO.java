package com.betterino.magnus.wonderbetterino_mm;

/**
 * Created by Magnus on 09-10-2017.
 */

public class UserDTO {
    private String name;
    private int wallet;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getWallet() {
        return wallet;
    }

    public void setWallet(int wallet) {
        this.wallet = wallet;
    }

    public UserDTO() {

    }

    public UserDTO(String name, int wallet) {
        this.name = name;
        this.wallet = wallet;
    }
}
