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
        shoppingChart.add(new Item( "ITEM000000", "ѩ��", "ƿ", 2.00,0.6));
        shoppingChart.add(new Item("ITEM000001", "����", "��", 3.00, 0.8));
        shoppingChart.add(new Item( "ITEM000002", "�̲�", "��", 3.00, 0.9,0.8,false));

        shoppingChart.setUser(new user("USER0001","�Ƴ�", true, 300));

        // when
        Pos pos = new Pos();
        String actualShoppingList = pos.getShoppingList(shoppingChart);
        Calendar calendar=Calendar.getInstance();
        StringBuilder time=new StringBuilder().append("��ӡʱ��:")
                .append(String.valueOf(calendar.get(Calendar.YEAR))).append("��")
                .append(String.valueOf(calendar.get(Calendar.MONTH))).append("��")
                .append(String.valueOf(calendar.get(Calendar.DATE))).append("�� ")
                .append(String.valueOf(calendar.get(Calendar.HOUR_OF_DAY))).append(":")
                .append(String.valueOf(calendar.get(Calendar.MINUTE))).append(":")
                .append(String.valueOf(calendar.get(Calendar.SECOND))).append("\n")
                .append("----------------------\n" );
        // then
        String expectedShoppingList =
                  "***�̵깺���嵥***\n"
                + "��Ա��ţ�USER0001\t��Ա���֣�303��\n"
                + "----------------------\n"
                +time.toString()
                + "���ƣ�ѩ�̣�������1ƿ�����ۣ�2.00(Ԫ)��С�ƣ�1.20(Ԫ)\n"
                + "���ƣ����֣�������1�ޣ����ۣ�3.00(Ԫ)��С�ƣ�2.40(Ԫ)\n"
                + "���ƣ��̲裬������1�������ۣ�3.00(Ԫ)��С�ƣ�2.16(Ԫ)\n"
                + "----------------------\n"
                + "�ܼƣ�5.76(Ԫ)\n"
                + "��ʡ��2.24(Ԫ)\n"
                + "**********************\n";
        assertThat(actualShoppingList, is(expectedShoppingList));
    }
}
