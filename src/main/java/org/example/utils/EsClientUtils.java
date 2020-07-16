package org.example.utils;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;

/**
 * 描述:
 *
 * @author yuanyuan.liao@pactera.com
 * @date 2020/7/16 19:18
 */
public class EsClientUtils {

  public static RestHighLevelClient getEsClient() {

    HttpHost httpHost = new HttpHost("192.168.1.11", 9200);

    RestClientBuilder restClientBuilder = RestClient.builder(httpHost);

    RestHighLevelClient restHighLevelClient = new RestHighLevelClient(restClientBuilder);

    return restHighLevelClient;
  }
}
