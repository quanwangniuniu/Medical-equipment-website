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
public class ClockIn {
    @JSONField(serializeUsing = ToStringSerializer.class)
    private Long id;
    private Long employee_id;
    private String name;
    private String time;
}
