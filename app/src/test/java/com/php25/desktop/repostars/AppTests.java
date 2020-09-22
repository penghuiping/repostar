package com.php25.desktop.repostars;

import com.php25.common.core.util.JsonUtil;
import com.php25.desktop.repostars.github.GistManager;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
class AppTests {

    @Autowired
    GistManager gistManager;


    @Test
    void getAllStarredGistTest() {
        for (int i = 0; i < 3; i++) {
            var result = gistManager.getAllStarredGist("penghuiping", 1, 3);
            log.info("result:{}", JsonUtil.toJson(result));
        }

    }

}
