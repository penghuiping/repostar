package com.php25.desktop.repostars.github;

import com.fasterxml.jackson.core.type.TypeReference;
import com.php25.common.core.mess.LruCache;
import com.php25.common.core.util.JsonUtil;
import com.php25.desktop.repostars.exception.Exceptions;
import com.php25.desktop.repostars.github.dto.Gist;
import lombok.extern.slf4j.Slf4j;
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
 * @date 2020/9/22 16:34
 */
@Service
@Slf4j
public class GistManagerImpl implements GistManager {

    private static final String LIST_STARRED_GISTS = "https://api.github.com/users/%s/starred";
    @Autowired
    private HttpClient httpClient;
    @Autowired
    private LruCache<String, Object> lruCache;

    @Override
    public List<Gist> getAllStarredGist(String username, Integer pageNum, Integer pageSize) {
        try {
            URI uri = new URI(String.format(LIST_STARRED_GISTS + "?page%d&&per_page=%d", username, pageNum, pageSize));
            Object result = lruCache.getValue(uri.toString());
            if (null != result) {
                return (List<Gist>) result;
            } else {
                var request = HttpRequest.newBuilder(uri).GET().build();
                var response = httpClient.send(request, HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));
                result = JsonUtil.fromJson(response.body(), new TypeReference<List<Gist>>() {
                });
                lruCache.putValue(uri.toString(), result);
                return (List<Gist>) result;
            }

        } catch (Exception e) {
            throw Exceptions.throwIllegalStateException("出错啦!", e);
        }
    }


}
