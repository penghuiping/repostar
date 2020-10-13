package com.php25.desktop.repostars.controller;

import com.php25.desktop.repostars.respository.entity.TbGist;
import com.php25.desktop.repostars.respository.entity.TbUser;
import com.php25.desktop.repostars.service.UserService;
import com.php25.desktop.repostars.util.GlobalUtil;
import com.php25.desktop.repostars.util.LocalStorage;
import com.php25.desktop.repostars.view.RepoListCell;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author penghuiping
 * @date 2020/10/13 16:09
 */
@Slf4j
@Component
public class GroupListAddController extends BaseController {

    public Button backBtn;
    public Label titleLabel;
    public TextField searchTextField;
    public Button searchBtn;
    public ScrollPane scrollPane;
    public VBox container;

    public Scene previousScene;

    @Autowired
    private UserService userService;

    @Autowired
    private LocalStorage localStorage;


    @Override
    public void start() throws Exception {
        scrollPane.getStyleClass().add("edge-to-edge");
        titleLabel.setText("新增分组项");
        backBtn.setOnMouseClicked(this);
        searchBtn.setOnMouseClicked(this);
        loadItem("");
    }

    @Override
    public void handleMouseEvent(MouseEvent mouseEvent) throws Exception {
        if (mouseEvent.getSource() instanceof Button) {
            Button button = (Button) mouseEvent.getSource();
            switch (button.getId()) {
                case "backBtn": {
                    GlobalUtil.goNextScene(mouseEvent, this.previousScene);
                    break;
                }
                case "searchBtn": {
                    String searchKey = searchTextField.getText();
                    loadItem(searchKey);
                    break;
                }
                default: {
                    break;
                }
            }
        }
    }

    private void loadItem(String searchKey) {
        container.getChildren().clear();
        TbUser tbUser = localStorage.getLoginUser();
        List<TbGist> tbGists = userService.getMyGistUngroup(tbUser.getLogin(), searchKey);
        var list = tbGists.stream()
                .map(tbGist -> new RepoListCell(tbGist.getFullName(), tbGist.getDescription(), tbGist.getForks() + ""))
                .collect(Collectors.toList());
        container.getChildren().addAll(list);
    }
}
