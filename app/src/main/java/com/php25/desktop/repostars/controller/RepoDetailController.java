package com.php25.desktop.repostars.controller;

import com.php25.common.core.mess.LruCache;
import com.php25.common.core.util.StringUtil;
import com.php25.desktop.repostars.util.GlobalUtil;
import com.php25.desktop.repostars.util.LocalStorage;
import com.php25.github.ReposManager;
import com.php25.github.dto.RepoReadme;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.data.MutableDataSet;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.web.WebView;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Base64;

/**
 * @author penghuiping
 * @date 2020/10/14 11:11
 */
@Slf4j
@Component
public class RepoDetailController extends BaseController {

    public Button backBtn;
    public Label titleLabel;
    public Long id;
    public String title;
    public Scene previousScene;
    public WebView container;

    @Autowired
    private LruCache<String, Object> lruCache;

    @Autowired
    private LocalStorage localStorage;

    @Autowired
    private ReposManager reposManager;

    @Override
    public void start() throws Exception {
        backBtn.setOnMouseClicked(this);
        titleLabel.setText(title);
        Object htmlObj = lruCache.getValue(title);
        if (null == htmlObj || StringUtil.isBlank(htmlObj.toString())) {
            var user = localStorage.getLoginUser();
            RepoReadme repoReadme = reposManager.getRepoReadme(user.getToken(), title);
            if (null == repoReadme) {
                return;
            }
            var content = new String(Base64.getMimeDecoder().decode(repoReadme.getContent()));
            MutableDataSet options = new MutableDataSet();
            Parser parser = Parser.builder(options).build();
            HtmlRenderer renderer = HtmlRenderer.builder(options).build();
            Node document = parser.parse(content);
            String html = renderer.render(document);
            lruCache.putValue(title, html);
            htmlObj = html;
        }
        String html = htmlObj.toString();
        container.getEngine().loadContent(html);
    }

    @Override
    public void handleMouseEvent(MouseEvent mouseEvent) throws Exception {
        if (mouseEvent.getSource() instanceof Button) {
            var button = (Button) mouseEvent.getSource();
            switch (button.getId()) {
                case "backBtn": {
                    GlobalUtil.goNextScene(mouseEvent, previousScene);
                    break;
                }
                default: {
                    break;
                }
            }
        }
    }
}
