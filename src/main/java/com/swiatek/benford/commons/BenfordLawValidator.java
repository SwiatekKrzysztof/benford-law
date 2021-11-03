package com.swiatek.benford.commons;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class BenfordLawValidator {
    /**
     * Based on John Morrow paper: http://www.johnmorrow.info/projects/benford/benfordMain.pdf
     */
    public boolean doesDataMatchBenfordLaw(Map<Integer, Long> digitsWithCount) {
        digitsWithCount.remove(null);
        final Long sampleSize = digitsWithCount.values().stream().reduce(Long::sum).orElse(0L);
        double m = Math.sqrt(sampleSize) * calculateMaxDeviation(digitsWithCount, sampleSize);
        double d = Math.sqrt(sampleSize) * calculateDistance(digitsWithCount, sampleSize);
        //assumed lowest significance level
        return (m <= 1.191 && d <= 1.012);
    }

    public Map<Integer, Long> getIdealDigitsMapForSampleSize(Long sampleSize) {
        Map<Integer, Long> map = new HashMap<>();
        for (int i = 1; i < 10; i++) {
            double doubleCount = Math.log10((1.0 + (1.0 / i))) * sampleSize.doubleValue();
            map.put(i, Math.round(doubleCount));
        }
        return map;
    }

    private double calculateMaxDeviation(Map<Integer, Long> digitsWithCount, Long sampleSize) {
        double maxDeviation = -1;
        for (int i = 1; i < 10; i++) {
            double deviation = Math.abs(digitsWithCount.getOrDefault(i, 0L) / (sampleSize * 1.0) - Math.log10(1.0 + 1.0 / i));
            if (deviation > maxDeviation) {
                maxDeviation = deviation;
            }
        }
        return maxDeviation;
    }

    private double calculateDistance(Map<Integer, Long> digitsWithCount, Long sampleSize) {
        double distance = 0;
        for (int i = 1; i < 10; i++) {
            distance += Math.pow((digitsWithCount.getOrDefault(i, 0L) / (sampleSize * 1.0) - Math.log10(1.0 + 1.0 / i)), 2.0);
        }
        return Math.sqrt(distance);
    }


}
