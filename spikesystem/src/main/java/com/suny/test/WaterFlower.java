package com.suny.test;

import java.util.Scanner;

public class WaterFlower {
    public static void main(String[] args){
        Scanner s = new Scanner(System.in);
        System.out.println("两个正整数，用空格隔开，保证数字都小于等于1000000，否则返回0");
        String str1 = s.nextLine();
        String[] array = str1.trim().split("\\s+");
        int num1 = Integer.parseInt(array[0]);
        int num2 = Integer.parseInt(array[1]);

        if(num1>num2){
            int temp = num1;
            num1= num2;
            num2 =temp;
        }
        if(num1<=0 || num1>1000000||num2<=0||num2>1000000){
            System.out.println(0);
        }else{
            int flag =0;
            for(int i= num1; i<=num2; i++){
                int currentNum = i;
                int numAbs = Math.abs(currentNum);
                int count = 0;
                while(numAbs >0){
                    count++;
                    numAbs /= 10;
                }
                int totalResult =0;
                for(int j=1; j<=count; j++){
                    int hundreds = (int)Math.pow(10,j-1);
                    int result = (currentNum/hundreds)%10;
                    int pow = (int)Math.pow(result,count);
                    totalResult += pow;
                }
                if(totalResult == currentNum){
                    flag++;
                }
            }
            System.out.println(flag);
        }
    }
}
