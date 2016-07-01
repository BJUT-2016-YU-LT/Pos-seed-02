package com.thoughtworks.pos.domains;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import com.thoughtworks.pos.domains.Report;
/**
 * Created by HC on 2016/6/30.
 */
public class user {
	private Report report;
private String userCode;
 private String name;
 private boolean isVip=false;
 private int mark=0;
 public user(){

 }

 public user(String userCode, String name){
     this.setuserCode(userCode);
     this.setName(name);
 }

 public user(String userCode, String name, boolean isVIP){
     this(userCode,name);
     if (isVIP) {
         this.setVIP();
     }
 }

 public user(String userCode, String name, int score){
     this(userCode,name);
     this.setVIP();
     this.setMark(score);
 }

 public user(String userCode, String name, boolean isVIP, int score){
     this(userCode,name,isVIP);
   
     this.addScore(score);
 }
public void setuserCode(String userCode){this.userCode=userCode;}
public void setMark(int a){mark=a;}
public void setName(String Num){name=Num;}
public boolean setVIP() {
    if(this.isVip) {
        return false;
    }
    this.isVip = true;
    return true;
}
public String getuserCode(){return userCode;}
public String getName(){return name;}
public boolean getisVip(){return isVip;}
   public int getMark(){return mark;}
public boolean addScore(int score) {
    if(this.isVip) {
        this.mark += score;
        return true;
    }
    return false;
}
public int ChangePoint(double moneyIn){
int units=(int)(moneyIn/5.00)   ;
    int pointChange=0;
    if(this.mark>=0&&this.mark<=200)
        pointChange= units*1;
    else if(this.mark>200&&this.mark<=500)
        pointChange= units*3;
    else if(this.mark>500)
        pointChange= units*5;
    this.mark+=pointChange;
    return this.mark;

}
}

