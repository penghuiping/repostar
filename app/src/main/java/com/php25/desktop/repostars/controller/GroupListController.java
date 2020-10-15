package com.php25.desktop.repostars.controller;

import com.php25.desktop.repostars.respository.entity.TbUser;
import com.php25.desktop.repostars.service.UserService;
import com.php25.desktop.repostars.util.GlobalUtil;
import com.php25.desktop.repostars.util.LocalStorage;
import com.php25.desktop.repostars.view.AbstractRepoListCell;
import com.php25.desktop.repostars.view.RepoListCell;
import com.php25.desktop.repostars.view.RepoListCellAdd;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
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
import java.util.Optional;
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

    public String groupName;

    public Long groupId;

    @Override
    public void start() throws Exception {
        isEdit = false;
        scrollPane.getStyleClass().add("edge-to-edge");
        backBtn.setOnMouseClicked(this);
        editBtn.setOnMouseClicked(this);
        titleLabel.setText(this.groupName);
        loadEditStatus();
    }

    @Override
    public void handleMouseEvent(MouseEvent mouseEvent) throws Exception {
        if (mouseEvent.getSource() instanceof Button) {
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
        } else if (mouseEvent.getSource() instanceof RepoListCellAdd) {
            GroupListAddController controller = this.applicationContext.getBean(GroupListAddController.class);
            controller.groupId = this.groupId;
            Scene previous = GlobalUtil.goNextScene("controller/group_list_add_controller.fxml", mouseEvent, this.applicationContext);
            controller.previousScene = previous;
        } else if (mouseEvent.getSource() instanceof RepoListCell) {
            var cell = (RepoListCell) mouseEvent.getSource();
            if (cell.isEdit) {
                //编辑状态删除操作
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("注意");
                alert.setHeaderText("确定把此项从此分组移除么");
                Optional<ButtonType> buttonType = alert.showAndWait();
                if (buttonType.isPresent() && buttonType.get() == ButtonType.OK) {
                    var user = localStorage.getLoginUser();
                    userService.deleteOneGistFromGroup(cell.id, this.groupId);
                    this.loadEditStatus();
                }
            } else {
                //非编辑状态点击进入详情界面
                var controller = this.applicationContext.getBean(RepoDetailController.class);
                controller.id = cell.id;
                controller.title = cell.titleLabel.getText();
                Scene scene = GlobalUtil.goNextScene("controller/repo_detail_controller.fxml", mouseEvent, this.applicationContext);
                controller.previousScene = scene;
            }
        }

    }

    public void loadEditStatus() {
        container.getChildren().clear();
        this.listCells = new ArrayList<>();
        TbUser tbUser = localStorage.getLoginUser();
        var tbGistDataGridPageDto = userService.searchPageByGroupId(tbUser.getLogin(),
                tbUser.getToken(), this.groupId, PageRequest.of(1, 20));
        var repoListCells = tbGistDataGridPageDto.getData().stream().map(tbGist -> {
            RepoListCell repoListCell = new RepoListCell(
                    tbGist.getId(),
                    tbGist.getFullName(),
                    tbGist.getDescription(),
                    tbGist.getWatchers().toString(),
                    tbGist.getForks().toString());
            return repoListCell;
        }).collect(Collectors.toList());
        this.listCells.addAll(repoListCells);
        RepoListCellAdd repoListCellAdd = new RepoListCellAdd();
        this.listCells.add(repoListCellAdd);
        if (this.isEdit) {
            editBtn.setText("取消");
            this.listCells.forEach(abstractRepoListCell -> {
                abstractRepoListCell.loadEditStatus(this.isEdit);
                abstractRepoListCell.setOnMouseClicked(GroupListController.this);
            });
        } else {
            editBtn.setText("编辑");
            this.listCells.forEach(abstractRepoListCell -> {
                abstractRepoListCell.loadEditStatus(this.isEdit);
                abstractRepoListCell.setOnMouseClicked(GroupListController.this);
            });
        }
        container.getChildren().addAll(this.listCells);
    }
}
