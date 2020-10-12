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
 * @date 2020/10/10 21:38
 */
@Slf4j
public class GroupItem extends GroupItem0 {

    @FXML
    public Label titleLabel;

    @FXML
    public ImageView deleteBtn;


    public GroupItem(String title) {
        super();
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(new ClassPathResource("./view/group_item.fxml").getURL());
            fxmlLoader.setController(this);
            Parent parent = fxmlLoader.load();
            this.getChildren().add(parent);
            titleLabel.setText(title);
            initDeleteBtn();
            selfClick();
            displayEditStatus(isEdit);
        } catch (Exception e) {
            log.error("出错啦", e);
        }
    }

    /**
     * 展示GroupItem是否处于编辑状态，默认处于非编辑状态
     *
     * @param isEdit true:表示处于编辑状态 false:非编辑状态
     */
    @Override
    public void displayEditStatus(Boolean isEdit) {
        this.isEdit = isEdit;
        if (this.isEdit) {
            this.selfClick();
            deleteBtn.setVisible(true);
        } else {
            selfClick();
            deleteBtn.setVisible(false);
        }
    }

    public void initDeleteBtn() {
        try {
            deleteBtn.setImage(new Image(new ClassPathResource("img/error.png").getInputStream()));
        } catch (Exception e) {
            log.error("出错啦", e);
        }
        deleteBtn.setOnMouseEntered(mouseDragEvent -> {
            try {
                deleteBtn.setImage(new Image(new ClassPathResource("img/error_red.png").getInputStream()));
            } catch (Exception e) {
                log.error("出错啦", e);
            }
        });
        deleteBtn.setOnMouseExited(mouseEvent -> {
            try {
                deleteBtn.setImage(new Image(new ClassPathResource("img/error.png").getInputStream()));
            } catch (Exception e) {
                log.error("出错啦", e);
            }
        });
        deleteBtn.setOnMouseMoved(mouseEvent -> {
            try {
                deleteBtn.setImage(new Image(new ClassPathResource("img/error_red.png").getInputStream()));
            } catch (Exception e) {
                log.error("出错啦", e);
            }
        });
        deleteBtn.setOnMousePressed(mouseEvent -> {
            try {
                deleteBtn.setImage(new Image(new ClassPathResource("img/error_red_pressed.png").getInputStream()));
            } catch (Exception e) {
                log.error("出错啦", e);
            }
        });

        deleteBtn.setOnMouseReleased(mouseEvent -> {
            try {
                deleteBtn.setImage(new Image(new ClassPathResource("img/error_red.png").getInputStream()));
            } catch (Exception e) {
                log.error("出错啦", e);
            }
        });
    }




    public String getTitle() {
        return titleLabel.getText();
    }
}
