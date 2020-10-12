package com.php25.desktop.repostars.view;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;

/**
 * @author penghuiping
 * @date 2020/10/12 17:06
 */
public abstract class GroupItem0 extends AnchorPane {

    @FXML
    public AnchorPane container;

    protected Boolean isEdit = false;

    public abstract void displayEditStatus(Boolean isEdit);

    public void selfClick() {
        if (!this.isEdit) {
            container.setStyle("-fx-background-color: white");

            container.setOnMouseMoved(mouseEvent -> {
                container.setStyle("-fx-background-color: gray");
            });

            container.setOnMouseExited(mouseEvent -> {
                container.setStyle("-fx-background-color: white");
            });

            container.setOnMousePressed(mouseEvent -> {
                container.setStyle("-fx-background-color: black");
            });

            container.setOnMouseReleased(mouseEvent -> {
                container.setStyle("-fx-background-color: gray");
            });
        } else {
            container.setStyle("-fx-background-color: white");
            container.setOnMouseMoved(mouseEvent -> {
                container.setStyle("-fx-background-color: white");
            });

            container.setOnMouseExited(mouseEvent -> {
                container.setStyle("-fx-background-color: white");
            });

            container.setOnMousePressed(mouseEvent -> {
                container.setStyle("-fx-background-color: white");
            });

            container.setOnMouseReleased(mouseEvent -> {
                container.setStyle("-fx-background-color: white");
            });
        }
    }
}
