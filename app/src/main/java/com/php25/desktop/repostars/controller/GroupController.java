package com.php25.desktop.repostars.controller;

import com.php25.desktop.repostars.util.GlobalUtil;
import com.php25.desktop.repostars.view.GroupItem;
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

    @Override
    public void start() throws Exception {
        scrollPane.getStyleClass().add("edge-to-edge");
        backBtn.setOnMouseClicked(this);
        var list = List.of(
                new GroupItem("人工智能"),
                new GroupItem("人工智能1"),
                new GroupItem("人工智能2"),
                new GroupItem("人工智能3"),
                new GroupItem("人工智能4"),
                new GroupItem("人工智能5"),
                new GroupItem("人工智能6"),
                new GroupItem("人工智能7"),
                new GroupItem("人工智能8"),
                new GroupItem("人工智能9"),
                new GroupItem("人工智能10"),
                new GroupItem("人工智能11"),
                new GroupItem("人工智能12"),
                new GroupItem("人工智能13"),
                new GroupItem("人工智能14"),
                new GroupItem("人工智能15"),
                new GroupItem("人工智能16"),
                new GroupItem("人工智能17"),
                new GroupItem("人工智能18")
        );

        list.forEach(groupItem -> groupItem.setOnMouseClicked(mouseEvent -> {
            var item = (GroupItem) mouseEvent.getSource();
            log.info("clicked group item:{}", item.getTitle());
        }));

        container.getChildren().addAll(list);

    }

    @Override
    public void handleMouseEvent(MouseEvent mouseEvent) throws Exception {
        Button button = (Button) mouseEvent.getSource();
        switch (button.getId()) {
            case "backBtn": {
                GlobalUtil.goNextScene("nav_controller.fxml", mouseEvent, this.applicationContext);
                break;
            }
            default: {
                break;
            }
        }
    }
}
