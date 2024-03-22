package com.wjs;

import com.github.tobato.fastdfs.FdfsClientConfig;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * description
 *
 * @author wangjunshan
 * @date 2023/11/16 10:03
 */
@SpringBootApplication
@MapperScan(basePackages = "com.wjs.mapper")
@EnableScheduling
@Import(FdfsClientConfig.class)
public class BlogApplication {

    public static void main(String[] args) {
        SpringApplication.run(BlogApplication.class);
    }

}
