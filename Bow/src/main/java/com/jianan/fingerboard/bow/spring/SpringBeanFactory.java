package com.jianan.fingerboard.bow.spring;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author by jianan.liu on 16/12/2.
 */
public class SpringBeanFactory implements BeanFactoryAware {

    private static BeanFactory beanFactory;

    private void initContext() {
        String springXml = "classpath:/spring/spring-context.xml";
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext(springXml);
        setBeanFactory(applicationContext);
    }

    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    public static SpringBeanFactory buildInit() {
        SpringBeanFactory beanFactory = new SpringBeanFactory();
        beanFactory.initContext();
        return beanFactory;
    }

    public static <T> T getBean(Class<T> type) throws BeansException {
        return beanFactory.getBean(type);
    }

}
