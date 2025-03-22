package com.smartapartment.dto;

import com.smartapartment.model.DeviceStatus;
import com.smartapartment.model.DeviceType;
import com.smartapartment.model.Apartment;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class DeviceDTO {
    private Long id;
    
    @NotBlank(message = "设备名称不能为空")
    private String name;
    
    @NotNull(message = "设备类型不能为空")
    private DeviceType type;
    
    @NotNull(message = "设备状态不能为空")
    private DeviceStatus status;
    
    @NotNull(message = "所属公寓不能为空")
    private Apartment apartment;
    
    private String description;
} 