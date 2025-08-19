package com.mysite.sbb.question;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.ui.Model;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class QuestionController {
    private final QuestionRepository questionRepository;

    @GetMapping("/question/list")
    @ResponseBody
    public String list(Model model){
        System.out.println(model);
        List<Question> questionList =  questionRepository.findAll();

        model.addAttribute("questionList", questionList);

        return "question_list";
    }
}
