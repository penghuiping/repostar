package com.php25.desktop.repostars.github.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author penghuiping
 * @date 2020/9/23 14:04
 */
@Setter
@Getter
public class User {

    private String login;

    private Long id;

    @JsonProperty("avatar_url")
    private String avatarUrl;

    @JsonProperty("html_url")
    private String htmlUrl;

    private String name;

    private String email;
}
