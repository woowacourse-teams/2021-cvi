package com.backjoongwon.cvi.config;

import com.backjoongwon.cvi.parser.Parser;
import com.backjoongwon.cvi.parser.VacinationParser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class VaccinationConfig {

    @Bean
    public VacinationParser vaccineParser() {
        return new VacinationParser(new Parser());
    }
}
