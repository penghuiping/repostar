package com.php25.desktop.repostars.config;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.php25.desktop.repostars.util.LocalStorage;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.http.HttpClient;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author penghuiping
 * @date 2020/9/22 16:35
 */
@Configuration
public class AppConfig {

    @Bean
    public HttpClient httpClient() {
        return HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_1_1)
                .connectTimeout(Duration.of(10, ChronoUnit.SECONDS))
                .build();
    }

    @Bean
    public Cache<String, Object> lruCache() {
        return CacheBuilder.newBuilder().maximumSize(1024).build();
    }

    @Bean
    public ExecutorService executorService() {
        return new ThreadPoolExecutor(1, 10,
                60L, TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(512),
                new ThreadFactoryBuilder().setNameFormat("repostar-pool-%d").build(), new ThreadPoolExecutor.AbortPolicy());
    }

    @Bean
    public LocalStorage localStorage() {
        return new LocalStorage();
    }
}
