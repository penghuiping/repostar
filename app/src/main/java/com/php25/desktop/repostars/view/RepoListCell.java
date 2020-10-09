package com.php25.desktop.repostars.view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;

/**
 * @author penghuiping
 * @date 2020/10/9 11:06
 */
@Slf4j
public class RepoListCell extends Pane {

    @FXML
    public Label titleLabel;

    @FXML
    public Label descLabel;

    @FXML
    public Label starLabel;


    public RepoListCell(String title, String description, String star) {
        super();
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(new ClassPathResource("./view/repo_list_cell.fxml").getURL());
            fxmlLoader.setController(this);
            Parent parent = fxmlLoader.load();
            this.getChildren().add(parent);
            titleLabel.setText(title);
            descLabel.setText(description);
            starLabel.setText(star);
        } catch (Exception e) {
            log.error("出错啦", e);
        }

    }
}
