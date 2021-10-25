package com.swiatek.benford.parser;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ParserConfiguration
{
    @Bean
    ParserFacade parserFacade(InputParser inputParser) {
       return new ParserFacade(inputParser);
    }

    @Bean
    InputParser inputParser() {
        return new InputParser();
    }
}
