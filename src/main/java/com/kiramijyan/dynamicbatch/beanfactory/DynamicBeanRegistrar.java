package com.kiramijyan.dynamicbatch.beanfactory;

import com.kiramijyan.dynamicbatch.writer.CompositeWriterFactory;
import org.springframework.batch.item.support.CompositeItemWriter;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class DynamicBeanRegistrar implements BeanDefinitionRegistryPostProcessor, ApplicationContextAware {

    @Autowired
    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        for (String country : applicationContext.getBean(DirectoryProvider.class).getCountries()) {
            BeanDefinition beanDefinition = BeanDefinitionBuilder.genericBeanDefinition(CompositeItemWriter.class, () -> {
                try {
                    return applicationContext.getBean(CompositeWriterFactory.class).createCompositeWriter(country);
                } catch (Exception e){
                    throw new RuntimeException(e);
                }
            })
                    .getBeanDefinition();
            registry.registerBeanDefinition(country, beanDefinition);
        }
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {

    }


}
