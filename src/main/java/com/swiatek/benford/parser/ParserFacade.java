package com.swiatek.benford.parser;

import org.springframework.stereotype.Component;

public class ParserFacade
{
    InputParser inputParser;

    public ParserFacade(final InputParser inputParser)
    {
        this.inputParser = inputParser;
    }
}
