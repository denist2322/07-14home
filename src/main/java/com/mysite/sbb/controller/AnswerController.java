package com.mysite.sbb.controller;

import com.mysite.sbb.dao.AnswerRepository;
import com.mysite.sbb.domain.Answer;
import com.mysite.sbb.domain.AnswerForm;
import com.mysite.sbb.domain.Question;
import com.mysite.sbb.service.AnswerService;
import com.mysite.sbb.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/answer")
public class AnswerController {

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private QuestionService questionService;

    @Autowired
    private AnswerService answerService;

    @RequestMapping("list")
    @ResponseBody
    public List<Answer> showList() {

        return answerRepository.findAll();
    }

    @PostMapping("create/{id}")
    public String createAnswer(Model model, @PathVariable("id") Integer id, @Valid AnswerForm answerForm, BindingResult bindingResult) {
        Question question = this.questionService.getQuestion(id);
        if(bindingResult.hasErrors()){
            model.addAttribute("question", question);
            return "/usr/question/question_detail";
        }

        this.answerService.create(question, answerForm.getContent());
        return String.format("redirect:/question/detail/%s", id);
    }

}
