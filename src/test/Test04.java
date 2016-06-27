package test;

import com.thoughtworks.pos.common.EmptyShoppingCartException;
import com.thoughtworks.pos.domains.Item;
import com.thoughtworks.pos.domains.Pos;
import com.thoughtworks.pos.domains.ShoppingChart;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.io.File;

import org.junit.After;
import org.junit.Before;
/**
 * Created by pc on 2016/6/25.
 */
public class Test04 {
	
	
	
	 private File indexFile;
	    private File itemsFile;


	    @Before
	    public void setUp() throws Exception {
	        indexFile = new File("./sampleIndex.json");
	        itemsFile = new File("./itemsFile.json");

	    }

	    @After
	    public void tearDown() throws Exception {
	        if(indexFile.exists()){
	            indexFile.delete();
	        }
	        if(itemsFile.exists()){
	            itemsFile.delete();
	        }

	    }
	 @Test
	public void testShouldSupportDiscountRatherThanPromotionWhenHavingOneItemWithPromotionSign() throws EmptyShoppingCartException {
        // given

        ShoppingChart shoppingChart = new ShoppingChart();
        shoppingChart.add(new Item("ITEM000000", "可口可乐", "瓶", 3.00,0.80));

        // when
        Pos pos = new Pos();
        String actualShoppingList = pos.getShoppingList(shoppingChart);

        // then
        String expectedShoppingList =
                "***商店购物清单***\n"
                        + "名称：可口可乐，数量：1瓶，单价：3.00(元)，小计：2.40(元)\n"
                        + "----------------------\n"
                        + "总计：2.40(元)\n"
                        + "节省：0.60(元)\n"
                        + "**********************\n";
        assertThat(actualShoppingList, is(expectedShoppingList));
    }
    @Test
    public void testShouldSupportPromotionWhenHavingTwoItemWithOutDiscount() throws EmptyShoppingCartException {
        // given
        ShoppingChart shoppingChart = new ShoppingChart();
        shoppingChart.add(new Item("ITEM000000", "可口可乐", "瓶", 3.00,true));
        shoppingChart.add(new Item("ITEM000000", "可口可乐", "瓶", 3.00,true));
        shoppingChart.add(new Item("ITEM000000", "可口可乐", "瓶", 3.00,true));
        shoppingChart.add(new Item("ITEM000001", "雪碧", "瓶", 3.00,0.8));
        // when
        Pos pos = new Pos();
        String actualShoppingList = pos.getShoppingList(shoppingChart);

        // then
        String expectedShoppingList =
                "***商店购物清单***\n"
                        + "名称：可口可乐，数量：3瓶，单价：3.00(元)，小计：6.00(元)\n"
                		+ "名称：雪碧，数量：1瓶，单价：3.00(元)，小计：2.40(元)\n"
                        + "----------------------\n"
                        + "挥泪赠送商品：\n"
                        + "名称：可口可乐，数量：1瓶\n"
                        +"----------------------\n"
                        + "总计：8.40(元)\n"
                        + "节省：0.60(元)\n"
                        + "**********************\n";
        assertThat(actualShoppingList, is(expectedShoppingList));
    }
    @Test
    public void testShouldRecognizeDiscountOrPromotionWhenHavingDifferentTypesOfItems() throws EmptyShoppingCartException {
        // given
        ShoppingChart shoppingChart = new ShoppingChart();
        shoppingChart.add(new Item("ITEM000001", "雪碧", "瓶", 2.00, true));
        shoppingChart.add(new Item("ITEM000001", "雪碧", "瓶", 2.00, true));
        shoppingChart.add(new Item("ITEM000001", "雪碧", "瓶", 2.00, true));
        shoppingChart.add(new Item("ITEM000000", "可口可乐", "瓶", 3.00,true));
        shoppingChart.add(new Item("ITEM000000", "可口可乐", "瓶", 3.00,true));
        shoppingChart.add(new Item("ITEM000000", "可口可乐", "瓶", 3.00,true));
        shoppingChart.add(new Item("ITEM000004","电池","个",3.00,0.8));



        // when
        Pos pos = new Pos();
        String actualShoppingList = pos.getShoppingList(shoppingChart);

        // then
        String expectedShoppingList =
                "***商店购物清单***\n"
                        + "名称：雪碧，数量：3瓶，单价：2.00(元)，小计：4.00(元)\n"
                        + "名称：可口可乐，数量：3瓶，单价：3.00(元)，小计：6.00(元)\n"
                        + "名称：电池，数量：1个，单价：3.00(元)，小计：2.40(元)\n"
                        + "----------------------\n"
                        + "挥泪赠送商品：\n"
                        + "名称：雪碧，数量：1瓶\n"
                        + "名称：可口可乐，数量：1瓶\n"
                        +"----------------------\n"
                        + "总计：12.40(元)\n"
                        + "节省：0.60(元)\n"
                        + "**********************\n";
        assertThat(actualShoppingList, is(expectedShoppingList));
    }
}
