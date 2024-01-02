package com.example.llt.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.ToStringSerializer;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Employee {
    @JSONField(serializeUsing = ToStringSerializer.class)
    private Long id;
    private String username;
    private String password;
    private String name;
    private Integer age;
    private String phone;
    private Integer sex;
    private String create_time;
    private Integer type;
    private Integer status;

}