package com.php25.desktop.repostars.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @author penghuiping
 * @date 2020/9/27 11:09
 */
@Slf4j
public abstract class BaseController {

    @Autowired
    protected ConfigurableApplicationContext applicationContext;

    /**
     * 用于初始化
     */
    public abstract void initialize();
}
