package org.example;

import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.admin.indices.get.GetIndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.json.JsonXContent;
import org.example.utils.EsClientUtils;
import org.junit.Test;

import java.io.IOException;

/**
 * 描述:
 *
 * @author yuanyuan.liao@pactera.com
 * @date 2020/7/16 19:33
 */
public class Index {

  String index = "person";
  String type = "man";

  @Test
  public void indexCreateTest() throws IOException {
    // 创建Index

    Settings.Builder builder = Settings.builder();

    Settings.Builder settings =
        builder //
            .put("number_of_shards", 5) //
            .put("number_of_replicas", 1);

    XContentBuilder xContentBuilder =
        JsonXContent.contentBuilder()
            .startObject() //
            .startObject("properties") //
            .startObject("name") //
            .field("type", "text") //
            .endObject() //
            .startObject("age") //
            .field("type", "integer") //
            .endObject() //
            .startObject("sex") //
            .field("type", "keyword") //
            .endObject() //
            .startObject("birthday") //
            .field("type", "date") //
            .field("format", "yyyy-MM-dd")
            .endObject()
            .startObject("address") //
            .field("type", "text") //
            .endObject() //
            .startObject("des") //
            .field("type", "text") //
            .field("analyzer", "ik_max_word")
            .endObject()
            .endObject()
            .endObject();

    CreateIndexRequest createIndexRequest =
        new CreateIndexRequest(index) //
            .settings(settings) //
            .mapping(type, xContentBuilder);

    CreateIndexResponse createIndexResponse =
        EsClientUtils.getEsClient().indices().create(createIndexRequest, RequestOptions.DEFAULT);

    System.out.println("createIndexResponse = " + createIndexResponse);
  }

  /**
   * 备注: 删除索引
   *
   * @author lojzes@qq.com
   * @date 19:53 2020/7/16
   */
  @Test
  public void indexDelete() throws IOException {
    DeleteIndexRequest deleteRequest = new DeleteIndexRequest(index);
    EsClientUtils.getEsClient().indices().delete(deleteRequest, RequestOptions.DEFAULT);
  }

  // 索引是否存在
  @Test
  public void indexExists() throws IOException {
    GetIndexRequest getIndexRequest = new GetIndexRequest();
    getIndexRequest.indices(index);
    boolean exists =
        EsClientUtils.getEsClient().indices().exists(getIndexRequest, RequestOptions.DEFAULT);

    System.out.println("exists = " + exists);
  }
}
