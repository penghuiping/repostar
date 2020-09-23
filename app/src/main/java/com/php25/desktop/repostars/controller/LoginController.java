package com.php25.desktop.repostars.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

/**
 * @author penghuiping
 * @date 2020/9/22 14:02
 */
@Slf4j
@Component
public class LoginController {

    @FXML
    public Button loginBtn;
    @FXML
    public Button resetBtn;
    @FXML
    public TextField usernameTextField;
    @FXML
    public TextField tokenTextField;
    @FXML
    public ImageView logo;

    @Autowired
    private ConfigurableApplicationContext applicationContext;


    @FXML
    public void initialize() {
        try {
            logo.setImage(new Image(new ClassPathResource("img/github_logo.png").getInputStream()));
//            btn.setText("你好");
//            btn.setOnMouseClicked(mouseEvent -> {
//                log.info("clicked");
//                Stage stage = GlobalUtil.getCurrentStage(mouseEvent);
//                Scene previousScene = stage.getScene();
//                log.info("previousScene:{}", previousScene);
//                Parent load = GlobalUtil.loadFxml("nav_controller.fxml", applicationContext);
//                stage.setScene(new Scene(load, 800, 600));
//                stage.show();
//                Scene latestScene = stage.getScene();
//                log.info("latestScene:{}", latestScene);
//            });
        } catch (Exception e) {
            throw new RuntimeException("出错啦", e);
        }
    }
}
