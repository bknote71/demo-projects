package com.example.onlineJudge.match;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SimpleExecuteStrategyManager {

    private final ApplicationContext ac;

    public ExecuteStrategy getMatchStrategy(String type) {
        return ac.getBeansOfType(ExecuteStrategy.class)
                .values()
                .stream()
                .filter(m -> type.equals(m.getType()))
                .findFirst()
                .get();
    }
}
