package com.fdm.testpoject.Test;

import javax.validation.constraints.NotNull;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * @author fdm
 * @date 2019/11/5 14:43
 * @Description:
 */

public class eed {
  //  A s;

   /* @Override
    public String toString() {
        return "eed{" +
                "s='" + s + '\'' +
                '}';
    }*/

    public void find(A s){
        System.out.println("b");
       // this.s = s;
    }
    public void find(B o){
        System.out.println("a");
        //this.s = s;
    }
    public static void main(String[] args)  {

      int i = 1;
        List list = new ArrayList<>();
        list.add("we");
        list.add(i);

    }
    public static boolean isMobile(String str) {

       String  PHONE_PATTERN="^(1[3-9][0-9])\\d{8}$";
        return Pattern.compile(PHONE_PATTERN).matcher(str).matches();
    }

    /**
     * 获取远程文件
     * @param remoteFilePath 远程文件路径
     * @param localFilePath 本地文件路径
     */
    public static  void downloadFile(String remoteFilePath, String localFilePath) throws Exception{
        URL urlfile = null;
        HttpURLConnection httpUrl = null;
        BufferedInputStream bis = null;
        urlfile = new URL(remoteFilePath);
        httpUrl = (HttpURLConnection)urlfile.openConnection();
        httpUrl.connect();
        bis = new BufferedInputStream(httpUrl.getInputStream());
        httpUrl.disconnect();
        bis.close();

    }

}

class A{

}

class B extends A{

}
