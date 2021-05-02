package com.php25.github.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author penghuiping
 * @date 2020/9/23 13:34
 */
@Setter
@Getter
public class Repos {

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
