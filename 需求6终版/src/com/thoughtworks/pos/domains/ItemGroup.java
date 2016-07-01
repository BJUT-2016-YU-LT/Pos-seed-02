package com.thoughtworks.pos.domains;

import java.util.List;
import com.thoughtworks.pos.domains.Item;
import com.thoughtworks.pos.domains.user;
/**
 * Created by Administrator on 2014/12/31.
 */
public class ItemGroup {
    private List<Item> items;
   
    private int quanitty;
    private int gift;
    private user user = new user();
 
    public void setUser(user user){
        this.user = user;
    }
    public user getUser(){
        return user;
    }

    public ItemGroup(List<Item> items) {
        this.items = items;
    }

    public String groupName() {
        return items.get(0).getName();
    }

    public int groupSize() {
        return items.size();
    }

    public String groupUnit() {
        return items.get(0).getUnit();
    }

    public double groupPrice() {
        return items.get(0).getPrice();
    }
    public boolean GroupIsPromotion(){return items.get(0).getIsPromotion();}
   
    public double subTotal() {
        double result = 0.00;
        for (Item item : items){
        	
        	if(item.getIsPromotion()==false){
        		result += item.getPrice() * item.getDiscount()*item.getvipDiscount();
        	}
        	else
        		result+=item.getPrice()*item.getDiscount();
        		
        		
            }
        if(GroupIsPromotion()&&groupSize()>=2)
            result-=groupPrice();
       
        
        return result;
    }

    public double saving() {
        double result = 0.00;
        for (Item item : items)
            result += item.getPrice() * (1 - (item.getDiscount()*item.getvipDiscount()));
        return result;
    }
    public void addOne(){
        quanitty++;
        for (Item item : items){
        if(item.getIsPromotion()&&quanitty>1){
            gift = 1;
        }
    }
        }
}
