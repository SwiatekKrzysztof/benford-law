package com.swiatek.benford.commons

import spock.lang.Specification

class BenfordLawTest extends Specification {

    BenfordLawValidator benfordLawValidator = new BenfordLawValidator()

    def "Benford law test"() {
        when: "Large enough dataset known to match Benford Law, such as powers of 2"
        Map<Integer, Long> powers = powersOfTwo(10000)
        then:
        benfordLawValidator.doesDataMatchBenfordLaw(powers)
        when: "Large dataset known to not match Benford Law such as roots of successive natural numbers"
        Map<Integer, Long> squares = squaresOfNaturalNumbers(10000)
        then:
        !benfordLawValidator.doesDataMatchBenfordLaw(squares)
        when: "Self test"
        Map<Integer, Long> ideal = benfordLawValidator.getIdealDigitsMapForSampleSize(10000)
        then:
        benfordLawValidator.doesDataMatchBenfordLaw(ideal)
    }

    static Map<Integer, Long> powersOfTwo(int n) {
        def map = [1: 0L, 2: 0L, 3: 0L, 4: 0L, 5: 0L, 6: 0L, 7: 0L, 8: 0L, 9: 0L]
        for (i in 0..<n) {
            Integer firstDigit = Integer.valueOf(BigInteger.TWO.pow(i).toString().substring(0, 1))
            map.put(firstDigit, map.get(firstDigit) + 1L)
        }
        return map
    }

    static Map<Integer, Long> squaresOfNaturalNumbers(int n) {
        def map = [1: 0L, 2: 0L, 3: 0L, 4: 0L, 5: 0L, 6: 0L, 7: 0L, 8: 0L, 9: 0L]
        for (i in 0..<n) {
            Integer firstDigit = Integer.valueOf(BigInteger.valueOf(i).sqrt().toString().substring(0, 1))
            if (firstDigit != 0)
                map.put(firstDigit, map.get(firstDigit) + 1L)
        }
        return map
    }
}
