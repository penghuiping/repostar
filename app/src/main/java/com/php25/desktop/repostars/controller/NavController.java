package com.php25.desktop.repostars.controller;

import com.php25.desktop.repostars.respository.entity.TbUser;
import com.php25.desktop.repostars.service.UserService;
import com.php25.desktop.repostars.util.GlobalUtil;
import com.php25.desktop.repostars.util.LocalStorage;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author penghuiping
 * @date 2020/9/22 14:36
 */

@Component
public class NavController extends BaseController {

    @FXML
    private Button logoutBtn;

    @Autowired
    private UserService userService;

    @Autowired
    private LocalStorage localStorage;

    @Override
    public void start() throws Exception {
        logoutBtn.setOnMouseClicked(this);
        TbUser tbUser = localStorage.getLoginUser();
        //进行后台全量同步gists
        userService.syncStarRepo(tbUser.getLogin(), tbUser.getToken());
    }

    @Override
    public void handleMouseEvent(MouseEvent mouseEvent) throws Exception {
        Button button = (Button) mouseEvent.getSource();
        switch (button.getId()) {
            case "logoutBtn": {
                GlobalUtil.goNextScene("login_controller.fxml", mouseEvent, this.applicationContext);
                localStorage.clearAll();
                break;
            }
            default: {
                break;
            }
        }
    }
}
