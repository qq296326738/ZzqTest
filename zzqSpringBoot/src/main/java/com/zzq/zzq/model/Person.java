package com.zzq.zzq.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @ApiModel()用于类 ；表示对类进行说明，用于参数用实体类接收
 * value–表示对象名
 * description–描述
 * 都可省略
 */
@ApiModel(description = "人类")
public class Person {
    /**
     * @ApiModelProperty()用于方法，字段； 表示对model属性的说明或者数据操作更改
     * value–字段说明
     * name–重写属性名字
     * dataType–重写属性类型
     * required–是否必填
     * example–举例说明
     * hidden–隐藏
     */
    @ApiModelProperty(value = "用户名", name = "name")
    private String name;
    @ApiModelProperty(value = "年级", name = "state", required = true)
    private String age;

    public Person(String name, String age) {
        this.name = name;
        this.age = age;
    }

    public Person() {

    }

    public String getName() {
        return name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public void setName(String name) {
        this.name = name;
    }
}
