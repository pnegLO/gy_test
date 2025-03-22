package com.smartapartment.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.init.DatabasePopulator;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import javax.sql.DataSource;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
public class SQLiteConfig {

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.sqlite.JDBC");
        
        // 创建data目录（如果不存在）
        try {
            Path dataDir = Paths.get("data");
            if (!Files.exists(dataDir)) {
                Files.createDirectories(dataDir);
            }
        } catch (IOException e) {
            throw new RuntimeException("无法创建数据目录", e);
        }
        
        // 使用相对路径指向data目录下的数据库文件，确保Windows路径兼容
        String dbPath = "data" + File.separator + "smart_apartment.db";
        dataSource.setUrl("jdbc:sqlite:" + dbPath);
        
        // 初始化数据库（如果需要）
        // SQLite不需要用户名和密码
        return dataSource;
    }
} 