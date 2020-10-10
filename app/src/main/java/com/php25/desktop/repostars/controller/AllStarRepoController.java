package com.php25.desktop.repostars.controller;

import com.php25.desktop.repostars.respository.entity.TbGist;
import com.php25.desktop.repostars.respository.entity.TbUser;
import com.php25.desktop.repostars.service.UserService;
import com.php25.desktop.repostars.util.GlobalUtil;
import com.php25.desktop.repostars.util.LocalStorage;
import com.php25.desktop.repostars.view.RepoListCell;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author penghuiping
 * @date 2020/10/9 16:29
 */
@Slf4j
@Component
public class AllStarRepoController extends BaseController {

    @FXML
    public ScrollPane scrollPane;

    @FXML
    public VBox container;

    @FXML
    public Button backBtn;

    @Autowired
    private UserService userService;

    @Autowired
    private LocalStorage localStorage;


    @Override
    public void start() throws Exception {
        scrollPane.getStyleClass().add("edge-to-edge");
        AtomicReference<Integer> pageNum = new AtomicReference<>(1);
        Integer pageSize = 50;


        TbUser tbUser = localStorage.getLoginUser();
        Integer totalPageSize = userService.getMyGistTotalPage(tbUser.getLogin(), tbUser.getToken(), pageSize);

        //初始加载
        List<TbGist> gistList = userService.getMyGist(tbUser.getLogin(), tbUser.getToken(), pageNum.get(), pageSize);
        if (null != gistList && !gistList.isEmpty()) {
            List<RepoListCell> repoListCells = new ArrayList<>();
            for (TbGist tbGist : gistList) {
                RepoListCell repoListCell = new RepoListCell(tbGist.getFullName(), tbGist.getDescription(), tbGist.getForks() + "");
                repoListCells.add(repoListCell);
            }
            container.getChildren().addAll(repoListCells);
        }

        //下滑加载
        scrollPane.setOnScroll(scrollEvent -> {
            if (scrollEvent.getDeltaY() < 0 && pageNum.get() < totalPageSize) {
                log.info("下划");
                pageNum.set(pageNum.get() + 1);
                List<TbGist> gistList1 = userService.getMyGist(tbUser.getLogin(), tbUser.getToken(), pageNum.get(), pageSize);
                if (null != gistList1 && !gistList1.isEmpty()) {
                    List<RepoListCell> repoListCells = new ArrayList<>();
                    for (TbGist tbGist : gistList1) {
                        RepoListCell repoListCell = new RepoListCell(tbGist.getFullName(), tbGist.getDescription(), tbGist.getForks() + "");
                        repoListCells.add(repoListCell);
                    }
                    container.getChildren().addAll(repoListCells);
                }
            }
        });
        backBtn.setOnMouseClicked(this);
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
