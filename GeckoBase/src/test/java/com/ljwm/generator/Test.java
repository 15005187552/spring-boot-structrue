package com.ljwm.generator;

import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Test {
  public static void main(String[] args) {
    List<String> keysList =new ArrayList();
    keysList.add("1");
    keysList.add("2");
    keysList.add("3");
    keysList.add("4");
    keysList.add("5");
    keysList.add("6");
    keysList.add("13");


    List<String> addMemberIds = Lists.newArrayList();
   // addMemberIds.addAll(memberIds);
    List<String> list =new ArrayList();
    list.add("6");
    list.add("14");


    //并集
    //memberIds.addAll(oldMemberIds);
    //交集
    //memberIds.retainAll(oldMemberIds);
    //差集
    //memberIds.removeAll(oldMemberIds);
    //无重复并集
    list.removeAll(keysList);
    keysList.addAll(list);

    Iterator<String> it=keysList.iterator();
    while (it.hasNext()) {
      System.out.println(it.next());
    }
    System.out.println("-----------------------------------\n");
    Iterator<String> it1=addMemberIds.iterator();
    while (it1.hasNext()) {
      System.out.println(it1.next());
    }

    //printStr(list1);

  }

  public static void printStr(List list1){
    for (int i = 0; i < list1.size(); i++) {
      System.out.println(list1.get(i));
    }
  }
}
