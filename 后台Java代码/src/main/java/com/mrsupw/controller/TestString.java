package com.mrsupw.controller;

public class TestString {

    public int getNumberOfA(String string){
        int count =0;
        for (int i = 0; i < string.length(); i++) {
            if(string.charAt(i) == 'a'){
                count += 1;
            }
        }
        return count;
    }
}
