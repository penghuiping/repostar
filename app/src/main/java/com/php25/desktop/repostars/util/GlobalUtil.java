package com.php25.desktop.repostars.util;

import com.php25.common.core.exception.Exceptions;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.ClassPathResource;

/**
 * @author penghuiping
 * @date 2020/9/22 15:01
 */
public abstract class GlobalUtil {

    public static Parent loadFxml(String path, ApplicationContext applicationContext) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(new ClassPathResource(path).getURL());
            fxmlLoader.setControllerFactory(applicationContext::getBean);
            return fxmlLoader.load();
        } catch (Exception e) {
            throw Exceptions.throwIllegalStateException("加载FXML文件出错", e);
        }
    }

    public static int getWindowWidth() {
        return 800;
    }

    public static int getWindowHeight() {
        return 600;
    }

    public static Scene goNextScene(String fxml, Event event, ApplicationContext applicationContext) {
        Stage stage = GlobalUtil.getCurrentStage(event);
        Scene previousScene = stage.getScene();
        Parent load = GlobalUtil.loadFxml(fxml, applicationContext);
        stage.setScene(new Scene(load, getWindowWidth(), getWindowHeight()));
        stage.show();
        return previousScene;
    }

    public static Stage getCurrentStage(Event event) {
        return (Stage) ((Node) event.getSource()).getScene().getWindow();
    }

    public static void showErrorMsg(String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}
