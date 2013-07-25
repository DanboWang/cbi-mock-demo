package com.sky.cbi.service;

import java.util.Random;

import mockit.Deencapsulation;
import mockit.Expectations;
import mockit.Mocked;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.sky.cbi.dao.CBIDemoDAO;
import com.sky.cbi.utils.CBIDemoUtil;

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
    
    /**
     * This case will show you how to mock a private method in your UT
     */
    @Test
    public void testDefaultConcatStr(){
        final String mockedStr = "$";
        final String str1 = "test1";
        final String str2 = "test2";
        // record private method getDefaultSeparator in service
        // make service as a passed in parameter so it will be treated as a mocked type.
        new Expectations(service) {
            {
                // Deencapsulation is a useful reflection util in jmockit.
                Deencapsulation.invoke(service, "getDefaultSeparator");
                result = mockedStr;
            }
        };
        
        // replay
        String actualStr = service.defaultConcatStr(str1, str2);
        // verify
        Assert.assertEquals(str1+" "+mockedStr+" "+str2, actualStr);
    }
    
    /**
     * This case will show you how to test a private method in your UT
     */
    @Test
    public void testGetDefaultSeparator(){
        final String mockedPrefix = "@@@";
        // record
        /**
         * strictly, this is a dynamically mock in jmockit.
         */
        new Expectations(service) {
            {
                Deencapsulation.invoke(service, "getDefaultSeparatorPrefix");
                times = 3;
                returns(null, "", mockedPrefix);
            }
        };
        // replay && verify
        // Notice : here use the Deencapsulation to invoke a private method
        Assert.assertEquals("#",Deencapsulation.invoke(service, "getDefaultSeparator"));
        Assert.assertEquals("#",Deencapsulation.invoke(service, "getDefaultSeparator"));
        Assert.assertEquals(mockedPrefix+"#",Deencapsulation.invoke(service, "getDefaultSeparator"));
    }
    
    /**
     * This case will show you how to mock a static method.
     */
    @Test
    public void testAvailableProcessors(){
        // record
        new Expectations(CBIDemoUtil.class) {
            {
                CBIDemoUtil.getLocalCpuCount(); result = 200;
            }
        };
        // replay && verify
        Assert.assertEquals(200, service.availableProcessors());
    }
    
    /**
     * This test case will show you how to mock a local variable and check exceptions in UT.
     * @param random
     */
    @Test
    public void testGetMonthNumber(@Mocked(methods={"nextInt"}) final Random random){
        // record
        new Expectations() {
            {
                random.nextInt();
                times = 5;
                returns(12,13,0,5,-1);
            }
        };
        // replay && verify
        try {
            //case1: 12 is a valid month
            Assert.assertEquals(12,service.getMonthNumber());
            //case2: 13 is an invalid month, should get an exception
            try {
                service.getMonthNumber();
                Assert.fail("Here never should be reached!");
            } catch (Exception e) {
                Assert.assertEquals("Invalid month number : 13", e.getMessage());
            }
            //case3: 0 is an invalid month, should get an exception
            try {
                service.getMonthNumber();
                Assert.fail("Here never should be reached!");
            } catch (Exception e) {
                Assert.assertEquals("Invalid month number : 0", e.getMessage());
            }
            //case4: 5 is a valid month
            Assert.assertEquals(5,service.getMonthNumber());
            //case5: 0 is an invalid month, should get an exception
            try {
                service.getMonthNumber();
                Assert.fail("Here never should be reached!");
            } catch (Exception e) {
                Assert.assertEquals("Invalid month number : -1", e.getMessage());
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    
    /**
     * This test case will show you how mock the exceptions you want.
     * @throws Exception
     */
    @Test
    public void testGenerateYearMonth() throws Exception{
        // record
        new Expectations(service) {
            {
                service.getMonthNumber();
                times = 2;
                returns(new Exception("Invalid number!"), 5);
            }
        };
        // replay && verify
        Assert.assertNull(service.generateYearMonth());
        Assert.assertEquals("2013-5", service.generateYearMonth());
    }
}
