package org.example;

import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.search.*;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.Operator;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.reindex.BulkByScrollResponse;
import org.elasticsearch.index.reindex.DeleteByQueryRequest;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.example.utils.EsClientUtils;
import org.junit.Test;

import java.io.IOException;
import java.util.Map;

/**
 * 描述:
 *
 * @author lojzes@qq.com
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

  @Test
  public void rangeTest() throws IOException {
    SearchRequest searchRequest = new SearchRequest(index);
    searchRequest.types(type);

    SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
    searchSourceBuilder.query(
        QueryBuilders.rangeQuery("age") //
            .gte(30) //
            .lte(32));

    searchRequest.source(searchSourceBuilder);

    SearchResponse searchResponse =
        EsClientUtils.getEsClient().search(searchRequest, RequestOptions.DEFAULT);

    for (SearchHit hit : searchResponse.getHits().getHits()) {
      System.out.println("hit.getSourceAsMap() = " + hit.getSourceAsMap());
    }
  }

  // 正则表达式
  @Test
  public void regexpTest() throws IOException {
    SearchRequest searchRequest = new SearchRequest(index);
    searchRequest.types(type);

    SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
    searchSourceBuilder.query(QueryBuilders.regexpQuery("address", "北京*"));

    searchRequest.source(searchSourceBuilder);

    SearchResponse searchResponse =
        EsClientUtils.getEsClient().search(searchRequest, RequestOptions.DEFAULT);

    for (SearchHit hit : searchResponse.getHits().getHits()) {
      System.out.println("hit.getSourceAsMap() = " + hit.getSourceAsMap());
    }
  }

  @Test
  public void scrollTest() throws IOException {

    SearchRequest searchRequest = new SearchRequest(index);
    searchRequest.types(type);
    searchRequest.scroll(TimeValue.timeValueMinutes(2L));

    SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
    searchSourceBuilder
        .size(2) //
        .sort("id", SortOrder.ASC);
    searchSourceBuilder.query(QueryBuilders.matchAllQuery());

    searchRequest.source(searchSourceBuilder);

    SearchResponse search =
        EsClientUtils.getEsClient().search(searchRequest, RequestOptions.DEFAULT);

    String scrollId = search.getScrollId();
    System.out.println("scrollId = " + scrollId);
    System.out.println("------------------首页-------------------------");

    for (SearchHit hit : search.getHits().getHits()) {
      System.out.println("hit.getSourceAsMap() = " + hit.getSourceAsMap());
    }

    while (true) {

      SearchScrollRequest searchScrollRequest = new SearchScrollRequest(scrollId);
      searchScrollRequest.scroll(TimeValue.timeValueMillis(2L));

      SearchResponse searchResponse =
          EsClientUtils.getEsClient().scroll(searchScrollRequest, RequestOptions.DEFAULT);

      SearchHit[] hits = searchResponse.getHits().getHits();

      if (null != hits && hits.length > 0) {
        System.out.println("------------------下一页-------------------------");

        for (SearchHit hit : hits) {
          System.out.println("hit = " + hit.getSourceAsMap());
        }
      } else {
        System.out.println("------------------结束-------------------------");
        break;
      }
    }

    ClearScrollRequest clearScrollRequest = new ClearScrollRequest();
    clearScrollRequest.addScrollId(scrollId);

    ClearScrollResponse clearScrollResponse =
        EsClientUtils.getEsClient().clearScroll(clearScrollRequest, RequestOptions.DEFAULT);

    System.out.println("clearScrollResponse = " + clearScrollResponse);
  }

  @Test
  public void deleteByQuery() throws IOException {

    DeleteByQueryRequest deleteByQueryRequest = new DeleteByQueryRequest(index);
    deleteByQueryRequest.types(type);

    deleteByQueryRequest.setQuery(QueryBuilders.rangeQuery("id").lte(3));

    BulkByScrollResponse bulkByScrollResponse =
        EsClientUtils.getEsClient().deleteByQuery(deleteByQueryRequest, RequestOptions.DEFAULT);
    System.out.println("bulkByScrollResponse = " + bulkByScrollResponse);
  }

  @Test
  public void booleanQuery() throws IOException {

    SearchRequest searchRequest = new SearchRequest(index);
    searchRequest.types(type);

    SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

    BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();

    boolQueryBuilder.must(QueryBuilders.matchQuery("name", "黄磊"));
    boolQueryBuilder.must(QueryBuilders.rangeQuery("age").gte(32));

    searchSourceBuilder.query(boolQueryBuilder);

    searchRequest.source(searchSourceBuilder);

    SearchResponse search =
        EsClientUtils.getEsClient().search(searchRequest, RequestOptions.DEFAULT);

    for (SearchHit hit : search.getHits().getHits()) {
      System.out.println("hit = " + hit.getSourceAsMap());
    }
  }
}
