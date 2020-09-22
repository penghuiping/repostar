package com.php25.desktop.repostars.github.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author penghuiping
 * @date 2020/9/22 16:58
 */
@Setter
@Getter
public class Gist {

    private Long id;

    private String name;

    @JsonProperty("full_name")
    private String fullName;

    @JsonProperty("html_url")
    private String htmlUrl;

    private String description;

    private String language;

    private Integer forks;

    private Integer watchers;
}
