package com.smartapartment.dto;

import com.smartapartment.model.Apartment;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class LeaseDTO {
    private Long id;
    
    @NotNull(message = "公寓不能为空")
    private Apartment apartment;
    
    @NotNull(message = "租户ID不能为空")
    private Long tenantId;
    
    @NotNull(message = "开始日期不能为空")
    private LocalDate startDate;
    
    @NotNull(message = "结束日期不能为空")
    private LocalDate endDate;
    
    @NotNull(message = "月租金不能为空")
    @Positive(message = "月租金必须大于0")
    private BigDecimal monthlyRent;
    
    @NotNull(message = "押金不能为空")
    @Positive(message = "押金必须大于0")
    private BigDecimal deposit;
    
    private String terms;
} 