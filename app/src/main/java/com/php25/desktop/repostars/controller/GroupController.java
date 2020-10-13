package com.php25.desktop.repostars.controller;

import com.php25.desktop.repostars.respository.entity.TbGroup;
import com.php25.desktop.repostars.service.UserService;
import com.php25.desktop.repostars.util.GlobalUtil;
import com.php25.desktop.repostars.util.LocalStorage;
import com.php25.desktop.repostars.view.GroupItem;
import com.php25.desktop.repostars.view.GroupItem0;
import com.php25.desktop.repostars.view.GroupItemAdd;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author penghuiping
 * @date 2020/10/10 21:20
 */
@Slf4j
@Component
public class GroupController extends BaseController {

    public Button backBtn;
    public FlowPane container;
    public ScrollPane scrollPane;
    public Button editBtn;

    private List<GroupItem0> groupItems;

    private Boolean isEdit = false;

    @Autowired
    private UserService userService;

    @Autowired
    private LocalStorage localStorage;

    @Override
    public void start() throws Exception {
        scrollPane.getStyleClass().add("edge-to-edge");
        backBtn.setOnMouseClicked(this);
        editBtn.setOnMouseClicked(this);
        this.loadGroupItem();
    }

    @Override
    public void handleMouseEvent(MouseEvent mouseEvent) throws Exception {
        if (mouseEvent.getSource() instanceof Button) {
            Button button = (Button) mouseEvent.getSource();
            switch (button.getId()) {
                case "backBtn": {
                    GlobalUtil.goNextScene("controller/nav_controller.fxml", mouseEvent, this.applicationContext);
                    break;
                }
                case "editBtn": {
                    toggleEditStatus();
                    break;
                }
                default: {
                    break;
                }
            }
        }
    }

    private void loadGroupItem() {
        this.groupItems = new ArrayList<>();
        var groupItemAdd = new GroupItemAdd();
        var user = localStorage.getLoginUser();
        List<TbGroup> groups = userService.getGroups(user.getLogin());
        if (null != groups && !groups.isEmpty()) {
            this.groupItems = groups.stream().map(tbGroup -> new GroupItem(tbGroup.getName(), tbGroup.getId())).collect(Collectors.toList());
        }
        this.groupItems.add(groupItemAdd);
        container.getChildren().clear();
        container.getChildren().addAll(groupItems);
        this.groupItems.forEach(groupItem -> {
            if (groupItem instanceof GroupItem) {
                var item = (GroupItem) groupItem;
                item.deleteBtn.setOnMouseClicked(mouseEvent -> {
                    //编辑状态删除
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("注意");
                    alert.setHeaderText("确定要删除么");
                    Optional<ButtonType> buttonType = alert.showAndWait();
                    if (buttonType.isPresent() && buttonType.get() == ButtonType.OK) {
                        userService.deleteGroup(user.getLogin(), item.getGroupId());
                        this.loadGroupItem();
                        setEditStatus(true);
                    }
                });
            }

            groupItem.setOnMouseClicked(mouseEvent -> {
                if (mouseEvent.getSource() instanceof GroupItem) {
                    //非编辑状态点击
                    var item = (GroupItem) mouseEvent.getSource();
                    if (!item.isEdit()) {

                        localStorage.put("group_list_controller_title", item.getTitle());
                        localStorage.put("group_list_controller_group_id", item.getGroupId() + "");
                        GlobalUtil.goNextScene("controller/group_list_controller.fxml", mouseEvent, this.applicationContext);
                    }
                } else {
                    //编辑状态新增
                    var item = (GroupItemAdd) mouseEvent.getSource();
                    TextInputDialog dialog = new TextInputDialog();
                    dialog.setTitle("注意");
                    dialog.setHeaderText("请输入组名");
                    dialog.setContentText("组名:");
                    Optional<String> result = dialog.showAndWait();
                    result.ifPresent(groupName -> {
                        userService.addGroup(user.getLogin(), groupName);
                        this.loadGroupItem();
                        setEditStatus(true);
                    });
                }
            });
        });

    }

    public void toggleEditStatus() {
        isEdit = !isEdit;
        setEditStatus(isEdit);
    }

    private void setEditStatus(Boolean isEdit) {
        this.groupItems.forEach(groupItem -> {
            groupItem.displayEditStatus(isEdit);
        });
        container.getChildren().clear();
        container.getChildren().addAll(this.groupItems);
        this.editBtn.setText(isEdit ? "取消" : "编辑");
    }
}
