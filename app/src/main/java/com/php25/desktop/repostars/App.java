package com.php25.desktop.repostars;

import com.php25.desktop.repostars.config.JavaFxApp;
import javafx.application.Application;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.php25")
public class App {
    public static void main(String[] args) {
        Application.launch(JavaFxApp.class, args);
    }
}
