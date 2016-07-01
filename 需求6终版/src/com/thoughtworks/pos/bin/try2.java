package com.thoughtworks.pos.bin;
import com.thoughtworks.pos.common.EmptyShoppingCartException;
import com.thoughtworks.pos.domains.Item;
import com.thoughtworks.pos.domains.Pos;
import com.thoughtworks.pos.domains.ShoppingChart;
import com.thoughtworks.pos.services.services.InputParser;
import com.thoughtworks.pos.domains.user;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
public class try2 {
	 private static  void WriteToFile(File file, String content) throws FileNotFoundException {
	        PrintWriter printWriter = new PrintWriter(file);
	        printWriter.write(content);
	        printWriter.close();
	    }
	 public boolean find() throws IOException{
		 BufferedReader in = new BufferedReader(new FileReader("fixtures/sampleIndexes.json"));
		 	String line = null;
		 	
		 	while((line = in.readLine())!=null){ 
		 		if(line.contains("USER0001")){
		 			line=line+2;
		 			if(line.contains("true"))
		 				return true;
		 			    return false;
		 		}
		 		if(line.contains("USER0002")){
		 			line=line+2;
		 			if(line.contains("true"))
		 				return true;
		 			    return false;
		 		}
		 		if(line.contains("USER0003")){
		 			line=line+2;
		 			if(line.contains("true"))
		 				return true;
		 			    return false;
		 		}
		 			}
		 	return false;
		
	}
	
	public static void main (String args[]) throws IOException, EmptyShoppingCartException{
		
		
		File indexFile = new File("fixtures/sampleIndexes2.json");
       File  itemsFile = new File("fixtures/sampleItems2.json");
       InputParser inputParser = new InputParser(indexFile, itemsFile);
       ArrayList<Item> items = inputParser.parser().getItems();

       ShoppingChart shoppingChart = new ShoppingChart();
       for (Item i : items) {
           shoppingChart.add(i);
       }

       Pos pos = new Pos();

       String actualShoppingList = pos.getShoppingList(shoppingChart);
       System.out.print(actualShoppingList);
	}

}
