package com.php25.desktop.repostars.util;

import com.php25.desktop.repostars.service.dto.UserDto;

import java.util.HashMap;
import java.util.Map;

/**
 * @author penghuiping
 * @date 2020/10/2 12:06
 */
public class LocalStorage {
    private final Map<String, String> map = new HashMap<>();

    private UserDto tbUser = null;

    public synchronized void put(String key, String value) {
        map.put(key, value);
    }

    public synchronized String get(String key) {
        return map.get(key);
    }

    public synchronized void save(UserDto tbUser) {
        this.tbUser = tbUser;
    }


    public synchronized UserDto getLoginUser() {
        return this.tbUser;
    }

    public synchronized void clearAll() {
        this.tbUser = null;
        map.clear();
    }
}
