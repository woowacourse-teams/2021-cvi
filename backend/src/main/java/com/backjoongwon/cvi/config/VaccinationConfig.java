package com.backjoongwon.cvi.config;

import com.backjoongwon.cvi.parser.Parser;
import com.backjoongwon.cvi.parser.VaccinationParser;
import com.backjoongwon.cvi.util.JsonMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class VaccinationConfig {

    @Bean
    public VaccinationParser vaccineParser() {
        return new VaccinationParser(new Parser(), new JsonMapper());
    }
}
