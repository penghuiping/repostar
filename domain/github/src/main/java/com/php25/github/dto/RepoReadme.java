package com.php25.github.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * @author penghuiping
 * @date 2020/10/10 13:59
 */
@Getter
@Setter
public class RepoReadme {

    private String encoding;

    private Long size;

    private String content;
}
