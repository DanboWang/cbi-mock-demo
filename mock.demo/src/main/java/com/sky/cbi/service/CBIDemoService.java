package com.sky.cbi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sky.cbi.dao.CBIDemoDAO;

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

}
