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
         StringBuilder time=new StringBuilder().append("打印时间:")
                 .append(String.valueOf(calendar.get(Calendar.YEAR))).append("年")
                 .append(String.valueOf(calendar.get(Calendar.MONTH))).append("月")
                 .append(String.valueOf(calendar.get(Calendar.DATE))).append("日 ")
                 .append(String.valueOf(calendar.get(Calendar.HOUR_OF_DAY))).append(":")
                 .append(String.valueOf(calendar.get(Calendar.MINUTE))).append(":")
                 .append(String.valueOf(calendar.get(Calendar.SECOND))).append("\n")
                 .append("----------------------\n" );
        String sampleItem = new StringBuilder()
                .append("{\n")
                .append("\"ITEM000005\":{\n")
                .append("\"name\": \"电池\",\n")
                .append("\"unit\": \"个\",\n")
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
                .append("\"name\":\"黄晨\",\n")
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
             "***商店购物清单***\n"
            		 +"会员编号：USER0001\t会员积分：550分\n"
            		 + "----------------------\n"
            		 +time.toString()
                     + "名称：电池，数量：3个，单价：2.00(元)，小计：2.88(元)\n"
                     + "----------------------\n"
                     + "总计：2.88(元)\n"
                     + "节省：3.12(元)\n"
                     + "**********************\n";
     
     assertThat(actualShoppingList, is(expectedShoppingList));
     
       
        assertThat(user.getuserCode(), is("USER0001"));
        assertThat(user.getName(), is("黄晨"));
        assertThat(user.getisVip(), is(true));
        assertThat(user.getMark(), is(550));
    }

    @Test
    public void testNotExistUserBuyDifferentItems() throws Exception {

   	 Calendar calendar=Calendar.getInstance();
        StringBuilder time=new StringBuilder().append("打印时间:")
                .append(String.valueOf(calendar.get(Calendar.YEAR))).append("年")
                .append(String.valueOf(calendar.get(Calendar.MONTH))).append("月")
                .append(String.valueOf(calendar.get(Calendar.DATE))).append("日 ")
                .append(String.valueOf(calendar.get(Calendar.HOUR_OF_DAY))).append(":")
                .append(String.valueOf(calendar.get(Calendar.MINUTE))).append(":")
                .append(String.valueOf(calendar.get(Calendar.SECOND))).append("\n")
                .append("----------------------\n" );
        String sampleItem = new StringBuilder()
                .append("{\n")
                .append("\"ITEM000005\":{\n")
                .append("\"name\": \"电池\",\n")
                .append("\"unit\": \"个\",\n")
                .append("\"price\": 2.00,\n")
                .append("\"discount\": 0.6,\n")
                .append("\"vipDiscount\":0.8,\n")
                .append("\"promotion\": false\n")
                .append("},\n")
                .append("\"ITEM000004\":{\n")
                .append("\"name\": \"奶茶\",\n")
                .append("\"unit\": \"瓶\",\n")
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
                .append("\"name\":\"黄晨\",\n")
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
                "***商店购物清单***\n"
               		 +"会员编号：USER0006\t会员积分：1分\n"
               		 + "----------------------\n"
               		     +time.toString()   
                        + "名称：电池，数量：1个，单价：2.00(元)，小计：0.96(元)\n"
                        + "名称：奶茶，数量：1瓶，单价：5.00(元)，小计：5.00(元)\n"
                        + "----------------------\n"
                        + "总计：5.96(元)\n"
                        + "节省：1.04(元)\n"
                        + "**********************\n";
        
        assertThat(actualShoppingList, is(expectedShoppingList));
       
        assertThat(user.getuserCode(), is("USER0006"));
        assertThat(user.getName(), is("新会员"));
        assertThat(user.getisVip(), is(true));
        assertThat(user.getMark(), is(1));
    }

    @Test
    public void testNullUserBuyDifferentItems() throws Exception {

   	 Calendar calendar=Calendar.getInstance();
        StringBuilder time=new StringBuilder().append("打印时间:")
                .append(String.valueOf(calendar.get(Calendar.YEAR))).append("年")
                .append(String.valueOf(calendar.get(Calendar.MONTH))).append("月")
                .append(String.valueOf(calendar.get(Calendar.DATE))).append("日 ")
                .append(String.valueOf(calendar.get(Calendar.HOUR_OF_DAY))).append(":")
                .append(String.valueOf(calendar.get(Calendar.MINUTE))).append(":")
                .append(String.valueOf(calendar.get(Calendar.SECOND))).append("\n")
                .append("----------------------\n" );
        String sampleItem = new StringBuilder()
                .append("{\n")
                .append("\"ITEM000005\":{\n")
                .append("\"name\": \"电池\",\n")
                .append("\"unit\": \"个\",\n")
                .append("\"price\": 2.00,\n")
                .append("\"discount\": 0.6,\n")
                .append("\"vipDiscount\":0.8,\n")
                .append("\"promotion\": false\n")
                .append("},\n")
                .append("\"ITEM000004\":{\n")
                .append("\"name\": \"奶茶\",\n")
                .append("\"unit\": \"瓶\",\n")
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
                .append("\"name\":\"黄晨\",\n")
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
                "***商店购物清单***\n"
               		 +"会员编号：USER0001\t会员积分：555分\n"
               		 + "----------------------\n"
               		 +time.toString()
                        + "名称：电池，数量：2个，单价：2.00(元)，小计：1.92(元)\n"
                        + "名称：奶茶，数量：1瓶，单价：5.00(元)，小计：5.00(元)\n"
                        + "----------------------\n"
                        + "总计：6.92(元)\n"
                        + "节省：2.08(元)\n"
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
        assertThat(user.getName(), is("黄晨"));
        assertThat(user.getisVip(), is(true));
        assertThat(user.getMark(), is(555));
    }

 

 
}