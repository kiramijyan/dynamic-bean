package com.kiramijyan.dynamicbatch.reader;

import com.kiramijyan.dynamicbatch.beanfactory.DirectoryProvider;
import com.kiramijyan.dynamicbatch.dto.Gender;
import com.kiramijyan.dynamicbatch.dto.InputCustomer;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Component;

import java.io.File;


@Component
public class InputReader {

    @Autowired
    private DirectoryProvider directoryProvider;

    public FlatFileItemReader<InputCustomer> reader(){
        File file = new File(directoryProvider.getInput());
        if(!file.exists()){
            throw new IllegalStateException("File now found: " + directoryProvider.getInput());
        }

        FlatFileItemReader<InputCustomer> reader = new FlatFileItemReader<>();
        reader.setStrict(false);
        reader.setResource(new FileSystemResource(directoryProvider.getInput()));
        reader.setLinesToSkip(1);

        DefaultLineMapper<InputCustomer> defaultLineMapper = new DefaultLineMapper<>();

        DelimitedLineTokenizer delimitedLineTokenizer = new DelimitedLineTokenizer();
        delimitedLineTokenizer.setNames("id", "firstName", "lastName","email","phoneNumber","dateOfBirth","addressLine","gender","city","country","postalCode");
        delimitedLineTokenizer.setDelimiter(",");

        BeanWrapperFieldSetMapper<InputCustomer> beanWrapperFieldSetMapper = new BeanWrapperFieldSetMapper<>();
        beanWrapperFieldSetMapper.setTargetType(InputCustomer.class);

        defaultLineMapper.setLineTokenizer(delimitedLineTokenizer);
        defaultLineMapper.setFieldSetMapper(beanWrapperFieldSetMapper);

        reader.setLineMapper(defaultLineMapper);
        return reader;
    }
}
