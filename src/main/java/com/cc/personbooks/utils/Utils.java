package com.cc.personbooks.utils;

/**
 * Created by Administrator on 2015-8-26.
 */
public class Utils {

    public static String StringOfMonths(int value){
        switch (value) {
            case 0:
                return "00";
            case 1:
                return "01";
            case 2:
                return "02";
            case 3:
                return "03";
            case 4:
                return "04";
            case 5:
                return "05";
            case 6:
                return "06";
            case 7:
                return "07";
            case 8:
                return "08";
            case 9:
                return "09";
            default:
                return String.valueOf(value);
        }
    }
}
