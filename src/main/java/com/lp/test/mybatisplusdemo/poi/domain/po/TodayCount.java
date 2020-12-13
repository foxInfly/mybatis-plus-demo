package com.lp.test.mybatisplusdemo.poi.domain.po;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author lp
 * @since 2020-12-13 16:25:55
 */
@Data
@Accessors(chain = true)
@TableName(value = "today_count")
public class TodayCount implements Serializable {
    private static final long serialVersionUID = -613348840786334526L;

    @TableId(value = "id",type = IdType.ASSIGN_UUID)
    private String id;

    @TableField
    private String areaCode;

    @TableField
    private String deptName;

    //总-行政权力类数量
    @TableField
    private String totalExecutivePower;

    //目录认领-行政权力类数量
    @TableField
    private String directoryExecutivePower;

    //实施清单编制-行政权力类数量
    @TableField
    private String listRuelExecutivePower;

    //实施清单审核-行政权力类数量
    @TableField
    private String listCheckExecutivePower;

    //实施清单发布-行政权力类数量
    @TableField
    private String listPublishExecutivePower;

    //办理项
    @TableField
    private String dealExecutivePower;

    //事项情形化
    @TableField
        private String itemExecutivePower;

    //高频事项
    @TableField
    private String heighExecutivePower;

    //最多跑一次事项
    @TableField
    private String oneMoreExecutivePower;

    //不见面审批事项
    @TableField
    private String noMeetExecutivePower;



}
