
package com.thoughtworks.pos.bin;

import com.thoughtworks.pos.common.EmptyShoppingCartException;
import com.thoughtworks.pos.domains.Pos;
import com.thoughtworks.pos.domains.ShoppingChart;
import com.thoughtworks.pos.services.services.InputParser;
import com.thoughtworks.pos.domains.Item;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by Administrator on 2016/6/22.
 */
public class PosCLI {
	 
    public static void main (String args[]) throws IOException, EmptyShoppingCartException {
        Item xuebi=new Item("ITEM00001","雪碧","瓶",2.00);
        Item dianchi=new Item("ITEM00002","电池","个",1.00);
        ShoppingChart S=new ShoppingChart();
        S.add(xuebi);
        S.add(dianchi);
        Pos p=new Pos();
        String sl = p.getShoppingList(S);
        System.out.print("需求1\n");
        System.out.print(sl);
    	Item cokeCola = new Item("ITEM000000", "可口可乐", "瓶", 3.00,0.8);
        ShoppingChart shoppingChart = new ShoppingChart();
        shoppingChart.add(cokeCola);
        Pos pos = new Pos();
        String shoppingList = pos.getShoppingList(shoppingChart);
        System.out.print("需求2\n");
        System.out.print(shoppingList);

        if(args.length!=0){
        InputParser inputParser = new InputParser(new File(args[0]), new File(args[1]));
       shoppingChart = inputParser.parser();
        }
      pos = new Pos();
       shoppingList = pos.getShoppingList(shoppingChart);
       System.out.print("builded from file\n");
        System.out.print(shoppingList);
        
    }
}