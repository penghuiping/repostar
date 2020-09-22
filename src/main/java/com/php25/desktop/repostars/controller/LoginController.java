package com.php25.desktop.repostars.controller;

import com.php25.desktop.repostars.util.GlobalUtil;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

/**
 * @author penghuiping
 * @date 2020/9/22 14:02
 */
@Slf4j
@Component
public class LoginController {

    @Autowired
    private ConfigurableApplicationContext applicationContext;

    @FXML
    private Button btn;

    @FXML
    public void initialize() {
        try {
            btn.setText("你好");
            btn.setOnMouseClicked(mouseEvent -> {
                log.info("clicked");
                Stage stage = GlobalUtil.getCurrentStage(mouseEvent);
                Parent load = GlobalUtil.loadFxml("nav_controller.fxml", applicationContext);
                stage.setScene(new Scene(load, 800, 600));
                stage.show();
            });
        } catch (Exception e) {
            throw new RuntimeException("出错啦", e);
        }
    }
}
