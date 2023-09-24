package com.kiramijyan.dynamicbatch.config;

import com.kiramijyan.dynamicbatch.dto.InputCustomer;
import com.kiramijyan.dynamicbatch.dto.OutputCustomer;
import com.kiramijyan.dynamicbatch.processor.BatchProcessor;
import com.kiramijyan.dynamicbatch.reader.InputReader;
import com.kiramijyan.dynamicbatch.writer.CompositeClassifier;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.step.builder.SimpleStepBuilder;
import org.springframework.batch.core.step.skip.AlwaysSkipItemSkipPolicy;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.support.ClassifierCompositeItemWriter;

import org.springframework.batch.item.support.CompositeItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.classify.Classifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private InputReader inputReader;

    @Autowired
    private BatchProcessor batchProcessor;

    @Autowired
    private CompositeClassifier compositeClassifier;

    @Bean
    public ClassifierCompositeItemWriter<OutputCustomer> classifierCompositeItemWriter(@Autowired CompositeClassifier compositeClassifier){
        ClassifierCompositeItemWriter<OutputCustomer> compositeItemWriter = new ClassifierCompositeItemWriter<>();
        Classifier<OutputCustomer, ItemWriter<? super OutputCustomer>> classifier = compositeClassifier :: classify;
        compositeItemWriter.setClassifier(classifier);
        return compositeItemWriter;
    }

    @Bean
    public Step readProcessWriteStep(@Autowired CompositeClassifier compositeClassifier){
        SimpleStepBuilder<InputCustomer, OutputCustomer> stepBuilder = stepBuilderFactory.get("read process write")
                .<InputCustomer, OutputCustomer>chunk(10)
                .reader(inputReader.reader())
                .processor(batchProcessor)
                .writer(classifierCompositeItemWriter(compositeClassifier))
                .faultTolerant()
                .processorNonTransactional()
                .skipPolicy(new AlwaysSkipItemSkipPolicy());

        for (CompositeItemWriter<?> composite : compositeClassifier.getCompositeItemWriterMap().values()){
            if (composite != null){
                stepBuilder.stream(composite);
            }
        }
        return stepBuilder.build();
    }

    @Bean
    public Job job(@Autowired CompositeClassifier compositeClassifier){
        return jobBuilderFactory.get("JOB")
                .incrementer(new RunIdIncrementer())
                .start(readProcessWriteStep(compositeClassifier))
                .build();
    }
}
