package ru.arlabs.taskservice.attempts;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import ru.arlabs.taskservice.attempts.internal.BucketAttemptService;
import ru.arlabs.taskservice.util.HttpUtil;


/**
 * @author Jeb
 */
@Configuration
public class AttemptConfiguration {

    @Bean
    @Scope("prototype")
    public AttemptService loginAttemptService(HttpUtil httpUtil) {
        return new BucketAttemptService(3, 30, httpUtil);
    }

    @Bean
    @Scope("prototype")
    public AttemptService changeAttemptService(HttpUtil httpUtil) {
        return new BucketAttemptService(1, 30, httpUtil);
    }
}
