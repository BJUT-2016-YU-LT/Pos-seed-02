package com.thoughtworks.pos.domains;

import java.util.ArrayList;

/**
 * Created by Administrator on 2014/12/28.
 */
public class ShoppingChart {
    private ArrayList<Item> items = new ArrayList<Item>();
    private user user = new user();

    public void add(Item item) {
        this.items.add(item);
    }

    public user getUser() { return user; }

    public void setUser(user user){
        this.user = user;
    }

    public ArrayList<Item> getItems() {
        return items;
    }
}
