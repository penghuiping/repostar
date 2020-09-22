package com.php25.desktop.repostars.util;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.ClassPathResource;

/**
 * @author penghuiping
 * @date 2020/9/22 15:01
 */
public class GlobalUtil {

    public static Parent loadFxml(String path, ApplicationContext applicationContext) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(new ClassPathResource(path).getURL());
            fxmlLoader.setControllerFactory(applicationContext::getBean);
            return fxmlLoader.load();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
