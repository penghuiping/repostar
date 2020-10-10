package com.php25.desktop.repostars.controller;

import com.php25.common.core.dto.DataGridPageDto;
import com.php25.desktop.repostars.respository.entity.TbGist;
import com.php25.desktop.repostars.respository.entity.TbUser;
import com.php25.desktop.repostars.service.UserService;
import com.php25.desktop.repostars.util.GlobalUtil;
import com.php25.desktop.repostars.util.LocalStorage;
import com.php25.desktop.repostars.view.RepoListCell;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
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

    @FXML
    public TextField searchTextField;

    @FXML
    public Button searchBtn;

    @Autowired
    private UserService userService;

    @Autowired
    private LocalStorage localStorage;

    private DataGridPageDto<TbGist> dataGridPageDto;


    @Override
    public void start() throws Exception {
        scrollPane.getStyleClass().add("edge-to-edge");
        this.loadData();
        backBtn.setOnMouseClicked(this);
        searchBtn.setOnMouseClicked(this);
    }

    @Override
    public void handleMouseEvent(MouseEvent mouseEvent) throws Exception {
        Button button = (Button) mouseEvent.getSource();
        switch (button.getId()) {
            case "backBtn": {
                GlobalUtil.goNextScene("nav_controller.fxml", mouseEvent, this.applicationContext);
                break;
            }
            case "searchBtn": {
                loadData();
                break;
            }
            default: {
                break;
            }
        }
    }


    private void loadData() {
        container.getChildren().clear();
        String searchText = searchTextField.getText();
        AtomicReference<Integer> pageNum = new AtomicReference<>(1);
        Integer pageSize = 50;
        TbUser tbUser = localStorage.getLoginUser();
        //初始加载
        this.dataGridPageDto = userService.searchPage(tbUser.getLogin(), tbUser.getToken(), searchText, PageRequest.of(pageNum.get(), pageSize));
        List<TbGist> gistList = dataGridPageDto.getData();
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
            if (scrollEvent.getDeltaY() < 0 && pageNum.get() < dataGridPageDto.getRecordsTotal() / pageSize) {
                log.info("下划");
                pageNum.set(pageNum.get() + 1);
                this.dataGridPageDto = userService.searchPage(tbUser.getLogin(), tbUser.getToken(), searchText, PageRequest.of(pageNum.get(), pageSize));
                List<TbGist> gistList1 = dataGridPageDto.getData();
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
    }
}
