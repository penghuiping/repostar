package com.php25.desktop.repostars.github;

import com.fasterxml.jackson.core.type.TypeReference;
import com.php25.common.core.exception.Exceptions;
import com.php25.common.core.util.JsonUtil;
import com.php25.desktop.repostars.github.dto.Repos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * @author penghuiping
 * @date 2020/9/23 13:36
 */
@Service
public class ReposManagerImpl implements ReposManager {

    @Autowired
    private HttpClient httpClient;

    @Override
    public List<Repos> getReposList(String token) {
        try {
            var uri = new URI(Constants.LIST_USER_REPOS);
            var request = HttpRequest.newBuilder().uri(uri).GET()
                    .header("Authorization", String.format("token %s", token))
                    .header("Accept", "application/vnd.github.v3+json").build();
            var response = httpClient.send(request, HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));
            return JsonUtil.fromJson(response.body(), new TypeReference<List<Repos>>() {
            });
        } catch (Exception e) {
            throw Exceptions.throwIllegalStateException("获取个人仓库项目列表失败", e);
        }
    }
}
