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
public class Leave {
    @JSONField(serializeUsing = ToStringSerializer.class)
    private Long id;
    private Long employee_id;
    private String name;
    private String start_date;
    private String end_date;
    private String reason;
    private Integer type;
    //=0 待审批; =1 批准 =2 拒绝
    private Integer status;

}
