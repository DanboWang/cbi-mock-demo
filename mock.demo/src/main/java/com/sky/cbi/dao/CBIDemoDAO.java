package com.sky.cbi.dao;

import org.springframework.stereotype.Component;

/**
 * CBI demo DAO layer class. 
 * A little anti-pattern since interface is not used.
 * But it's just for demo so we can see how does jmockit deal with common class.
 * 
 * @author taoyt
 *
 */
@Component
public class CBIDemoDAO {
    
    public String getDBString1(){
        System.out.println("I am communicating with DB from getDBString1!"); 
        return "DBString1";
    }
    
    public String getDBString2(){
        System.out.println("I am communicating with DB from getDBString2!"); 
        return "DBString2";
    }

}
