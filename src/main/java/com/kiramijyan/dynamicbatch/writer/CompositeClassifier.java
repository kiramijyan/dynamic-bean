package com.kiramijyan.dynamicbatch.writer;

import com.kiramijyan.dynamicbatch.dto.OutputCustomer;
import lombok.Getter;
import org.springframework.batch.item.support.CompositeItemWriter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.classify.Classifier;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;

@Component
@Getter
public class CompositeClassifier implements Classifier<OutputCustomer, CompositeItemWriter<OutputCustomer>> {


    @Autowired
    private ApplicationContext context;

    private Map<String, CompositeItemWriter> compositeItemWriterMap = new HashMap<>();


    @PostConstruct
    public void init(){
        compositeItemWriterMap = context.getBeansOfType(CompositeItemWriter.class);
    }


    @Override
    public CompositeItemWriter<OutputCustomer> classify(OutputCustomer outputCustomer) {
        return compositeItemWriterMap.get(outputCustomer.getCountry());
    }
}
