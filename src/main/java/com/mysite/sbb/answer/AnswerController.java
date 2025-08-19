package com.mysite.sbb.answer;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@RequiredArgsConstructor
@Controller
public class AnswerController {
    private final AnswerService answerService;

    @PostMapping("/answer/create")
    @ResponseBody
    public String create(@RequestParam("content") String content) {
        answerService.create(content);

        return "등록성공";
    }
}
