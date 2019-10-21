package com.rbkmoney.cm.config;

import com.rbkmoney.cm.converter.ClaimConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ConversionServiceFactoryBean;

import java.util.Set;

@Configuration
@ComponentScan( basePackages = {"com.rbkmoney.cm.converter"})
public class ConverterConfig {

    @Bean
    ConversionServiceFactoryBean conversionService(Set<ClaimConverter> converters) {
        ConversionServiceFactoryBean bean = new ConversionServiceFactoryBean();
        bean.setConverters(converters);
        return bean;
    }

}
