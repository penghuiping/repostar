package com.php25.desktop.repostars.controller;

import com.php25.common.core.dto.DataGridPageDto;
import com.php25.desktop.repostars.service.UserService;
import com.php25.desktop.repostars.service.dto.GistDto;
import com.php25.desktop.repostars.service.dto.UserDto;
import com.php25.desktop.repostars.util.GlobalUtil;
import com.php25.desktop.repostars.util.LocalStorage;
import com.php25.desktop.repostars.view.RepoListCell;
import javafx.scene.Scene;
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

    public ScrollPane scrollPane;
    public VBox container;
    public Button backBtn;
    public TextField searchTextField;
    public Button searchBtn;

    @Autowired
    private UserService userService;

    @Autowired
    private LocalStorage localStorage;

    private DataGridPageDto<GistDto> dataGridPageDto;


    @Override
    public void start() throws Exception {
        scrollPane.getStyleClass().add("edge-to-edge");
        backBtn.setOnMouseClicked(this);
        searchBtn.setOnMouseClicked(this);
        this.loadData();
    }

    @Override
    public void handleMouseEvent(MouseEvent mouseEvent) throws Exception {
        if (mouseEvent.getSource() instanceof Button) {
            Button button = (Button) mouseEvent.getSource();
            switch (button.getId()) {
                case "backBtn": {
                    GlobalUtil.goNextScene("controller/nav_controller.fxml", mouseEvent, this.applicationContext);
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
        } else if (mouseEvent.getSource() instanceof RepoListCell) {
            var cell = (RepoListCell) mouseEvent.getSource();
            var controller = this.applicationContext.getBean(RepoDetailController.class);
            controller.id = cell.id;
            controller.title = cell.titleLabel.getText();
            Scene scene = GlobalUtil.goNextScene("controller/repo_detail_controller.fxml", mouseEvent, this.applicationContext);
            controller.previousScene = scene;
        }
    }

    private void loadData() {
        container.getChildren().clear();
        String searchText = searchTextField.getText();
        AtomicReference<Integer> pageNum = new AtomicReference<>(1);
        Integer pageSize = 10;
        UserDto tbUser = localStorage.getLoginUser();
        //初始加载
        this.dataGridPageDto = userService.searchPage(tbUser.getLogin(), tbUser.getToken(), searchText,
                PageRequest.of(pageNum.get(), pageSize));
        List<GistDto> gistList = dataGridPageDto.getData();
        if (null != gistList && !gistList.isEmpty()) {
            List<RepoListCell> repoListCells = new ArrayList<>();
            for (GistDto tbGist : gistList) {
                RepoListCell repoListCell = new RepoListCell(tbGist.getId(), tbGist.getFullName(),
                        tbGist.getDescription(),
                        tbGist.getWatchers().toString(),
                        tbGist.getForks().toString());
                repoListCell.setOnMouseClicked(this);
                repoListCells.add(repoListCell);
            }
            container.getChildren().addAll(repoListCells);
        }

        //下滑加载
        scrollPane.setOnScroll(scrollEvent -> {
            if (scrollEvent.getDeltaY() < 0 && pageNum.get() < dataGridPageDto.getRecordsTotal() / pageSize) {
                pageNum.set(pageNum.get() + 1);
                this.dataGridPageDto = userService.searchPage(tbUser.getLogin(), tbUser.getToken(), searchText,
                        PageRequest.of(pageNum.get(), pageSize));
                List<GistDto> gistList1 = dataGridPageDto.getData();
                if (null != gistList1 && !gistList1.isEmpty()) {
                    List<RepoListCell> repoListCells = new ArrayList<>();
                    for (GistDto tbGist : gistList1) {
                        RepoListCell repoListCell = new RepoListCell(tbGist.getId(), tbGist.getFullName(),
                                tbGist.getDescription(),
                                tbGist.getWatchers().toString(),
                                tbGist.getForks().toString());
                        repoListCell.setOnMouseClicked(this);
                        repoListCells.add(repoListCell);
                    }
                    container.getChildren().addAll(repoListCells);
                }
            }
        });
    }
}
