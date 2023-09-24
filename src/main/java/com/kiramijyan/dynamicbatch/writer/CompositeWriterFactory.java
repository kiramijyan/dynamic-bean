package com.kiramijyan.dynamicbatch.writer;


import com.kiramijyan.dynamicbatch.dto.OutputCustomer;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.support.CompositeItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CompositeWriterFactory {
    @Autowired
    private CsvFlatFileItemWriterFactory csvFlatFileItemWriterFactory;

    public CompositeItemWriter<OutputCustomer> createCompositeWriter(String country) throws Exception {
        List<ItemWriter<? super OutputCustomer>> writers = new ArrayList<>(2);
        writers.add(csvFlatFileItemWriterFactory.createCsvWriter(country));
        CompositeItemWriter<OutputCustomer> itemWriter = new CompositeItemWriter<>();
        itemWriter.setDelegates(writers);
        return itemWriter;
    }
}
