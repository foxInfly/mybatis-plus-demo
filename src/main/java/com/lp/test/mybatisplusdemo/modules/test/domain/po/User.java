package com.lp.test.mybatisplusdemo.modules.test.domain.po;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

/**Po要和数据库的表名完全一致，驼峰对应_
 * @author lp
 * @since 2020-12-12 14:36:47
 */
@Data
@Accessors(chain = true)
@TableName(value = "user")
public class User {
    /**
     * 常用属性：
     *         value           用于定义主键字段名
     *         type            用于定义主键类型（主键策略 IdType）
     * 主键策略：
     *       IdType.AUTO          主键自增，系统分配，不需要手动输入
     *       IdType.NONE          未设置主键
     *       IdType.INPUT         需要自己输入 主键值。
     *       IdType.ASSIGN_ID     系统分配 ID，用于数值型数据（Long，对应 mysql 中 BIGINT 类型）。
     *       IdType.ASSIGN_UUID   系统分配 UUID，用于字符串型数据（String，对应 mysql 中 varchar(32) 类型）。
     **/
    @TableId(value = "id",type = IdType.AUTO)
    private Long id;
    /**
     * 常用属性：
     *         value                用于定义非主键字段名
     *         exist                用于指明是否为数据表的字段， true 表示是，false 为不是。
     *         fill                 用于指定字段填充策略（FieldFill）。
     * 字段填充策略：（一般用于填充 创建时间、修改时间等字段）
     *         FieldFill.DEFAULT         默认不填充
     *         FieldFill.INSERT          插入时填充
     *         FieldFill.UPDATE          更新时填充
     *         FieldFill.INSERT_UPDATE   插入、更新时填充。
     **/
    @TableField(value = "name",exist = true,fill = FieldFill.DEFAULT)
    private String name;
    private int age;
    private String email;
    /**
     * 常用属性：
     *         value            用于定义未删除时字段的值
     *         delval           用于定义删除时字段的值
     */
    @TableLogic (value = "0",delval = "1")
    @TableField(value = "is_del",exist = true,fill = FieldFill.INSERT)
    private String isDel;
    @TableField(value = "create_date",exist = true,fill = FieldFill.INSERT)
    private Date createDate;
    @TableField(value = "update_date",exist = true,fill = FieldFill.INSERT_UPDATE)
    private Date updateDate;
}
