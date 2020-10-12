package com.php25.desktop.repostars.controller;

import com.php25.desktop.repostars.respository.entity.TbRepos;
import com.php25.desktop.repostars.respository.entity.TbUser;
import com.php25.desktop.repostars.service.UserService;
import com.php25.desktop.repostars.util.GlobalUtil;
import com.php25.desktop.repostars.util.LocalStorage;
import com.php25.desktop.repostars.view.RepoListCell;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author penghuiping
 * @date 2020/10/9 10:35
 */
@Component
public class MyRepoController extends BaseController {

    @FXML
    public VBox container;

    @FXML
    public ScrollPane scrollPane;

    @FXML
    public Button backBtn;

    @Autowired
    private UserService userService;

    @Autowired
    private LocalStorage localStorage;

    @Override
    public void start() throws Exception {
        scrollPane.getStyleClass().add("edge-to-edge");
        TbUser tbUser = localStorage.getLoginUser();
        List<TbRepos> tbReposList = userService.getMyRepos(tbUser.getLogin(), tbUser.getToken());
        if (null != tbReposList && !tbReposList.isEmpty()) {
            for (TbRepos tbRepos : tbReposList) {
                RepoListCell repoListCell = new RepoListCell(tbRepos.getFullName(), tbRepos.getDescription(), tbRepos.getWatchers() + "");
                container.getChildren().add(repoListCell);
            }
        }
        backBtn.setOnMouseClicked(this);
    }

    @Override
    public void handleMouseEvent(MouseEvent mouseEvent) throws Exception {
        Button button = (Button) mouseEvent.getSource();
        switch (button.getId()) {
            case "backBtn": {
                GlobalUtil.goNextScene("controller/nav_controller.fxml", mouseEvent, this.applicationContext);
                break;
            }
            default: {
                break;
            }
        }
    }
}
