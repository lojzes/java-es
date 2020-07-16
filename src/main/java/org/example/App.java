package org.example;

import org.elasticsearch.client.RestHighLevelClient;
import org.example.utils.EsClientUtils;

/** Hello world! */
public class App {
  public static void main(String[] args) {
    RestHighLevelClient esClient = EsClientUtils.getEsClient();

    System.out.println("esClient = " + esClient);
  }
}
