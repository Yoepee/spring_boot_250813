package com.mysite.sbb.question;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/question")
@RequiredArgsConstructor
@Controller
public class QuestionController {
    private final QuestionService questionService;

    @GetMapping("/list")
    public String list(Model model){
        List<Question> questionList =  questionService.getList();

        model.addAttribute("questionList", questionList);

        return "question_list";
    }

    @GetMapping("/detail/{id}")
    public String detail(Model model, @PathVariable Integer id) {
        Question question = questionService.getQuestion(id);

        model.addAttribute("question", question);
        return "question_detail";
    }

    @GetMapping("/create")
    public String create(Model model) {
        return "question_form";
    }

    @PostMapping("/create")
    public String createQuestion(@RequestParam("subject") String subject, @RequestParam("content") String content) {
        Question question = questionService.create(subject, content);

        return "redirect:/question/detail/%d".formatted(question.getId());
    }
}
