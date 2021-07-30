package com.backjoongwon.cvi.parser;

import com.backjoongwon.cvi.dto.VaccineParserResponse;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class VaccineParserTest {

    @Test
    void parseToPublicData() {
        VaccineParser vaccineParser = new VaccineParser(new Parser());
        VaccineParserResponse vaccineParserResponse = vaccineParser.parseToPublicData();

        System.out.println("vaccineParserResponse.getCurrentCount() = " + vaccineParserResponse.getCurrentCount());
    }
}