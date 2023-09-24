package com.kiramijyan.dynamicbatch.callbaks;

import com.kiramijyan.dynamicbatch.beanfactory.DirectoryProvider;
import org.springframework.batch.item.file.FlatFileHeaderCallback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.Writer;

@Component
public class CsvHeaderCallback implements FlatFileHeaderCallback {

    @Autowired
    private DirectoryProvider directoryProvider;


    @Override
    public void writeHeader(Writer writer) throws IOException {
        writer.write(directoryProvider.getCsvHeader());
    }
}
