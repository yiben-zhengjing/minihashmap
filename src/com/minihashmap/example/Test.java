package com.minihashmap.example;

public class Test {
    public static void main(String[] args) {
        MyMap<String,String> myMap = new MyHashMap<>();
        for(int i = 0; i<500; i++){
            myMap.put("key"+i,"value"+i);
        }

        for(int i = 0; i<500; i++){
            System.out.println("key"+i+",value is "+myMap.get("key"+i));
        }
    }
}
