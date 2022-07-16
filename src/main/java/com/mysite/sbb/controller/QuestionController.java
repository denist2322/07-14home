package com.mysite.sbb.controller;

import com.mysite.sbb.dao.QuestionRepository;
import com.mysite.sbb.domain.AnswerForm;
import com.mysite.sbb.domain.Question;
import com.mysite.sbb.domain.QuestionForm;
import com.mysite.sbb.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/question")
public class QuestionController {
    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private QuestionService questionService;

    @RequestMapping("list")
    public String showList(Model model, @RequestParam(value="page", defaultValue = "0") int page) {
        Page<Question> paging = questionService.getList(page);
        model.addAttribute("paging", paging);
        return "usr/question/question_list";
    }

    @RequestMapping("detail/{id}")
    public String showDetail(@PathVariable("id") Integer id, Model model, AnswerForm answerForm) {
        Question question = this.questionService.getQuestion(id);
        model.addAttribute("question", question);
        return "usr/question/question_detail";
    }

    @GetMapping("/create")
    public String questionCreate(QuestionForm questionForm) {
        return "usr/question/question_form";
    }

//    @PostMapping("/create")
//    public String questionCreate(@RequestParam String subject, @RequestParam String content) {
//        this.questionService.create(subject, content);
//        return "redirect:/question/list";
//    }

    @PostMapping("/create")
    public String questionCreate(@Valid QuestionForm questionForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "usr/question/question_form";
        }
        this.questionService.create(questionForm.getSubject(), questionForm.getContent());
        return "redirect:/question/list";
    }
}
