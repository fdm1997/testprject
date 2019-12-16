package com.fdm.testpoject.NullObject;

import java.util.List;

/**
 * @author fdm
 * @date 2019/11/22 11:07
 * @Description:
 */
public class Client {

    public static void main(String[] args) {
        List<String> list = null;
        String str1 = "";
        for (String str:list){
            str1 += str;
        }
    }
}
