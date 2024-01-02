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
public class Product {
    @JSONField(serializeUsing = ToStringSerializer.class)
    private Long id;
    private String name;
    private String type;
    private Double price;
    private Double purchase_price;
    private String manufacturer;
    private String specification;
    private String efficacy;
    private String instruction;
    private String side_effect;

}
