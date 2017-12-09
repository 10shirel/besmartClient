package com.besmart.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@SpringBootConfiguration
@PropertySource("application.properties")
public class Configurations {

    @Value("${range.of.triangle.edg.min}")
    protected int rangeOfTriangleEdgMin;

    @Value("${range.of.triangle.edg.max}")
    protected int rangeOfTriangleEdgMax;

    @Value("${fixed.delay.in.milliseconds}")
    protected int fixedDelayInMilliseconds;


    public int getRangeOfTriangleEdgMin() {
        return rangeOfTriangleEdgMin;
    }

    public int getRangeOfTriangleEdgMax() {
        return rangeOfTriangleEdgMax;
    }

    public int getFixedDelayInMilliseconds() {
        return fixedDelayInMilliseconds;
    }
}
