package top.kudaompq.utils;

import org.apache.commons.codec.digest.MurmurHash3;

/**
 * @description: Hash工具类
 * @author: kudaompq
 * @date: 2022/2/3 23:36
 * @version: v1.0
 */

public class HashUtils {

    public static long getMurmurHash32(String str) {
        int i = MurmurHash3.hash32(str);
        long num = i < 0 ? Integer.MAX_VALUE - (long) i : i;
        return num;
    }
}
