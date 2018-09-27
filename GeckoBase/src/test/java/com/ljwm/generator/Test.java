package com.ljwm.generator;

import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Test {
  public static void main(String[] args) {
    List memberIds =new ArrayList();
    memberIds.add("1111");
    memberIds.add("2222");
    memberIds.add("3333");


    List<String> addMemberIds = Lists.newArrayList();
    addMemberIds.addAll(memberIds);
    List oldMemberIds =new ArrayList();
    oldMemberIds.add("3333");
    oldMemberIds.add("4444");
    oldMemberIds.add("5555");



    //并集
    //memberIds.addAll(oldMemberIds);
    //交集
    //memberIds.retainAll(oldMemberIds);
    //差集
    //memberIds.removeAll(oldMemberIds);
    //无重复并集
    oldMemberIds.removeAll(memberIds);
    memberIds.addAll(oldMemberIds);

    Iterator<String> it=memberIds.iterator();
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
