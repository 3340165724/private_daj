package com.dsj.application.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data // 为当前实体类在编译期设置对应的get/set方法，toString方法，hashCode方法，equals方法等
//@TableName("tb_user")
public class User {
    /*
    @TableId：在实体类中标识主键字段
        value：用于指定主键对应的数据库表字段名，如果与实体类中的属性名相同，则可以省略不写
        type：用于指定主键生成策略，默认为 IdType.NONE，表示不自动生成主键。常用的主键生成策略还有 IdType.AUTO（自增长）、IdType.ASSIGN_ID（手动输入主键值）、IdType.UUID（使用 UUID 生成主键）
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /*
     @TableField：指定实体类中的属性与数据库表字段的映射关系
        value：用于指定实体类属性对应的数据库表字段名，如果与属性名相同，则可以省略不写
         @TableField(value = "数据表字段名"）
            private String username;
            @TableField(value = "user_name"）
     */
    private String username;
    private String password;
    private String repassword;
}
