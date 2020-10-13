package com.php25.desktop.repostars.view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;

/**
 * @author penghuiping
 * @date 2020/10/13 10:06
 */
@Slf4j
public class RepoListCellAdd extends AbstractRepoListCell {

    @FXML
    public ImageView icon;

    public RepoListCellAdd() {
        super();
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(new ClassPathResource("./view/repo_list_cell_add.fxml").getURL());
            fxmlLoader.setController(this);
            Parent parent = fxmlLoader.load();
            this.getChildren().add(parent);
            this.icon.setImage(new Image(new ClassPathResource("img/add.png").getInputStream()));
            this.loadEditStatus(false);
        } catch (Exception e) {
            log.error("出错啦", e);
        }
    }

    @Override
    public void loadEditStatus(Boolean isEdit) {
        this.isEdit = isEdit;
        this.setVisible(isEdit);
    }
}
