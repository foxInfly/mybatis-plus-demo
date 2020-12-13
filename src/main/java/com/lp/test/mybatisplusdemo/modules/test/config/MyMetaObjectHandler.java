package com.lp.test.mybatisplusdemo.modules.test.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.lp.test.mybatisplusdemo.modules.test.constant.IsDelete;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * mybtais自定义填充的内容
 *
 * @author lp
 * @since 2020-12-12 16:07:20
 */
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        this.strictInsertFill(metaObject, "createDate", Date.class, new Date());
        this.strictInsertFill(metaObject, "updateDate", Date.class, new Date());
        this.strictInsertFill(metaObject, "isDel", String.class, IsDelete.NO.getCode());
        this.strictInsertFill(metaObject, "version", Integer.class, 1);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.strictUpdateFill(metaObject, "updateDate", Date.class, new Date());
    }
}
