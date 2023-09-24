package com.kiramijyan.dynamicbatch.aggregator;

import com.kiramijyan.dynamicbatch.dto.OutputCustomer;
import org.springframework.batch.item.file.transform.LineAggregator;
import org.springframework.stereotype.Component;

import java.util.StringJoiner;

@Component
public class CsvLineAggregator implements LineAggregator<OutputCustomer> {
    @Override
    public String aggregate(OutputCustomer outputCustomer) {
        StringJoiner stringJoiner = new StringJoiner(",");
        stringJoiner.add(outputCustomer.getId().toString());
        stringJoiner.add(outputCustomer.getFirstName());
        stringJoiner.add(outputCustomer.getLastName());
        stringJoiner.add(outputCustomer.getEmail());
        stringJoiner.add(outputCustomer.getPhoneNumber());
        stringJoiner.add(outputCustomer.getDateOfBirth().toString());
        stringJoiner.add(outputCustomer.getAddressLine());
        stringJoiner.add(outputCustomer.getGender().toString());
        stringJoiner.add(outputCustomer.getCity());
        stringJoiner.add(outputCustomer.getCountry());
        stringJoiner.add(outputCustomer.getPostalCode());
        stringJoiner.add(outputCustomer.getPriceReduction().toString());
        return stringJoiner.toString();
    }
}
