package com.thoughtworks.pos.bin;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;

import com.thoughtworks.pos.common.EmptyShoppingCartException;
import com.thoughtworks.pos.domains.Item;
import com.thoughtworks.pos.domains.Pos;
import com.thoughtworks.pos.domains.ShoppingChart;

public class try0 {
	
	public static void main (String args[]) throws IOException, EmptyShoppingCartException{
		ShoppingChart S=new ShoppingChart();
		 Item xuebi=new Item("ITEM000001","雪碧","瓶",3.00,0.8);
		 Item kele=new Item("ITEM000002","可口可乐","瓶",3.00,0.6);
	     Item dianchi=new Item("ITEM000004","电池","个",1.00,true);
	     Item dianchi1=new Item("ITEM000004","电池","个",1.00,true);
	     Item dianchi2=new Item("ITEM000004","电池","个",1.00,true);
	     Pos p=new Pos();
	     BufferedReader in = new BufferedReader(new FileReader("fixtures/sampleIndexes.json"));
	 	String line = null;
	 	String str="";
	 	while((line = in.readLine())!=null){str+=line;}
	 	try {
	 		
	 		
	 		
	 		File f5 = new File("fixtures/sampleItems.json");//这几行是为了在写入文件之前先清空文件，这是通WRITE空语句完成的
	        FileWriter fw5 = new FileWriter(f5);
	        BufferedWriter bw1 = new BufferedWriter(fw5);
		    bw1.write("");
		    bw1.close();
   if(str.contains(dianchi.getBarcode())){
	   FileWriter f= new FileWriter("fixtures/sampleItems.json", true);
       BufferedWriter bw = new BufferedWriter(f);
       bw.append("{\n")
       .append("'ITEM000004'")
       .append("\n")
       .append("}\n")
       .toString();
       bw.close();
       f.close(); 
   }
   if(str.contains(kele.getBarcode())){
	   FileWriter f= new FileWriter("fixtures/sampleItems.json", true);
       BufferedWriter bw = new BufferedWriter(f);
       bw.append("{\n")
       .append("'ITEM000002'")
       .append("\n")
       .append("}\n")
       .toString();
       bw.close();
       f.close(); 
   } 		
	     
   if(str.contains(xuebi.getBarcode())){
	   FileWriter f= new FileWriter("fixtures/sampleItems.json", true);
       BufferedWriter bw = new BufferedWriter(f);
       bw.append("{\n")
       .append("'ITEM000001'")
       .append("\n")
       .append("}\n")
       .toString();
       bw.close();
       f.close(); 
   }     
   if(str.contains(dianchi1.getBarcode())){
	   FileWriter f= new FileWriter("fixtures/sampleItems.json", true);
       BufferedWriter bw = new BufferedWriter(f);
       bw.append("{\n")
       .append("'ITEM000004'")
       .append("\n")
       .append("}\n")
       .toString();
       bw.close();
       f.close(); 
   }
   if(str.contains(dianchi2.getBarcode())){
	   FileWriter f= new FileWriter("fixtures/sampleItems.json", true);
       BufferedWriter bw = new BufferedWriter(f);
       bw.append("{\n")
       .append("'ITEM000004'")
       .append("\n")
       .append("}\n")
       .toString();
       bw.close();
       f.close(); 
   }
	     
	}catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	 
	 	
	 	
	 	BufferedReader in1 = new BufferedReader(new FileReader("fixtures/sampleItems.json"));
		String line1 = null;
		String str1="";
		
		while((line1 = in1.readLine())!=null)
		{
			if(line1.contains("ITEM000001"))
			{S.add(xuebi);}
			if(line1.contains("ITEM000004"))
			{S.add(dianchi);}
			if(line1.contains("ITEM000002"))
			{S.add(kele);}
		 str1+=line1;
		}
		String sl = p.getShoppingList(S);
	     System.out.print(sl);
		
		
		
		
		
		
		
}
}
