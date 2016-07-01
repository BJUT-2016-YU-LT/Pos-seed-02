package test;


import com.thoughtworks.pos.common.BarCodeNotExistException;
import com.thoughtworks.pos.common.BarCodeReuseException;
import com.thoughtworks.pos.common.EmptyShoppingCartException;
import com.thoughtworks.pos.domains.Pos;
import com.thoughtworks.pos.domains.ShoppingChart;
import com.thoughtworks.pos.services.services.ListInputParser;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Created by HC on 2016/6/30.
 */
public class accordingFileTest {
    private File items = new File("fixtures/sampleItems1.json");
    private File userShoppingList = new File("fixtures/newlist1.json");
    private File userShoppingList2 = new File("fixtures/newlist2.json");
    private File users = new File("fixtures/users.json");

    
    @Test
    public void userScoreChange() throws IOException,BarCodeNotExistException,EmptyShoppingCartException{
        ListInputParser inputParser = new ListInputParser(userShoppingList, items, users);
        ShoppingChart shoppingChart = inputParser.parser();
        Pos pos = new Pos();
        pos.getShoppingList(shoppingChart);
        String actualUsersList = inputParser.saveFile(shoppingChart);;
        String expectedUsersList =
                "{"
                        +"\"USER0002\":{"
                        +"\"userCode\":\"USER0002\","
                        +"\"name\":\"ÇØÀ×\","
                        +"\"isVip\":false,"
                        +"\"mark\":0},"

                        +"\"USER0001\":{"
                        + "\"userCode\":\"USER0001\","
                        +"\"name\":\"»Æ³¿\","
                        +"\"isVip\":true,"
                        +"\"mark\":306}"
                        
                       

                +"}";
        assertThat(actualUsersList, is(expectedUsersList));
    }

    @Test
    public void newUserAdd() throws IOException,BarCodeNotExistException,EmptyShoppingCartException{
        ListInputParser inputParser = new ListInputParser(userShoppingList2, items, users);
        ShoppingChart shoppingChart = inputParser.parser();
        Pos pos = new Pos();
        pos.getShoppingList(shoppingChart);
        String actualUsersList = inputParser.saveFile(shoppingChart);
        String expectedUsersList =
                "{"
                        +"\"USER0007\":{"
                        +"\"userCode\":\"USER0007\","
                        +"\"name\":\"ÐÂ»áÔ±\","
                        +"\"isVip\":true,"
                        +"\"mark\":2},"

                        +"\"USER0002\":{"
                        +"\"userCode\":\"USER0002\","
                        +"\"name\":\"ÇØÀ×\","
                        +"\"isVip\":false,"
                        +"\"mark\":0},"

                        +"\"USER0001\":{"
                        + "\"userCode\":\"USER0001\","
                        +"\"name\":\"»Æ³¿\","
                        +"\"isVip\":true,"
                        +"\"mark\":300}"

                       
                        +"}";
        assertThat(actualUsersList, is(expectedUsersList));
    }
}

