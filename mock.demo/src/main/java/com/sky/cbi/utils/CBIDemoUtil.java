package com.sky.cbi.utils;

/**
 * Just a demo utility in which all the methods are static.
 * @author taoyt
 *
 */
public class CBIDemoUtil {
    
    public static final int getLocalCpuCount(){
        int count = Runtime.getRuntime().availableProcessors();
        System.out.println("Current system has "+count+" CPUs.");
        return count;
    }

}
