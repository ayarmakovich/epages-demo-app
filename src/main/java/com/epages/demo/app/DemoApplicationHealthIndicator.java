package com.epages.demo.app;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component
public class DemoApplicationHealthIndicator implements HealthIndicator {
    @Override
    public Health health() {
        return new Health.Builder().up().withDetail("demo app", "alive").build();
    }
}
