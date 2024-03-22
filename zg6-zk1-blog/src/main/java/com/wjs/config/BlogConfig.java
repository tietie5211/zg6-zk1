package com.wjs.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * description
 *
 * @author wangjunshan
 * @date 2023/11/19 21:04
 */
@Component
public class BlogConfig {

    // bean 作用  直接存入 springboot 里  利用  Autowired 注入就可以
    @Bean
    public RestHighLevelClient initElasticSearch() {

        // HttpHost localhost = new HttpHost("192.168.171.129", 9200);
        // RestClientBuilder builder = RestClient.builder(localhost);

        RestClientBuilder builder = RestClient.builder(new HttpHost("192.168.171.129", 9200));

        return new RestHighLevelClient(builder);
    }


}
