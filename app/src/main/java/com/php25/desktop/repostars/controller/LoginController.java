package com.php25.desktop.repostars.controller;

import com.php25.desktop.repostars.respository.entity.TbUser;
import com.php25.desktop.repostars.service.UserService;
import com.php25.desktop.repostars.util.GlobalUtil;
import com.php25.desktop.repostars.util.LocalStorage;
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
    private UserService userService;

    @Autowired
    private LocalStorage localStorage;

    @Override
    public void start() throws Exception {
        logo.setImage(new Image(new ClassPathResource("img/github_logo.png").getInputStream()));
        usernameTextField.setAlignment(Pos.BASELINE_LEFT);
        tokenTextField.setAlignment(Pos.BASELINE_LEFT);
        usernameTextField.requestFocus();
        resetBtn.setOnMouseClicked(this);
        loginBtn.setOnMouseClicked(this);

    }

    @Override
    public void handleMouseEvent(MouseEvent mouseEvent) throws Exception {
        if (mouseEvent.getSource() instanceof Button) {
            Button button = (Button) mouseEvent.getSource();
            switch (button.getId()) {
                case "loginBtn": {
                    String token = tokenTextField.getText();
                    String username = usernameTextField.getText();
                    TbUser tbUser = userService.login(username, token);
                    localStorage.save(tbUser);
                    GlobalUtil.goNextScene("controller/nav_controller.fxml", mouseEvent, this.applicationContext);
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
