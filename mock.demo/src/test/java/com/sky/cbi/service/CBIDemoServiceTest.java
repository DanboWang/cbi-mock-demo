package com.sky.cbi.service;

import mockit.Expectations;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.sky.cbi.dao.CBIDemoDAO;

/**
 * UT demo for CBIDemoServiceTest.java
 * 
 * @author taoyt
 */
public class CBIDemoServiceTest {

    private static CBIDemoService service;

    @BeforeClass
    public static void setUp() {
        // Notice : you don't need to prepare another spring configure file apart from the "real one".
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-config.xml");
        service = (CBIDemoService) context.getBean("CBIDemoService");
    }

    /**
     * This is the simplest and most common use case for jmockit.
     */
    @Test
    public void testConcatDBString() {

        final String str1 = "test1";
        final String str2 = "test2";

        String actualResult;
        // TODO how about assert before mock ?!
        // actualResult = service.concatDBString();
        // Assert.assertEquals(str1 + " + " + str2, actualResult);

        /**
         * <pre>
         * This Expectations is a record phase which will record what behavior you want 
         * when invoking some mocked instance. 
         * Notice : This is a <strong>strict</strong> expectation by default!
         * </pre>
         */
        new Expectations() {

            // Notice : cbiDemoDao is a private field in CBIDemoService.
            // And it's injected through Spring container.
            CBIDemoDAO cbiDemoDao;
            {
                cbiDemoDao.getDBString1();
                result = str1;
                // default means the following invocation times.
                // times = 1;
                cbiDemoDao.getDBString2();
                result = str2;
            }
        };

        /**
         * replay phase (including the implicit verify phase for mock expectation)
         */
        actualResult = service.concatDBString();
        /**
         * verify phase
         */
        Assert.assertEquals(str1 + " + " + str2, actualResult);

    }
}
