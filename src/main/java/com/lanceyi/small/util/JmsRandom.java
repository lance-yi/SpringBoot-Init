package com.lanceyi.small.util;

import java.util.Random;

/**
 * Created by e311 on 2017/4/19.
 */
public class JmsRandom {


    public static String genRanNoChar(int pwd_len) {
        int i; // 生成的随机数
        int count = 0; // 生成的密码的长度
        char[] str = { 'a', 'b', 'c', 'd', 'e', 'f', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C','D', 'E', 'F' };
        StringBuffer pwd = new StringBuffer("");
        Random r = new Random();
        while (count < pwd_len) {
            i = Math.abs(r.nextInt(str.length)); // 生成的数最大为str.length-1
            if (i >= 0 && i < str.length) {
                pwd.append(str[i]);
                count++;
            }
        }
        return pwd.toString();
    }


    public static String genRanNo(int pwd_len) {
        int i; // 生成的随机数
        int count = 0; // 生成的密码的长度
        char[] str = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
        StringBuffer pwd = new StringBuffer("");
        Random r = new Random();
        while (count < pwd_len) {
            i = Math.abs(r.nextInt(str.length)); // 生成的数最大为str.length-1
            if (i >= 0 && i < str.length) {
                pwd.append(str[i]);
                count++;
            }
        }
        return pwd.toString();
    }


    public static String genChar(int pwd_len) {
        int i; // 生成的随机数
        int count = 0; // 生成的密码的长度
        char[] str = { 'a', 'b', 'c', 'd', 'e', 'f', 'A', 'B', 'C','D', 'E', 'F' };
        StringBuffer pwd = new StringBuffer("");
        Random r = new Random();
        while (count < pwd_len) {
            i = Math.abs(r.nextInt(str.length)); // 生成的数最大为str.length-1
            if (i >= 0 && i < str.length) {
                pwd.append(str[i]);
                count++;
            }
        }
        return pwd.toString();
    }
}
