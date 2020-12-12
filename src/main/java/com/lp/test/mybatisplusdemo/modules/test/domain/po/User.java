package com.lp.test.mybatisplusdemo.modules.test.domain.po;

import lombok.Data;
import lombok.experimental.Accessors;

/**Po要和数据库的表明完全一致，驼峰对应_
 * @author lp
 * @since 2020-12-12 14:36:47
 */
@Data
@Accessors(chain = true)
public class User {
    private Long id;
    private String name;
    private int age;
    private String email;
}
