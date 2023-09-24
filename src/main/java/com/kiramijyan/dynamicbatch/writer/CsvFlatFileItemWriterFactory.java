package com.kiramijyan.dynamicbatch.writer;

import com.kiramijyan.dynamicbatch.aggregator.CsvLineAggregator;
import com.kiramijyan.dynamicbatch.beanfactory.DirectoryProvider;
import com.kiramijyan.dynamicbatch.callbaks.CsvHeaderCallback;
import com.kiramijyan.dynamicbatch.dto.OutputCustomer;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

@Component
public class CsvFlatFileItemWriterFactory {

    @Autowired
    private DirectoryProvider directoryProvider;

    @Autowired
    private CsvHeaderCallback csvHeaderCallback;

    @Autowired
    private CsvLineAggregator csvLineAggregator;

    public FlatFileItemWriter<OutputCustomer> createCsvWriter(String country) throws Exception {
        Resource resource = new FileSystemResource("src/main/resources/output"+"/"+ country + "/" + "output.csv");
        FlatFileItemWriter<OutputCustomer> writer = new FlatFileItemWriter<>();
        writer.setResource(resource);
        writer.setAppendAllowed(true);
        writer.setHeaderCallback(csvHeaderCallback);
        writer.setLineAggregator(csvLineAggregator);
        writer.afterPropertiesSet();
        return writer;
    }
}
