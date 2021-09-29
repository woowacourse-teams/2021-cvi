package com.cvi.config;

import com.cvi.parser.Parser;
import com.cvi.parser.VaccinationParser;
import com.cvi.util.JsonMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class VaccinationConfig {

    @Bean
    public VaccinationParser vaccineParser() {
        return new VaccinationParser(new Parser(), new JsonMapper());
    }
}

