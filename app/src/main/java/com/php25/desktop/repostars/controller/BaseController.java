package com.php25.desktop.repostars.controller;

import com.php25.common.core.exception.BusinessException;
import com.php25.common.core.exception.IllegalStateException;
import com.php25.desktop.repostars.util.GlobalUtil;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @author penghuiping
 * @date 2020/9/27 11:09
 */
@Slf4j
public abstract class BaseController implements EventHandler<MouseEvent> {

    @Autowired
    protected ConfigurableApplicationContext applicationContext;

    /**
     * 用于初始化
     */
    public final void initialize() {
        try {
            this.start();
        } catch (BusinessException | IllegalArgumentException e) {
            GlobalUtil.showErrorMsg(e.getMessage());
        } catch (Exception e) {
            log.error("出错啦", e);
        }
    }

    @Override
    public final void handle(MouseEvent mouseEvent) {
        try {
            this.handleMouseEvent(mouseEvent);
        } catch (BusinessException | IllegalArgumentException | IllegalStateException e) {
            GlobalUtil.showErrorMsg(e.getMessage());
        } catch (Exception e) {
            log.error("出错啦", e);
        }
    }

    public abstract void start() throws Exception;

    public void handleMouseEvent(MouseEvent mouseEvent) throws Exception {

    }
}
