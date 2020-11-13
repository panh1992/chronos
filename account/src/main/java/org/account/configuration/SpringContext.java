package org.account.configuration;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;

@Component
public class SpringContext implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(@Nullable ApplicationContext applicationContext) {
        if (SpringContext.applicationContext == null) {
            SpringContext.applicationContext = applicationContext;
        }
    }

    public static <T> T getBean(Class<T> clazz) {
        return SpringContext.applicationContext.getBean(clazz);
    }

}
