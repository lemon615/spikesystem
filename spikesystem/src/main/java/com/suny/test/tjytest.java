package com.suny.test;

import java.lang.reflect.Array;
import java.util.Scanner;

public class tjytest {
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        System.out.println("请输入两个不等且小于1000000的正整数，用空格分隔");
        String str = sc.nextLine();
        String[] arr = str.split("\\s+");
        int num1 = Integer.parseInt(arr[0]);
        int num2 = Integer.parseInt(arr[1]);

        if(num2 < num1){
            int temp = num1;
            num1= num2;
            num2 = temp;
        }

        if (num1 < 0 || num2 < 0 || num1 > 1000000 || num2 > 1000000){
            System.out.println(0);
            return;
        }else{
            int result = 0;
            for(int i=num1;i<=num2;i++){
                int count = 0;
                int cuurtnum = i;
                while (cuurtnum > 0){
                    count ++;
                    cuurtnum /= 10;
                }

                int totalResult = 0;

                for (int j =1; j<= count;j++){
                    int hundreds = (int)Math.pow(10,j-1);
                    int onlyNum = (i/hundreds)%10;
                    int pow = (int) Math.pow(onlyNum,j);
                    totalResult += pow;
                }

                if (totalResult == i){
                    result++;
                }
            }
            System.out.println(result);
        }
    }

}
