package com.fdm.testpoject.service;

/**
 * @author fdm
 * @date 2019/10/19 22:33
 * @Description:
 */
class Test{

    public static void main(String[] args){
        int x;
        int a[]={0,0,0,0,0,0};
        calculate(a,a[5]);
        System.out.println("the value of a[0] is "+a[0]);
        System.out.println("the value is a[5] is "+a[5]);}
    static int calculate(int x[],int y){
        for(int i=1;i<x.length;i++)
            if(y<x.length) x[i]=x[i-1]+1;
        return x[0];
    }
}//
