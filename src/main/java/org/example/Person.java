package org.example;

import com.fasterxml.jackson.annotation.JsonFormat;
import jdk.nashorn.internal.ir.annotations.Ignore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 描述:
 *
 * @author yuanyuan.liao@pactera.com
 * @date 2020/7/16 20:09
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Person {

  @Ignore private Integer id;
  private String name;
  private Integer age;
  private String sex;

  @JsonFormat(pattern = "yyyy-MM-dd")
  private Date birthday;

  private String address;
  private String des;
}
