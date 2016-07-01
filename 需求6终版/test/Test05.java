package test;

import com.thoughtworks.pos.common.BarCodeNotExistException;
import com.thoughtworks.pos.common.EmptyShoppingCartException;
import com.thoughtworks.pos.domains.Item;
import com.thoughtworks.pos.domains.Pos;
import com.thoughtworks.pos.domains.ShoppingChart;
import com.thoughtworks.pos.domains.user;
import com.thoughtworks.pos.services.services.ListInputParser;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Calendar;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

public class Test05 {
    private File indexFile;
    private File itemsFile;
    private File usersFile;

    @Before
    public void setUp() throws Exception {
        indexFile = new File("fixtures/newlist.json") ;
        itemsFile = new File("fixtures/sampleIndex.json");
        usersFile = new File("fixtures/us.json");
    }

    private void WriteToFile(File file, String content) throws FileNotFoundException {
        PrintWriter printWriter = new PrintWriter(file);
        printWriter.write(content);
        printWriter.close();
    }

    @After
    public void tearDown() throws Exception {
        if (indexFile.exists()){
            indexFile.delete();
        }
        if (itemsFile.exists()){
            itemsFile.delete();
        }
        if(usersFile.exists()){
            usersFile.delete();
        }
    }

    @Test
    public void testExistUserBuyTwoSameItem() throws Exception {
    	
    	 Calendar calendar=Calendar.getInstance();
         StringBuilder time=new StringBuilder().append("��ӡʱ��:")
                 .append(String.valueOf(calendar.get(Calendar.YEAR))).append("��")
                 .append(String.valueOf(calendar.get(Calendar.MONTH))).append("��")
                 .append(String.valueOf(calendar.get(Calendar.DATE))).append("�� ")
                 .append(String.valueOf(calendar.get(Calendar.HOUR_OF_DAY))).append(":")
                 .append(String.valueOf(calendar.get(Calendar.MINUTE))).append(":")
                 .append(String.valueOf(calendar.get(Calendar.SECOND))).append("\n")
                 .append("----------------------\n" );
        String sampleItem = new StringBuilder()
                .append("{\n")
                .append("\"ITEM000005\":{\n")
                .append("\"name\": \"���\",\n")
                .append("\"unit\": \"��\",\n")
                .append("\"price\": 2.00,\n")
                .append("\"discount\": 0.6,\n")
                .append("\"vipDiscount\":0.8,\n")
                .append("\"promotion\": false\n")
                .append("}\n")
                .append("}\n")
                .toString();
        WriteToFile(itemsFile, sampleItem);

        String sampleUser = new StringBuilder()
                .append("{\n")
                .append("\"USER0001\":{\n")
                .append("\"userCode\":\"USER0001\",\n")
                .append("\"name\":\"�Ƴ�\",\n")
                .append("\"isVip\":true,\n")
                .append("\"mark\":550\n")
                .append("}\n")
                .append("}\n")
                .toString();
        WriteToFile(usersFile, sampleUser);

        String sampleIndex = new StringBuilder()
                .append("{\n")
                .append("\"user\":\"USER0001\",\n")
                .append("\"items\":[\n")
                .append("\"ITEM000005\",\n")
                .append("\"ITEM000005\",\n")
                .append("\"ITEM000005\"\n")
                .append("]\n")
                .append("}\n")
                .toString();
        WriteToFile(indexFile, sampleIndex);

        ListInputParser inputParser = new ListInputParser(indexFile, itemsFile, usersFile);

        ShoppingChart shoppingChart = inputParser.parser();
        ArrayList<Item> items = shoppingChart.getItems();
        user user = shoppingChart.getUser();
     Pos pos =new Pos();
     String actualShoppingList = pos.getShoppingList(shoppingChart);
     String expectedShoppingList =
             "***�̵깺���嵥***\n"
            		 +"��Ա��ţ�USER0001\t��Ա���֣�550��\n"
            		 + "----------------------\n"
            		 +time.toString()
                     + "���ƣ���أ�������3�������ۣ�2.00(Ԫ)��С�ƣ�2.88(Ԫ)\n"
                     + "----------------------\n"
                     + "�ܼƣ�2.88(Ԫ)\n"
                     + "��ʡ��3.12(Ԫ)\n"
                     + "**********************\n";
     
     assertThat(actualShoppingList, is(expectedShoppingList));
     
       
        assertThat(user.getuserCode(), is("USER0001"));
        assertThat(user.getName(), is("�Ƴ�"));
        assertThat(user.getisVip(), is(true));
        assertThat(user.getMark(), is(550));
    }

