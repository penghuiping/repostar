package com.php25.desktop.repostars.controller;

import com.php25.desktop.repostars.respository.entity.TbGist;
import com.php25.desktop.repostars.respository.entity.TbUser;
import com.php25.desktop.repostars.service.UserService;
import com.php25.desktop.repostars.util.LocalStorage;
import com.php25.desktop.repostars.view.RepoListCell;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author penghuiping
 * @date 2020/10/9 16:29
 */
@Component
public class AllStarRepoController extends BaseController {

    @FXML
    public ScrollPane scrollPane;

    @FXML
    public VBox container;

    @Autowired
    private UserService userService;

    @Autowired
    private LocalStorage localStorage;

    @Override
    public void start() throws Exception {
        scrollPane.getStyleClass().add("edge-to-edge");
        TbUser tbUser = localStorage.getLoginUser();
        List<TbGist> gistList = userService.getMyGist(tbUser.getLogin(), tbUser.getToken());
        if (null != gistList && !gistList.isEmpty()) {
            for (TbGist tbGist : gistList) {
                RepoListCell repoListCell = new RepoListCell(tbGist.getFullName(), tbGist.getDescription(), tbGist.getForks() + "");
                container.getChildren().add(repoListCell);
            }
        }
    }
}
