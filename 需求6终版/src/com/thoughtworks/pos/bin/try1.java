package com.thoughtworks.pos.bin;
import com.thoughtworks.pos.common.EmptyShoppingCartException;
import com.thoughtworks.pos.domains.Item;
import com.thoughtworks.pos.domains.Pos;
import com.thoughtworks.pos.domains.ShoppingChart;
import com.thoughtworks.pos.services.services.InputParser;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
public class try1 {
	 private static  void WriteToFile(File file, String content) throws FileNotFoundException {
	        PrintWriter printWriter = new PrintWriter(file);
	        printWriter.write(content);
	        printWriter.close();
	    }
	public static void main (String args[]) throws IOException, EmptyShoppingCartException{
		File indexFile;
		File itemsFile;
		indexFile = new File("./sampleIndexes.json");
        itemsFile = new File("./sampleItems.json");
        String sampleIndex = new StringBuilder()
                .append("{\n")
                .append("'ITEM000000':{\n")
                .append("\"name\": '¿É¿Ú¿ÉÀÖ',\n")
                .append("\"unit\": 'Æ¿',\n")
                .append("\"price\": 3.00,\n")
                .append("\"promotion\": true\n")
                .append("}\n")
                .append("}\n")
                .toString();
        WriteToFile(indexFile, sampleIndex);

        String sampleItems = new StringBuilder()
                .append("[\n")
                .append("\"ITEM000000\",\n")
                .append("\"ITEM000000\",\n")
                .append("\"ITEM000000\"")
                .append("]")
                .toString();
        WriteToFile(itemsFile, sampleItems);
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
