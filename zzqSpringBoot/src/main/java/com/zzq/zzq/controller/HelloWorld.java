package com.zzq.zzq.controller;

import com.zzq.zzq.model.GdsMember;
import com.zzq.zzq.model.Person;
import com.zzq.zzq.model.User;
import com.zzq.zzq.service.PersonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class HelloWorld {

    private final Logger log = LoggerFactory.getLogger(HelloWorld.class);

    @Value("${book.name:小强}")
    private String name;

    @Autowired
    private PersonService personService;

    @ResponseBody
    @RequestMapping("/helloWorld")
    public Person HelloWorld() {
        Person person = personService.testUser();
        person.setAge("11111");
        person.setName("账单");
        log.info("哈哈哈哈哈,我的第一次!!!!!!!!!!!!!!!");
        return person;
    }

    @RequestMapping("/helloWorld1")
    public String HelloWorld1() {
        return "index";
    }

    @ResponseBody
    @RequestMapping("/helloWorld2")
    public GdsMember helloWorld2() {
        GdsMember member = personService.findByName("总承包");
        return member;
    }

    @ResponseBody
    @RequestMapping("/helloWorld3")
    public List<User> helloWorld3() {
        List<User> user = personService.getUser();
        return user;
    }

    @ResponseBody
    @RequestMapping("/helloWorld4")
    public List<User> helloWorld4() {
        List<User> user = personService.findByAllUser();
        return user;
    }

    @ResponseBody
    @RequestMapping("helloWorld5")
    public User findBy() {
        User by = personService.findBy();
        return by;
    }

    @ResponseBody
    @RequestMapping("helloWorld6")
    public List<User> selectPage() {
        List<User> users = personService.selectPage();
        return users;
    }

    @RequestMapping("table")
    public String table() {

        return "jquery_table";
    }

}