    @Test
    public void testNotExistUserBuyDifferentItems() throws Exception {

   	 Calendar calendar=Calendar.getInstance();
        StringBuilder time=new StringBuilder().append("��ӡʱ��:")
                .append(String.valueOf(calendar.get(Calendar.YEAR))).append("��")
                .append(String.valueOf(calendar.get(Calendar.MONTH))).append("��")
                .append(String.valueOf(calendar.get(Calendar.DATE))).append("�� ")
                .append(String.valueOf(calendar.get(Calendar.HOUR_OF_DAY))).append(":")
                .append(String.valueOf(calendar.get(Calendar.MINUTE))).append(":")
                .append(String.valueOf(calendar.get(Calendar.SECOND))).append("\n")
                .append("----------------------\n" );
        String sampleItem = new StringBuilder()
                .append("{\n")
                .append("\"ITEM000005\":{\n")
                .append("\"name\": \"���\",\n")
                .append("\"unit\": \"��\",\n")
                .append("\"price\": 2.00,\n")
                .append("\"discount\": 0.6,\n")
                .append("\"vipDiscount\":0.8,\n")
                .append("\"promotion\": false\n")
                .append("},\n")
                .append("\"ITEM000004\":{\n")
                .append("\"name\": \"�̲�\",\n")
                .append("\"unit\": \"ƿ\",\n")
                .append("\"price\": 5.00,\n")
                .append("\"vipDiscount\":0.8,\n")
                .append("\"promotion\": true\n")
                .append("}\n")
                .append("}\n")
                .toString();
        WriteToFile(itemsFile, sampleItem);

        String sampleUser = new StringBuilder()
                .append("{\n")
                .append("\"USER0001\":{\n")
                .append("\"userCode\":\"USER0001\",\n")
                .append("\"name\":\"�Ƴ�\",\n")
                .append("\"isVip\":true,\n")
                .append("\"mark\":550\n")
                .append("}\n")
                .append("}\n")
                .toString();
        WriteToFile(usersFile, sampleUser);

        String sampleIndex = new StringBuilder()
                .append("{\n")
                .append("\"user\":\"USER0006\",\n")
                .append("\"items\":[\n")
                .append("\"ITEM000005\",\n")
                .append("\"ITEM000004\"\n")
                .append("]\n")
                .append("}\n")
                .toString();
        WriteToFile(indexFile, sampleIndex);

        ListInputParser inputParser = new ListInputParser(indexFile, itemsFile, usersFile);

        ShoppingChart shoppingChart = inputParser.parser();
        ArrayList<Item> items = shoppingChart.getItems();
        user user = shoppingChart.getUser();
        Pos pos =new Pos();
        String actualShoppingList = pos.getShoppingList(shoppingChart);
        String expectedShoppingList =
                "***�̵깺���嵥***\n"
               		 +"��Ա��ţ�USER0006\t��Ա���֣�1��\n"
               		 + "----------------------\n"
               		     +time.toString()   
                        + "���ƣ���أ�������1�������ۣ�2.00(Ԫ)��С�ƣ�0.96(Ԫ)\n"
                        + "���ƣ��̲裬������1ƿ�����ۣ�5.00(Ԫ)��С�ƣ�5.00(Ԫ)\n"
                        + "----------------------\n"
                        + "�ܼƣ�5.96(Ԫ)\n"
                        + "��ʡ��1.04(Ԫ)\n"
                        + "**********************\n";
        
        assertThat(actualShoppingList, is(expectedShoppingList));
       
        assertThat(user.getuserCode(), is("USER0006"));
        assertThat(user.getName(), is("�»�Ա"));
        assertThat(user.getisVip(), is(true));
        assertThat(user.getMark(), is(1));
    }

