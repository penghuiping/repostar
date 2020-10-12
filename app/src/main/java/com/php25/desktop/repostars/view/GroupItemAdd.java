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
 * @date 2020/10/12 16:58
 */
@Slf4j
public class GroupItemAdd extends GroupItem0 {

    @FXML
    private ImageView icon;

    public GroupItemAdd() {
        super();
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(new ClassPathResource("./view/group_item_add.fxml").getURL());
            fxmlLoader.setController(this);
            Parent parent = fxmlLoader.load();
            this.getChildren().add(parent);
            this.icon.setImage(new Image(new ClassPathResource("img/add.png").getInputStream()));
            this.selfClick();
            displayEditStatus(false);
        } catch (Exception e) {
            log.error("出错啦", e);
        }
    }

    @Override
    public void displayEditStatus(Boolean isEdit) {
        this.isEdit = isEdit;
        this.setVisible(this.isEdit);
    }
}
