package ru.arlabs.taskservice.attempts.internal;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import io.github.bucket4j.*;
import lombok.extern.slf4j.Slf4j;
import ru.arlabs.taskservice.attempts.AttemptService;
import ru.arlabs.taskservice.attempts.exception.TooManyAttemptsException;
import ru.arlabs.taskservice.util.HttpUtil;

import javax.servlet.http.HttpServletRequest;
import java.time.Duration;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * @author Jeb
 */
@Slf4j
public class BucketAttemptService implements AttemptService {
    private static final int DEFAULT_MAX_ATTEMPT = 5;
    private static final int DEFAULT_SECONDS_REFILL = 60;
    private static final int DEFAULT_BUCKETS_EXPIRE = 10;

    private final int maxAttempt;
    private final int secondsRefill;

    private final LoadingCache<String, Bucket> attemptsCache;
    private final HttpUtil httpUtil;

    public BucketAttemptService(HttpUtil httpUtil) {
        this(DEFAULT_MAX_ATTEMPT, DEFAULT_SECONDS_REFILL, DEFAULT_BUCKETS_EXPIRE, httpUtil);
    }

    public BucketAttemptService(int maxAttempt, HttpUtil httpUtil) {
        this(maxAttempt, DEFAULT_SECONDS_REFILL, DEFAULT_BUCKETS_EXPIRE, httpUtil);
    }

    public BucketAttemptService(int maxAttempt, int secondsRefill, HttpUtil httpUtil) {
        this(maxAttempt, secondsRefill, DEFAULT_BUCKETS_EXPIRE, httpUtil);

    }

    public BucketAttemptService(int maxAttempt, int secondsRefill, int bucketsExpire, HttpUtil httpUtil) {
        this.maxAttempt = maxAttempt;
        this.secondsRefill = secondsRefill;
        this.attemptsCache = CacheBuilder.newBuilder()
                .expireAfterWrite(bucketsExpire, TimeUnit.MINUTES)
                .build(new CacheLoader<>() {
                    @Override
                    public Bucket load(String key) {
                        return newBucket();
                    }
                });
        this.httpUtil = httpUtil;
    }

    @Override
    public void attempt(String key) {
        Bucket bucket;
        try {
            bucket = attemptsCache.get(key);
        } catch (ExecutionException e) {
            log.error("Could not load value from cache", e);
            bucket = newBucket();
        }
        ConsumptionProbe consumptionProbe = bucket.tryConsumeAndReturnRemaining(1);
        if (!consumptionProbe.isConsumed()) {
            long waitForRefill = consumptionProbe.getNanosToWaitForRefill() / 1_000_000_000;
            throw new TooManyAttemptsException(waitForRefill, secondsRefill, consumptionProbe.getRemainingTokens());
        }
    }

    @Override
    public void attempt(HttpServletRequest request) {
        final String clientIP = httpUtil.getClientIP(request);
        attempt(clientIP);
    }

    @Override
    public void invalidate(HttpServletRequest request) {
        final String clientIP = httpUtil.getClientIP(request);
        invalidate(clientIP);
    }

    @Override
    public void invalidate(String key) {
        attemptsCache.invalidate(key);
    }

    private Bucket newBucket() {
        Refill refill = Refill.intervally(maxAttempt, Duration.ofSeconds(secondsRefill));
        Bandwidth limit = Bandwidth.classic(maxAttempt, refill);
        return Bucket4j.builder()
                .addLimit(limit)
                .build();
    }
}
