package com.php25.desktop.repostars.controller;

import com.php25.desktop.repostars.util.GlobalUtil;
import com.php25.desktop.repostars.util.LocalStorage;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author penghuiping
 * @date 2020/9/22 14:36
 */
@Slf4j
@Component
public class NavController extends BaseController {

    @FXML
    public Button myRepoBtn;
    @FXML
    public Button allStarRepoBtn;
    @FXML
    public Button selfDefinedGroupBtn;
    @FXML
    private Button logoutBtn;

    @Autowired
    private LocalStorage localStorage;

    @Override
    public void start() throws Exception {
        logoutBtn.setOnMouseClicked(this);
        myRepoBtn.setOnMouseClicked(this);
        allStarRepoBtn.setOnMouseClicked(this);
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
            case "myRepoBtn": {
                GlobalUtil.goNextScene("my_repo_controller.fxml", mouseEvent, this.applicationContext);
                break;
            }
            case "allStarRepoBtn": {
                GlobalUtil.goNextScene("all_star_repo_controller.fxml", mouseEvent, this.applicationContext);
                break;
            }
            default: {
                break;
            }
        }
    }
}
