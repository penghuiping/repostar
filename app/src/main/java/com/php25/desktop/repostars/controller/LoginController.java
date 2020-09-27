package com.php25.desktop.repostars.controller;

import com.php25.common.core.exception.Exceptions;
import com.php25.common.core.util.StringUtil;
import com.php25.desktop.repostars.constant.AppError;
import com.php25.desktop.repostars.github.UserManager;
import com.php25.desktop.repostars.github.dto.User;
import com.php25.desktop.repostars.util.GlobalUtil;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

/**
 * @author penghuiping
 * @date 2020/9/22 14:02
 */
@Slf4j
@Component
public class LoginController extends BaseController {

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
    private UserManager userManager;

    @Override
    public void initialize() {
        try {
            logo.setImage(new Image(new ClassPathResource("img/github_logo.png").getInputStream()));
            resetBtn.setOnMouseClicked(mouseEvent -> {
                usernameTextField.setText("");
                tokenTextField.setText("");
                usernameTextField.requestFocus();
            });
            loginBtn.setOnMouseClicked(mouseEvent -> {
                String token = tokenTextField.getText();
                if (StringUtil.isBlank(token)) {
                    throw Exceptions.throwBusinessException(AppError.LOGIN_ERROR);
                }
                User user = userManager.getUserInfo(token);
                if (user != null) {
                    Stage stage = GlobalUtil.getCurrentStage(mouseEvent);
                    Scene previousScene = stage.getScene();
                    Parent load = GlobalUtil.loadFxml("nav_controller.fxml", this.applicationContext);
                    stage.setScene(new Scene(load, 800, 600));
                    stage.show();
                }
                throw Exceptions.throwBusinessException(AppError.LOGIN_ERROR);
            });
        } catch (Exception e) {
            throw new RuntimeException("出错啦", e);
        }
    }
}
