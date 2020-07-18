package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.common.xcontent.XContentType;
import org.example.utils.EsClientUtils;
import org.junit.Test;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * 描述:
 *
 * @author yuanyuan.liao@pactera.com
 * @date 2020/7/16 20:13
 */
public class Doc {

  ObjectMapper objectMapper = new ObjectMapper();
  String index = "person";
  String type = "man";

  @Test
  public void docCreate() throws IOException, ParseException {
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

    Person person =
        new Person(
            2,
            "张三",
            30,
            "男",
            simpleDateFormat.parse("1990-09-09"),
            "北京",
            "昌平，沙河",
            "192.168.1.111",
            "fdsafsad发大发");

    String s = objectMapper.writeValueAsString(person);

    IndexRequest indexRequest = new IndexRequest(index, type, person.getId().toString());
    indexRequest.source(s, XContentType.JSON);

    IndexResponse indexResponse =
        EsClientUtils.getEsClient().index(indexRequest, RequestOptions.DEFAULT);

    DocWriteResponse.Result result = indexResponse.getResult();

    System.out.println("result = " + result);
  }

  @Test
  public void updateDoc() throws IOException {

    Map<String, Object> stringObjectHashMap = new HashMap<String, Object>();
    stringObjectHashMap.put("name", "lyy");

    UpdateRequest updateRequest = new UpdateRequest(index, type, "1");
    updateRequest.doc(stringObjectHashMap);

    UpdateResponse updateResponse =
        EsClientUtils.getEsClient().update(updateRequest, RequestOptions.DEFAULT);

    System.out.println("update = " + updateResponse.getResult());
  }

  @Test
  public void deleteDoc() throws IOException {

    DeleteRequest deleteRequest = new DeleteRequest(index, type, "1");

    DeleteResponse deleteResponseete =
        EsClientUtils.getEsClient().delete(deleteRequest, RequestOptions.DEFAULT);

    System.out.println("update = " + deleteResponseete.getResult());
  }

  @Test
  public void batchAddDoc() throws IOException, ParseException {

    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

    Person person1 =
        new Person(
            1,
            "小米",
            20,
            "男",
            simpleDateFormat.parse("1989-12-01"),
            "北京",
            "海淀 西二旗",
            "192.168.2.1",
            "fdsafsad发大发");
    Person person2 =
        new Person(
            2,
            "lyy",
            23,
            "男",
            simpleDateFormat.parse("1999-12-12"),
            "天津",
            " 红桥",
            "192.168.2.2",
            "fdsafsad发大发");
    Person person3 =
        new Person(
            3,
            "lojzes",
            30,
            "男",
            simpleDateFormat.parse("1978-12-03"),
            "天津",
            "西青 张家窝",
            "192.168.2.3",
            "范德萨发生的");
    Person person4 =
        new Person(
            4,
            "张三",
            32,
            "女",
            simpleDateFormat.parse("1989-11-02"),
            "天津",
            "北京 河东",
            "192.168.2.4",
            "古方红糖人");
    Person person5 =
        new Person(
            5,
            "李四",
            32,
            "男",
            simpleDateFormat.parse("1988-05-22"),
            "天津",
            "北京 河东",
            "192.168.2.5",
            "恢复工具");
    Person person6 =
        new Person(
            5,
            "王五",
            32,
            "女",
            simpleDateFormat.parse("1999-12-11"),
            "上海",
            "河东",
            "192.168.2.6",
            "糊涂");
    Person person7 =
        new Person(
            6,
            "赵六",
            32,
            "男",
            simpleDateFormat.parse("2000-02-02"),
            "上海",
            "河东",
            "192.168.2.7",
            "坦然坦然坦然");
    Person person8 =
        new Person(
            7,
            "国庆",
            32,
            "女",
            simpleDateFormat.parse("1992-12-12"),
            "广州",
            "河东",
            "192.168.2.8",
            "也太容易");
    Person person9 =
        new Person(
            8,
            "李娜",
            32,
            "男",
            simpleDateFormat.parse("1993-03-09"),
            "北京",
            "北京 河东",
            "192.168.2.9",
            "也会烦得很");
    Person person10 =
        new Person(
            9,
            "谢娜",
            32,
            "女",
            simpleDateFormat.parse("1994-12-09"),
            "北京",
            "北京 河东",
            "192.168.2.10",
            "韩国护肤");
    Person person11 =
        new Person(
            10,
            "何炅",
            32,
            "男",
            simpleDateFormat.parse("1995-09-08"),
            "北京",
            "北京 河东",
            "192.168.2.11",
            " 公司的股份的时光");
    Person person12 =
        new Person(
            11,
            "黄磊",
            32,
            "男",
            simpleDateFormat.parse("1996-09-09"),
            "北京",
            "北京 河东",
            "192.168.2.12",
            "割发代首告诉辅导员人");

    BulkRequest bulkRequest = new BulkRequest();
    bulkRequest.add(
        new IndexRequest(index, type, person1.getId().toString())
            .source(objectMapper.writeValueAsString(person1), XContentType.JSON));
    bulkRequest.add(
        new IndexRequest(index, type, person2.getId().toString())
            .source(objectMapper.writeValueAsString(person2), XContentType.JSON));

    bulkRequest.add(
        new IndexRequest(index, type, person3.getId().toString())
            .source(objectMapper.writeValueAsString(person3), XContentType.JSON));

    bulkRequest.add(
        new IndexRequest(index, type, person4.getId().toString())
            .source(objectMapper.writeValueAsString(person4), XContentType.JSON));

    bulkRequest.add(
        new IndexRequest(index, type, person5.getId().toString())
            .source(objectMapper.writeValueAsString(person5), XContentType.JSON));
    bulkRequest.add(
        new IndexRequest(index, type, person6.getId().toString())
            .source(objectMapper.writeValueAsString(person6), XContentType.JSON));
    bulkRequest.add(
        new IndexRequest(index, type, person7.getId().toString())
            .source(objectMapper.writeValueAsString(person7), XContentType.JSON));

    bulkRequest.add(
        new IndexRequest(index, type, person8.getId().toString())
            .source(objectMapper.writeValueAsString(person8), XContentType.JSON));

    bulkRequest.add(
        new IndexRequest(index, type, person9.getId().toString())
            .source(objectMapper.writeValueAsString(person9), XContentType.JSON));

    bulkRequest.add(
        new IndexRequest(index, type, person10.getId().toString())
            .source(objectMapper.writeValueAsString(person10), XContentType.JSON));

    bulkRequest.add(
        new IndexRequest(index, type, person11.getId().toString())
            .source(objectMapper.writeValueAsString(person11), XContentType.JSON));

    bulkRequest.add(
        new IndexRequest(index, type, person12.getId().toString())
            .source(objectMapper.writeValueAsString(person12), XContentType.JSON));

    BulkResponse bulkItemResponses =
        EsClientUtils.getEsClient().bulk(bulkRequest, RequestOptions.DEFAULT);

    System.out.println("bulk = " + bulkItemResponses);
  }
}
