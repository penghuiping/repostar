package com.php25.desktop.repostars.controller;

import com.php25.desktop.repostars.util.GlobalUtil;
import com.php25.desktop.repostars.view.GroupItem;
import com.php25.desktop.repostars.view.GroupItem0;
import com.php25.desktop.repostars.view.GroupItemAdd;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author penghuiping
 * @date 2020/10/10 21:20
 */
@Slf4j
@Component
public class GroupController extends BaseController {

    @FXML
    public Button backBtn;

    @FXML
    public FlowPane container;

    @FXML
    public ScrollPane scrollPane;

    @FXML
    public Button editBtn;

    private List<GroupItem0> groupItems;

    private Boolean isEdit = false;

    @Override
    public void start() throws Exception {
        scrollPane.getStyleClass().add("edge-to-edge");
        backBtn.setOnMouseClicked(this);
        editBtn.setOnMouseClicked(this);
        this.groupItems = List.of(
                new GroupItem("人工智能"),
                new GroupItem("人工智能1"),
                new GroupItem("人工智能2"),
                new GroupItemAdd()
        );

        this.groupItems.forEach(groupItem -> groupItem.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getSource() instanceof GroupItem) {
                var item = (GroupItem) mouseEvent.getSource();
                log.info("clicked group item:{}", item.getTitle());
            } else {
                var item = (GroupItemAdd) mouseEvent.getSource();
                log.info("clicked goup item add");
            }

        }));

        container.getChildren().addAll(groupItems);

    }

    @Override
    public void handleMouseEvent(MouseEvent mouseEvent) throws Exception {
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

    public void toggleEditStatus() {
        isEdit = !isEdit;
        this.groupItems.forEach(groupItem -> {
            groupItem.displayEditStatus(isEdit);
        });
        container.getChildren().clear();
        container.getChildren().addAll(this.groupItems);
        this.editBtn.setText(isEdit ? "取消" : "编辑");
    }
}
