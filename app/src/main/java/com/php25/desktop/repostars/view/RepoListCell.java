package com.php25.desktop.repostars.view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;


/**
 * @author penghuiping
 * @date 2020/10/9 11:06
 */
@Slf4j
public class RepoListCell extends AbstractRepoListCell {

    @FXML
    public Label titleLabel;

    @FXML
    public Label descLabel;

    @FXML
    public Label starLabel;

    @FXML
    public Label forkLabel;

    @FXML
    public ImageView deleteAndArrowBtn;

    public Long id;


    public RepoListCell(Long id, String title, String description, String star, String fork) {
        super();
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(new ClassPathResource("./view/repo_list_cell.fxml").getURL());
            fxmlLoader.setController(this);
            Parent parent = fxmlLoader.load();
            this.getChildren().add(parent);
            titleLabel.setText(title);
            descLabel.setText(description);
            starLabel.setText(star);
            forkLabel.setText(fork);
            this.id = id;
            loadEditStatus(false);
        } catch (Exception e) {
            log.error("出错啦", e);
        }
    }

    @Override
    public void loadEditStatus(Boolean isEdit) {
        this.isEdit = isEdit;
        if (!this.isEdit) {
            //正常状态
            try {
                deleteAndArrowBtn.setImage(new Image(new ClassPathResource("img/right_arrow.png").getInputStream()));
            } catch (Exception e) {
                log.error("出错啦", e);
            }
        } else {
            //编辑状态
            try {
                deleteAndArrowBtn.setImage(new Image(new ClassPathResource("img/error_red.png").getInputStream()));
            } catch (Exception e) {
                log.error("出错啦", e);
            }
        }
    }
}
