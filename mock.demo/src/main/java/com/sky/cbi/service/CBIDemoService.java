package com.sky.cbi.service;

import java.io.File;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sky.cbi.dao.CBIDemoDAO;
import com.sky.cbi.utils.CBIDemoUtil;

/**
 * CBI demo service layer Service.
 * This class will make some call to {@link CBIDemoDAO} to simulate the common service architecture.
 * @author taoyt
 *
 */
@Service
public class CBIDemoService {
    
    @Autowired
    private CBIDemoDAO cbiDemoDao;
    
    /**
     * Just a simple demo to join two Strings got from DB together.
     * @return
     */
    public String concatDBString(){
        String str1 = cbiDemoDao.getDBString1();
        // String str1 = "";
        String str2 = cbiDemoDao.getDBString2();
        /**
         * Usually, there will be some business logic here.
         */
        return str1+" + "+str2;
    }
    
    /**
     * A little complicated method which contains private method call and may throw RuntimeException.
     * @param str1
     * @param str2
     * @return
     */
    public String defaultConcatStr(String str1, String str2){
        String defaultSeparator = getDefaultSeparator();
        return str1+" "+defaultSeparator+" "+str2;
    }

    /**
     * Just a simple private method which should be exist as a single one for whatever reason in the reallity.
     * @return
     */
    private String getDefaultSeparator() {
        System.out.println("This may need communicate with some other system");
        String defaultSeparator = "#";
        String prefix = getDefaultSeparatorPrefix();
        if (prefix == null || prefix.length() == 0){
            return defaultSeparator;
        }
        return prefix + defaultSeparator;
    }
    
    private String getDefaultSeparatorPrefix() {
        return "~";
    }
    
    
    /**
     * This method will invoke a static method of CBIDemoUtil
     * @return
     */
    public int availableProcessors(){
        return CBIDemoUtil.getLocalCpuCount();
    }
    
    
    /**
     * A demo method which will fetch a number from a private method
     * @return the corresponding month number
     * @throws Exception if the target number is larger than 12.
     */
    public int getMonthNumber() throws Exception{
        Random random = new Random();
        int targetNum = random.nextInt();
        if (targetNum>12 || targetNum<0) {
            throw new Exception("Invalid month number : "+ targetNum);
        }
        // in the real world, there may be some other logic here.
        return targetNum;
    }
    
    
    /**
     * A method which will invoke {@link #generateYearMonth()} which may throw checked exception.
     * @return
     */
    public String generateYearMonth(){
        String thisYear = "2013";
        int monthNum;
        try {
            monthNum = getMonthNumber();
        } catch (Exception e) {
            // may be some log record here!
            return null;
        }
        return thisYear + "-" + monthNum;
    }

}
