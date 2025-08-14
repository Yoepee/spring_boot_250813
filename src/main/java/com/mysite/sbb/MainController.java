package com.mysite.sbb;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDateTime;

@Controller
public class MainController {
    @ResponseBody
    @GetMapping("/")
    public String index() {
        return "sbb";
    }

    @ResponseBody
    @GetMapping("/hello")
    public String hello() {
        return "안녕하세요";
    }

    @ResponseBody
    @GetMapping("/question")
    public Question question() {
        Question q = new Question();
        q.setId(1);
        q.setSubject("테스트");
        q.setContent("작성");
        q.setCreateDate(LocalDateTime.now());

        return q;
    }
}
