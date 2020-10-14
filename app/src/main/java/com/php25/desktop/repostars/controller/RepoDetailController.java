package com.php25.desktop.repostars.controller;

import com.php25.desktop.repostars.util.GlobalUtil;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

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

    @Override
    public void start() throws Exception {
        backBtn.setOnMouseClicked(this);
        titleLabel.setText(title);
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
