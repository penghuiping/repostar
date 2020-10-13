package com.php25.desktop.repostars.controller;

import com.php25.common.core.util.StringUtil;
import com.php25.desktop.repostars.respository.entity.TbUser;
import com.php25.desktop.repostars.service.UserService;
import com.php25.desktop.repostars.util.GlobalUtil;
import com.php25.desktop.repostars.util.LocalStorage;
import com.php25.desktop.repostars.view.AbstractRepoListCell;
import com.php25.desktop.repostars.view.RepoListCell;
import com.php25.desktop.repostars.view.RepoListCellAdd;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author penghuiping
 * @date 2020/10/12 21:47
 */
@Slf4j
@Component
public class GroupListController extends BaseController {

    public Button backBtn;
    public Button editBtn;
    public Label titleLabel;
    public VBox container;
    public ScrollPane scrollPane;

    @Autowired
    private LocalStorage localStorage;

    @Autowired
    private UserService userService;

    private boolean isEdit = false;

    private List<AbstractRepoListCell> listCells;

    private String groupName;

    private Long groupId;

    @Override
    public void start() throws Exception {
        scrollPane.getStyleClass().add("edge-to-edge");
        backBtn.setOnMouseClicked(this);
        editBtn.setOnMouseClicked(this);
        this.groupName = localStorage.get("group_list_controller_title");
        this.groupId = StringUtil.isBlank(localStorage.get("group_list_controller_group_id")) ? null : Long.parseLong(localStorage.get("group_list_controller_group_id"));
        titleLabel.setText(this.groupName);
        loadEditStatus();
    }

    @Override
    public void handleMouseEvent(MouseEvent mouseEvent) throws Exception {
        Button button = (Button) mouseEvent.getSource();
        switch (button.getId()) {
            case "backBtn": {
                GlobalUtil.goNextScene("controller/group_controller.fxml", mouseEvent, this.applicationContext);
                break;
            }
            case "editBtn": {
                this.isEdit = !isEdit;
                loadEditStatus();
                break;
            }
            default: {
                break;
            }
        }
    }

    private void loadEditStatus() {
        container.getChildren().clear();
        this.listCells = new ArrayList<>();
        TbUser tbUser = localStorage.getLoginUser();
        var tbGistDataGridPageDto = userService.searchPageByGroupId(tbUser.getLogin(), tbUser.getToken(), this.groupId, PageRequest.of(1, 20));
        var repoListCells = tbGistDataGridPageDto.getData().stream().map(tbGist -> {
            RepoListCell repoListCell = new RepoListCell(tbGist.getFullName(), tbGist.getDescription(), tbGist.getForks() + "");
            return repoListCell;
        }).collect(Collectors.toList());
        this.listCells.addAll(repoListCells);
        RepoListCellAdd repoListCellAdd = new RepoListCellAdd();
        this.listCells.add(repoListCellAdd);
        if (this.isEdit) {
            editBtn.setText("取消");
            repoListCellAdd.setVisible(true);
        } else {
            editBtn.setText("编辑");
            repoListCellAdd.setVisible(false);
        }
        container.getChildren().addAll(this.listCells);
    }
}
