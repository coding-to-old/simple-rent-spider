package cto.github.rent.config;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class SpringContext implements ApplicationContextAware {
	
    private static ApplicationContext context = null;
    
    @Override  
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;  
    }

    public static ApplicationContext getApplicationContext() {
        return context;  
    }  
}
