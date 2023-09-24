package com.kiramijyan.dynamicbatch.beanfactory;

import lombok.Getter;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@Component
@Getter
public class DirectoryProvider implements ApplicationContextAware {
    private ApplicationContext applicationContext;
    private Map<String, Double> countryPriceReductionMap;
    private String[] countries;
    private String[] priceReductionInStringArray;
    private List<Double> priceReductions;
    private String input;
    private String csvHeader;

    private String rootPath;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;

        input = applicationContext.getEnvironment().getProperty("path.input", String.class);
        priceReductionInStringArray = applicationContext.getEnvironment().getProperty("data.priceReduction", String[].class);
        countries = applicationContext.getEnvironment().getProperty("data.countries", String[].class);
        priceReductions = convertToDoubleList(priceReductionInStringArray);
        countryPriceReductionMap = createMapFromLists(Arrays.asList(countries), priceReductions);
        csvHeader = applicationContext.getEnvironment().getProperty("callback.csv.header", String.class);
        rootPath = applicationContext.getEnvironment().getProperty("path.root", String.class);

        System.out.println(input);
        System.out.println(priceReductionInStringArray.length);

        for(String country : countries) {
            System.out.println(country);
        }

        for(String price : priceReductionInStringArray) {
            System.out.println(price);
        }
    }

    private Map<String, Double> createMapFromLists(List<String> country, List<Double> priceReductionPercentage){
        return IntStream.range(0, country.size())
                .boxed()
                .collect(Collectors.toMap(country::get, priceReductionPercentage::get));
    }

    private List<Double> convertToDoubleList(String[] priceReductionStringArray){

        if(priceReductionStringArray == null || priceReductionStringArray.length == 0){
            return Collections.emptyList();
        }

        List<Double> convertedList = Arrays.stream(priceReductionStringArray)
                .map(String::trim)
                .map(Double::valueOf)
                .collect(Collectors.toList());

        if (convertedList.isEmpty()){
            return Stream.generate(() -> 0.0)
                    .limit(priceReductionStringArray.length)
                    .collect(Collectors.toList());
        }
        return convertedList;
    }
}
