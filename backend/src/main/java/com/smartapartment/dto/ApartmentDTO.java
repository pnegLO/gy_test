package com.smartapartment.dto;

import com.smartapartment.model.ApartmentStatus;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

@Data
public class ApartmentDTO {
    private Long id;
    
    @NotBlank(message = "名称不能为空")
    private String name;
    
    @NotBlank(message = "地址不能为空")
    private String address;
    
    @NotNull(message = "面积不能为空")
    @Positive(message = "面积必须大于0")
    private BigDecimal area;
    
    @NotNull(message = "价格不能为空")
    @Positive(message = "价格必须大于0")
    private BigDecimal price;
    
    @NotNull(message = "状态不能为空")
    private ApartmentStatus status;
    
    private String description;
} 