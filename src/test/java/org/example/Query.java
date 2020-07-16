package org.example;

import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.index.query.Operator;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.example.utils.EsClientUtils;
import org.junit.Test;

import java.io.IOException;
import java.util.Map;

/**
 * 描述:
 *
 * @author yuanyuan.liao@pactera.com
 * @date 2020/7/16 22:09
 */
public class Query {

  String index = "person";
  String type = "man";

  // term 查询
  @Test
  public void termTest() throws IOException {

    SearchRequest searchRequest = new SearchRequest(index);
    searchRequest.types(type);

    SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
    searchSourceBuilder //
        .from(0) //
        .size(10)
        .query(QueryBuilders.termQuery("sex", "男"));

    searchRequest.source(searchSourceBuilder);

    SearchResponse search =
        EsClientUtils.getEsClient().search(searchRequest, RequestOptions.DEFAULT);

    for (SearchHit hit : search.getHits().getHits()) {
      System.out.println("hit = " + hit.getSourceAsMap());
    }
  }

  // terms 查询
  @Test
  public void termsTest() throws IOException {

    SearchRequest searchRequest = new SearchRequest(index);
    searchRequest.types(type);

    SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
    searchSourceBuilder //
        .from(0) //
        .size(10)
        .query(QueryBuilders.termsQuery("sex", "男", "女"));

    searchRequest.source(searchSourceBuilder);

    SearchResponse search =
        EsClientUtils.getEsClient().search(searchRequest, RequestOptions.DEFAULT);

    for (SearchHit hit : search.getHits().getHits()) {
      System.out.println("hit = " + hit.getSourceAsMap());
    }
  }

  @Test
  public void matchAllTest() throws IOException {

    SearchRequest searchRequest = new SearchRequest(index);
    searchRequest.types(type);

    SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
    SearchSourceBuilder query = searchSourceBuilder.query(QueryBuilders.matchAllQuery());

    searchRequest.source(query);

    SearchResponse searchResponse =
        EsClientUtils.getEsClient().search(searchRequest, RequestOptions.DEFAULT);

    for (SearchHit hit : searchResponse.getHits().getHits()) {
      System.out.println("hit.getSourceAsMap() = " + hit.getSourceAsMap());
    }
  }

  @Test
  public void matchTest() throws IOException {

    SearchRequest searchRequest = new SearchRequest(index);
    searchRequest.types(type);

    SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
    SearchSourceBuilder query = searchSourceBuilder.query(QueryBuilders.matchQuery("des", "股份"));

    searchRequest.source(query);

    SearchResponse searchResponse =
        EsClientUtils.getEsClient().search(searchRequest, RequestOptions.DEFAULT);

    for (SearchHit hit : searchResponse.getHits().getHits()) {
      System.out.println("hit.getSourceAsMap() = " + hit.getSourceAsMap());
    }
  }

  @Test
  public void booleanMatchTest() throws IOException {

    SearchRequest searchRequest = new SearchRequest(index);
    searchRequest.types(type);

    SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
    SearchSourceBuilder query =
        searchSourceBuilder.query(QueryBuilders.matchQuery("des", "股份 韩国").operator(Operator.OR));

    searchRequest.source(query);

    SearchResponse searchResponse =
        EsClientUtils.getEsClient().search(searchRequest, RequestOptions.DEFAULT);

    for (SearchHit hit : searchResponse.getHits().getHits()) {
      System.out.println("hit.getSourceAsMap() = " + hit.getSourceAsMap());
    }
  }

  @Test
  public void MultiMatchTest() throws IOException {

    SearchRequest searchRequest = new SearchRequest(index);
    searchRequest.types(type);

    SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
    SearchSourceBuilder query =
        searchSourceBuilder.query(QueryBuilders.multiMatchQuery("天津 坦然", "address", "des"));

    searchRequest.source(query);

    SearchResponse searchResponse =
        EsClientUtils.getEsClient().search(searchRequest, RequestOptions.DEFAULT);

    for (SearchHit hit : searchResponse.getHits().getHits()) {
      System.out.println("hit.getSourceAsMap() = " + hit.getSourceAsMap());
    }
  }

  @Test
  public void getTest() throws IOException {

    GetRequest getRequest = new GetRequest(index, type, "1");

    GetResponse documentFields =
        EsClientUtils.getEsClient().get(getRequest, RequestOptions.DEFAULT);

    Map<String, Object> source = documentFields.getSource();

    System.out.println("source = " + source);
  }

  @Test
  public void idsTest() throws IOException {
    SearchRequest searchRequest = new SearchRequest(index);
    searchRequest.types(type);

    SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
    searchSourceBuilder.query(QueryBuilders.idsQuery().addIds("1").addIds("2"));

    searchRequest.source(searchSourceBuilder);

    SearchResponse searchResponse =
        EsClientUtils.getEsClient().search(searchRequest, RequestOptions.DEFAULT);

    for (SearchHit hit : searchResponse.getHits().getHits()) {
      System.out.println("hit.getSourceAsMap() = " + hit.getSourceAsMap());
    }
  }

  @Test
  public void prefixTest() throws IOException {
    SearchRequest searchRequest = new SearchRequest(index);
    searchRequest.types(type);

    SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
    searchSourceBuilder.query(QueryBuilders.prefixQuery("des", "韩"));

    searchRequest.source(searchSourceBuilder);

    SearchResponse searchResponse =
        EsClientUtils.getEsClient().search(searchRequest, RequestOptions.DEFAULT);

    for (SearchHit hit : searchResponse.getHits().getHits()) {
      System.out.println("hit.getSourceAsMap() = " + hit.getSourceAsMap());
    }
  }

  @Test
  public void fuzzyTest() throws IOException {
    SearchRequest searchRequest = new SearchRequest(index);
    searchRequest.types(type);

    SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
    searchSourceBuilder.query(QueryBuilders.fuzzyQuery("name", "李"));

    searchRequest.source(searchSourceBuilder);

    SearchResponse searchResponse =
        EsClientUtils.getEsClient().search(searchRequest, RequestOptions.DEFAULT);

    for (SearchHit hit : searchResponse.getHits().getHits()) {
      System.out.println("hit.getSourceAsMap() = " + hit.getSourceAsMap());
    }
  }

  @Test
  public void wildcardTest() throws IOException {
    SearchRequest searchRequest = new SearchRequest(index);
    searchRequest.types(type);

    SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
    searchSourceBuilder.query(QueryBuilders.wildcardQuery("name", "王*"));

    searchRequest.source(searchSourceBuilder);

    SearchResponse searchResponse =
        EsClientUtils.getEsClient().search(searchRequest, RequestOptions.DEFAULT);

    for (SearchHit hit : searchResponse.getHits().getHits()) {
      System.out.println("hit.getSourceAsMap() = " + hit.getSourceAsMap());
    }
  }
}
