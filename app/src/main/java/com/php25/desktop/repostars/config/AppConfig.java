package com.php25.desktop.repostars.config;

import com.php25.common.core.mess.LruCache;
import com.php25.common.core.mess.LruCacheImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.http.HttpClient;
import java.time.Duration;
import java.time.temporal.ChronoUnit;

/**
 * @author penghuiping
 * @date 2020/9/22 16:35
 */
@Configuration
public class AppConfig {

    @Bean
    public HttpClient httpClient() {
        return HttpClient.newBuilder().connectTimeout(Duration.of(10, ChronoUnit.SECONDS)).build();
    }

    @Bean
    public LruCache<String, Object> lruCache() {
        return new LruCacheImpl<>(1024);
    }
}
