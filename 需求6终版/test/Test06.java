package test;

import com.thoughtworks.pos.domains.Item;
import com.thoughtworks.pos.domains.Pos;
import com.thoughtworks.pos.domains.ShoppingChart;
import com.thoughtworks.pos.domains.user;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Calendar;

/**
 * Created by HC on 2016/6/30.
 */
public class Test06{
    @Test
    public void testExistVipUserBuyItemsHaveDifferentDiscountType() throws  Exception{
        // given
        ShoppingChart shoppingChart = new ShoppingChart();
        shoppingChart.add(new Item( "ITEM000000", "雪碧", "瓶", 2.00,0.6));
        shoppingChart.add(new Item("ITEM000001", "可乐", "罐", 3.00, 0.8));
        shoppingChart.add(new Item( "ITEM000002", "奶茶", "听", 3.00, 0.9,0.8,false));

        shoppingChart.setUser(new user("USER0001","黄晨", true, 300));

        // when
        Pos pos = new Pos();
        String actualShoppingList = pos.getShoppingList(shoppingChart);
        Calendar calendar=Calendar.getInstance();
        StringBuilder time=new StringBuilder().append("打印时间:")
                .append(String.valueOf(calendar.get(Calendar.YEAR))).append("年")
                .append(String.valueOf(calendar.get(Calendar.MONTH))).append("月")
                .append(String.valueOf(calendar.get(Calendar.DATE))).append("日 ")
                .append(String.valueOf(calendar.get(Calendar.HOUR_OF_DAY))).append(":")
                .append(String.valueOf(calendar.get(Calendar.MINUTE))).append(":")
                .append(String.valueOf(calendar.get(Calendar.SECOND))).append("\n")
                .append("----------------------\n" );
        // then
        String expectedShoppingList =
                  "***商店购物清单***\n"
                + "会员编号：USER0001\t会员积分：303分\n"
                + "----------------------\n"
                +time.toString()
                + "名称：雪碧，数量：1瓶，单价：2.00(元)，小计：1.20(元)\n"
                + "名称：可乐，数量：1罐，单价：3.00(元)，小计：2.40(元)\n"
                + "名称：奶茶，数量：1听，单价：3.00(元)，小计：2.16(元)\n"
                + "----------------------\n"
                + "总计：5.76(元)\n"
                + "节省：2.24(元)\n"
                + "**********************\n";
        assertThat(actualShoppingList, is(expectedShoppingList));
    }
}
