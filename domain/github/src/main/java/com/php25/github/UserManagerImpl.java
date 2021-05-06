package com.php25.github;

import com.php25.common.core.exception.Exceptions;
import com.php25.common.core.util.JsonUtil;
import com.php25.github.dto.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

/**
 * @author penghuiping
 * @date 2020/9/22 17:23
 */
@Service
public class UserManagerImpl implements UserManager {

    @Autowired
    private HttpClient httpClient;

    @Override
    public User getUserInfo(String token) {
        try {
            var uri = new URI(Constants.GET_USER_INFO);
            var request = HttpRequest.newBuilder()
                    .timeout(Duration.ofSeconds(Constants.TIMEOUT))
                    .uri(uri)
                    .GET()
                    .header("Authorization", String.format("token %s", token))
                    .header("Accept", "application/vnd.github.v3+json").build();
            var response = httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));
            var result = response.get(Constants.TIMEOUT, TimeUnit.SECONDS);
            return JsonUtil.fromJson(result.body(), User.class);
        } catch (Exception e) {
            throw Exceptions.throwIllegalStateException("获取个人信息失败", e);
        }
    }
}
