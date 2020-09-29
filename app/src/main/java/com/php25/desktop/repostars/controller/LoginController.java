package com.php25.desktop.repostars.controller;

import com.php25.common.core.exception.Exceptions;
import com.php25.common.core.util.StringUtil;
import com.php25.desktop.repostars.constant.AppError;
import com.php25.desktop.repostars.util.GlobalUtil;
import com.php25.github.UserManager;
import com.php25.github.dto.User;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
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
            usernameTextField.setAlignment(Pos.BASELINE_LEFT);
            tokenTextField.setAlignment(Pos.BASELINE_LEFT);
            resetBtn.setOnMouseClicked(this::handle);
            loginBtn.setOnMouseClicked(this::handle);
        } catch (Exception e) {
            throw Exceptions.throwIllegalStateException("出错啦", e);
        }
    }


    public void handle(MouseEvent mouseEvent) {
        if (mouseEvent.getSource() instanceof Button) {
            Button button = (Button) mouseEvent.getSource();
            switch (button.getId()) {
                case "loginBtn": {
                    String token = tokenTextField.getText();
                    if (StringUtil.isBlank(token)) {
                        throw Exceptions.throwBusinessException(AppError.LOGIN_ERROR);
                    }
                    User user = userManager.getUserInfo(token);
                    if (user != null && StringUtil.isNotBlank(user.getLogin())) {
                        GlobalUtil.goNextScene("nav_controller.fxml", mouseEvent, this.applicationContext);
                    }
                    throw Exceptions.throwBusinessException(AppError.LOGIN_ERROR);
                }
                case "resetBtn": {
                    usernameTextField.setText("");
                    tokenTextField.setText("");
                    usernameTextField.requestFocus();
                    break;
                }
                default: {
                    break;
                }
            }
        }
    }


}
