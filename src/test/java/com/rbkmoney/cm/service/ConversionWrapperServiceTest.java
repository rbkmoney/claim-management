package com.rbkmoney.cm.service;

import com.rbkmoney.cm.config.ConverterConfig;
import com.rbkmoney.cm.exception.InvalidChangesetException;
import com.rbkmoney.cm.util.MockUtil;
import com.rbkmoney.damsel.claim_management.Modification;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@ContextConfiguration(
        classes = {
                ConverterConfig.class
        }
)
@TestPropertySource("classpath:application.yml")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@Slf4j
public class ConversionWrapperServiceTest {

    @Autowired
    private ConversionService conversionService;

    @Test(expected = InvalidChangesetException.class)
    public void conversionTest() {
        ConversionWrapperService conversionWrapperService = new ConversionWrapperService(conversionService);
        Modification modification = MockUtil.generateTBase(Modification.class);
        conversionWrapperService.convertModifications(List.of(modification, modification));
    }
}
