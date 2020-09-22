package com.php25.desktop.repostars.init;

import com.php25.desktop.repostars.util.GlobalUtil;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author penghuiping
 * @date 2020/9/22 13:55
 */
@Component
public class StageInitializer implements ApplicationListener<JavaFxApp.StageReadyEvent> {

    @Autowired
    private ConfigurableApplicationContext applicationContext;

    @Override
    public void onApplicationEvent(JavaFxApp.StageReadyEvent stageReadyEvent) {
        Stage stage = stageReadyEvent.getStage();
        stage.setScene(new Scene(GlobalUtil.loadFxml("login_controller.fxml", applicationContext), 800, 600));
        stage.show();
    }
}
