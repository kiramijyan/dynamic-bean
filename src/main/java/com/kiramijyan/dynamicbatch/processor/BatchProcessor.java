package com.kiramijyan.dynamicbatch.processor;

import com.kiramijyan.dynamicbatch.beanfactory.DirectoryProvider;
import com.kiramijyan.dynamicbatch.dto.InputCustomer;
import com.kiramijyan.dynamicbatch.dto.OutputCustomer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BatchProcessor implements ItemProcessor<InputCustomer, OutputCustomer> {

    @Autowired
    private DirectoryProvider directoryProvider;


    @Override
    public OutputCustomer process(InputCustomer input) throws Exception {
        return  mapToOutputCustomer(input);
    }


    private OutputCustomer mapToOutputCustomer(InputCustomer inputCustomer) {
        OutputCustomer outputCustomer = new OutputCustomer();

        outputCustomer.setId(inputCustomer.getId());
        outputCustomer.setFirstName(inputCustomer.getFirstName());
        outputCustomer.setLastName(inputCustomer.getLastName());
        outputCustomer.setEmail(inputCustomer.getEmail());
        outputCustomer.setPhoneNumber(inputCustomer.getPhoneNumber());
        outputCustomer.setDateOfBirth(inputCustomer.getDateOfBirth());
        outputCustomer.setAddressLine(inputCustomer.getAddressLine());
        outputCustomer.setGender(inputCustomer.getGender());
        outputCustomer.setCity(inputCustomer.getCity());
        outputCustomer.setCountry(inputCustomer.getCountry());
        outputCustomer.setPostalCode(inputCustomer.getPostalCode());

        outputCustomer.setPriceReduction(directoryProvider.getCountryPriceReductionMap().get(inputCustomer.getCountry()));

        System.out.println(outputCustomer.toString());

        return outputCustomer;
    }
}
