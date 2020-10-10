package com.php25.desktop.repostars.view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;

/**
 * @author penghuiping
 * @date 2020/10/10 21:38
 */
@Slf4j
public class GroupItem extends AnchorPane {

    @FXML
    public Label titleLabel;

    public GroupItem(String title) {
        super();
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(new ClassPathResource("./view/group_item.fxml").getURL());
            fxmlLoader.setController(this);
            Parent parent = fxmlLoader.load();
            this.getChildren().add(parent);
            titleLabel.setText(title);
        } catch (Exception e) {
            log.error("出错啦", e);
        }
    }

    public String getTitle() {
        return titleLabel.getText();
    }
}
