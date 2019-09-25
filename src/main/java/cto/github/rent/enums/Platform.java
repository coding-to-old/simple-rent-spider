package cto.github.rent.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * @Date 2019-09-25
 * @author ybbzbb
 * @Description 站点
 * */
@AllArgsConstructor
@Getter
public enum Platform {


    DOUBAN(100,"DB","豆瓣");

    private Integer id;

    private String shorthand;

    private String cnName;
}