    @Test
    public void testNullUserBuyDifferentItems() throws Exception {

   	 Calendar calendar=Calendar.getInstance();
        StringBuilder time=new StringBuilder().append("��ӡʱ��:")
                .append(String.valueOf(calendar.get(Calendar.YEAR))).append("��")
                .append(String.valueOf(calendar.get(Calendar.MONTH))).append("��")
                .append(String.valueOf(calendar.get(Calendar.DATE))).append("�� ")
                .append(String.valueOf(calendar.get(Calendar.HOUR_OF_DAY))).append(":")
                .append(String.valueOf(calendar.get(Calendar.MINUTE))).append(":")
                .append(String.valueOf(calendar.get(Calendar.SECOND))).append("\n")
                .append("----------------------\n" );
        String sampleItem = new StringBuilder()
                .append("{\n")
                .append("\"ITEM000005\":{\n")
                .append("\"name\": \"���\",\n")
                .append("\"unit\": \"��\",\n")
                .append("\"price\": 2.00,\n")
                .append("\"discount\": 0.6,\n")
                .append("\"vipDiscount\":0.8,\n")
                .append("\"promotion\": false\n")
                .append("},\n")
                .append("\"ITEM000004\":{\n")
                .append("\"name\": \"�̲�\",\n")
                .append("\"unit\": \"ƿ\",\n")
                .append("\"price\": 5.00,\n")
                .append("\"vipDiscount\":0.8,\n")
                .append("\"promotion\": true\n")
                .append("}\n")
                .append("}\n")
                .toString();
        WriteToFile(itemsFile, sampleItem);

        String sampleUser = new StringBuilder()
                .append("{\n")
                .append("\"USER0001\":{\n")
                .append("\"userCode\":\"USER0001\",\n")
                .append("\"name\":\"�Ƴ�\",\n")
                .append("\"isVip\":true,\n")
                .append("\"mark\":550\n")
                .append("}\n")
                .append("}\n")
                .toString();
        WriteToFile(usersFile, sampleUser);

        String sampleIndex = new StringBuilder()
                .append("{\n")
                .append("\"user\":\"USER0001\",\n")
                .append("\"items\":[\n")
                .append("\"ITEM000005\",\n")
                .append("\"ITEM000004\",\n")
                .append("\"ITEM000005\"\n")
                .append("]\n")
                .append("}\n")
                .toString();
        WriteToFile(indexFile, sampleIndex);

        ListInputParser inputParser = new ListInputParser(indexFile, itemsFile, usersFile);

        ShoppingChart shoppingChart = inputParser.parser();
        ArrayList<Item> items = shoppingChart.getItems();
        user user = shoppingChart.getUser();
       
        Pos pos =new Pos();
        String actualShoppingList = pos.getShoppingList(shoppingChart);
        String expectedShoppingList =
                "***�̵깺���嵥***\n"
               		 +"��Ա��ţ�USER0001\t��Ա���֣�555��\n"
               		 + "----------------------\n"
               		 +time.toString()
                        + "���ƣ���أ�������2�������ۣ�2.00(Ԫ)��С�ƣ�1.92(Ԫ)\n"
                        + "���ƣ��̲裬������1ƿ�����ۣ�5.00(Ԫ)��С�ƣ�5.00(Ԫ)\n"
                        + "----------------------\n"
                        + "�ܼƣ�6.92(Ԫ)\n"
                        + "��ʡ��2.08(Ԫ)\n"
                        + "**********************\n";
        
        assertThat(actualShoppingList, is(expectedShoppingList));
        assertThat(items.size(), is(3));
        Item item = items.get(0);
        assertThat(item.getDiscount(), is(0.6));
        assertThat(item.getvipDiscount(), is(0.8));
        assertThat(item.getIsPromotion(), is(false));
        item = items.get(1);
        assertThat(item.getDiscount(), is(1.0));
        assertThat(item.getvipDiscount(), is(1.0));
        assertThat(item.getIsPromotion(), is(true));

        assertThat(user.getuserCode(), is("USER0001"));
        assertThat(user.getName(), is("�Ƴ�"));
        assertThat(user.getisVip(), is(true));
        assertThat(user.getMark(), is(555));
    }

 

 
}