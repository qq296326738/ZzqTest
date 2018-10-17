package com.zzq.zzq.controller;

import com.zzq.zzq.model.Person;
import com.zzq.zzq.model.User;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.*;

/**
 * Swagger测试类
 */

/**
 * @Api() 用于类；表示标识这个类是swagger的资源
 * tags–表示说明
 * value–也是说明，可以使用tags替代
 */
@Api(value = "功能说明", description = "默认说明", tags = {"点击显示---1"/*, "点击显示---2"*/})
@RestController
public class SwaggerTest {

    /**
     * @ApiOperation() 用于方法；表示一个http请求的操作
     * value用于方法描述
     * notes用于提示内容
     * tags可以重新分组（视情况而用）
     */
    @ApiOperation(value = "value用于方法描述", notes = "notes用于提示内容 ")
    @GetMapping("/getAdd")
    /**
     @ApiParam() 用于方法，参数，字段说明；表示对参数的添加元数据（说明或是否必填等）
     name–参数名
     value–参数说明
     required–是否必填
     */
    public Long getUserInfo(@ApiParam(name = "参数详情", value = "第一个参数", required = true) Long aa,
                            @ApiParam(name = "参数详情", value = "第二个参数") Long bb) {
        return aa + bb;
    }


    @ApiOperation("更改用户信息")
    @PostMapping("/updateUserInfo")
    public Person updateUserInfo(@RequestBody @ApiParam(name = "用户对象", value = "传入json格式", required = true) Person person) {
        person.setName("小琳琳");
        person.setAge("已经一百岁了");
        return person;
    }

    /**
     * @ApiIgnore()用于类或者方法上，可以不被swagger显示在页面上
     * @ApiImplicitParam() 用于方法
     * 表示单独的请求参数
     * @ApiImplicitParams() 用于方法，包含多个 @ApiImplicitParam
     * name–参数ming
     * value–参数说明
     * dataType–数据类型
     * paramType–参数类型
     * example–举例说明
     */
    @ApiOperation(value = "获取列表", notes = "获取列表")
    @ApiImplicitParams({                                 /*包含多个 @ApiImplicitParam*/
            @ApiImplicitParam(                           /*作用在方法上，表示单独的请求参数 */
                    name = "access_token",
                    value = "参数的具体意义，作用",
                    required = true                     /*参数是否必填*/,
                    dataType = "String",                /*参数的数据类型*/
                    paramType = "query"),/*query直接跟参数完成自动映射赋值*//*body以流的形式提交 仅支持POST*/
            @ApiImplicitParam(name = "dto",
                    value = "String",
                    paramType = "body",
                    dataType = "String")
    })
    @RequestMapping(value = "/findList", method = RequestMethod.POST)
    public String findList(@RequestParam("access_token") String access_token,
                           @RequestBody String dto) {
        return access_token + dto;
    }

}
