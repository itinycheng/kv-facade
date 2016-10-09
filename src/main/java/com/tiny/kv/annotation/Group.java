package com.tiny.kv.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 组/列簇别名
 * 通过该别名映射到实际表中的列
 * demo:
 * family0 = 1 << 0
 * family1 = 1 << 1
 * iscontainFmaily0 = inputParam & family0 > 0 ? true : false;
 * iscontainFmaily0 : 是否查询表中列簇为family0的数据
 *
 * @author tiny
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface Group {

    /**
     * 组/列簇的匿名
     *
     * @return
     */
    long alias();

    String name();


}
