package com.thoughtworks.pos.services.services;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.pos.common.BarCodeNotExistException;
import com.thoughtworks.pos.common.EmptyShoppingCartException;
import com.thoughtworks.pos.domains.Item;
import com.thoughtworks.pos.domains.ShoppingChart;
import com.thoughtworks.pos.domains.user;
import com.thoughtworks.pos.domains.UserShoppingList;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;

/**
 * Created by HC on 2016/6/29.
 */
public class ListInputParser{
    private File indexFile;
    private File itemsFile;
    private File usersFile;
    private final ObjectMapper objectMapper;

    public File getIndexFile(){
        return indexFile;
    }

    public File getItemsFile(){
        return itemsFile;
    }

    public File getUsersFile(){
        return usersFile;
    }

    public ListInputParser(File indexFile, File itemsFile, File usersFile) {
        this.indexFile = indexFile;
        this.itemsFile = itemsFile;
        this.usersFile = usersFile;
        objectMapper = new ObjectMapper(new JsonFactory());
        objectMapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
    }

    public ShoppingChart parser() throws IOException,BarCodeNotExistException,EmptyShoppingCartException {
        return BuildShoppingChart(getUserShoppingList(), getItemIndexes(), getUserIndexes());
    }

    private ShoppingChart BuildShoppingChart(UserShoppingList userShoppingList, HashMap<String, Item> itemIndexes, HashMap<String, user> userIndexes) throws BarCodeNotExistException, EmptyShoppingCartException {
        ShoppingChart shoppingChart = new ShoppingChart();
        user user = new user();
        if(!userShoppingList.getUser().isEmpty()){
            if (userIndexes.containsKey(userShoppingList.getUser())) {
                user mappedUser = userIndexes.get(userShoppingList.getUser());
                user = new user(userShoppingList.getUser(), mappedUser.getName(), mappedUser.getisVip(), mappedUser.getMark());
                shoppingChart.setUser(user);
            }
            else {
                user = new user(userShoppingList.getUser(), "新会员", true, 0);
                userIndexes.put(userShoppingList.getUser(), user);
                shoppingChart.setUser(user);
               
                }
            }
        
        if(userShoppingList.getItems().length!=0){
            for (String barcode : userShoppingList.getItems()) {
                if(itemIndexes.containsKey(barcode)) {
                    Item mappedItem = itemIndexes.get(barcode);
                    Item item = new Item( barcode, mappedItem.getName(), mappedItem.getUnit(), mappedItem.getPrice(), mappedItem.getDiscount(),mappedItem.getvipDiscount(), mappedItem.getIsPromotion());
                    shoppingChart.add(item);
                }
                else {
                    throw new BarCodeNotExistException();
                }
            }
        }
        else {
            throw new EmptyShoppingCartException();
        }

        return shoppingChart;
    }

    private UserShoppingList getUserShoppingList() throws IOException {
        String userShoppingListStr = FileUtils.readFileToString(indexFile);
        return objectMapper.readValue(userShoppingListStr, UserShoppingList.class);
    }

    private HashMap<String, Item> getItemIndexes() throws IOException {
        String itemsIndexStr = FileUtils.readFileToString(itemsFile);
        TypeReference<HashMap<String,Item>> typeRef = new TypeReference<HashMap<String,Item>>() {};
        return objectMapper.readValue(itemsIndexStr, typeRef);
    }

    private HashMap<String, user> getUserIndexes() throws IOException {
        String userIndexStr = FileUtils.readFileToString(usersFile);
        TypeReference<HashMap<String,user>> typeRef = new TypeReference<HashMap<String,user>>() {};
        return objectMapper.readValue(userIndexStr, typeRef);
    }
    private HashMap<String, user> setUsersScore(user user)throws IOException{
        HashMap<String, user> newUserIndexes = getUserIndexes();
        // 消费获取用户列表中用户编号相同的用户并修改其积分
        if(newUserIndexes.containsKey(user.getuserCode())){
            //System.out.println("该用户有记录");
            newUserIndexes.get(user.getuserCode()).setMark(user.getMark());;
            return newUserIndexes;
        }
        else if(user.getuserCode().isEmpty()) {
            //System.out.println("该用户无记录");
            return newUserIndexes;
        }
        // 若为有编号但不存在与用户列表中，新增该用户至表中
        //System.out.println("该用户无记录，已添加");
        newUserIndexes.put(user.getuserCode(), user);
        return newUserIndexes;
    }

    public String saveFile(ShoppingChart shoppingChart)throws IOException{
        HashMap<String, user> newUserIndexes = setUsersScore(shoppingChart.getUser());
        String userIndexesJson = objectMapper.writeValueAsString(newUserIndexes);
        //JsonNode node = objectMapper.readTree(userIndexesJson);
        //System.out.println(userIndexesJson);
        return userIndexesJson;
    }
}