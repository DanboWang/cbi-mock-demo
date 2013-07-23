package com.sky.cbi;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.sky.cbi.service.CBIDemoService;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-config.xml");
//        CBIDemoDAO cbiDemoDAO = (CBIDemoDAO)context.getBean("cbiDemoDAO");
//        cbiDemoDAO.getDBString1();
//        cbiDemoDAO.getDBString2();
        CBIDemoService service = (CBIDemoService)context.getBean("CBIDemoService");
        System.out.println(service.concatDBString());
    }
}
