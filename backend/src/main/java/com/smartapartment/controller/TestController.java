package com.smartapartment.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import javax.persistence.EntityManager;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private EntityManager entityManager;

    @GetMapping("/hello")
    public String hello() {
        return "Hello from Smart Apartment Backend!";
    }

    @GetMapping("/db")
    public Map<String, Object> testDatabase() {
        Map<String, Object> response = new HashMap<>();
        try {
            // 测试查询公寓数量
            Long apartmentCount = (Long) entityManager
                .createQuery("SELECT COUNT(a) FROM Apartment a")
                .getSingleResult();
            
            // 测试查询设备数量
            Long deviceCount = (Long) entityManager
                .createQuery("SELECT COUNT(d) FROM Device d")
                .getSingleResult();
            
            // 测试查询租约数量
            Long leaseCount = (Long) entityManager
                .createQuery("SELECT COUNT(l) FROM Lease l")
                .getSingleResult();

            response.put("success", true);
            response.put("message", "Database connection successful");
            
            // 替换 Map.of() 为 HashMap
            Map<String, Long> data = new HashMap<>();
            data.put("apartments", apartmentCount);
            data.put("devices", deviceCount);
            data.put("leases", leaseCount);
            response.put("data", data);
            
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Database error: " + e.getMessage());
        }
        return response;
    }

    @GetMapping("/tables")
    public Map<String, Object> showTables() {
        Map<String, Object> response = new HashMap<>();
        try {
            List<String> tables = entityManager
                .createNativeQuery("SHOW TABLES")
                .getResultList();
            
            response.put("success", true);
            response.put("tables", tables);
        } catch (Exception e) {
            response.put("success", false);
            response.put("error", e.getMessage());
        }
        return response;
    }
} 