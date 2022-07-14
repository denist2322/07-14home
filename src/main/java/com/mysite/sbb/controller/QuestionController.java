package com.mysite.sbb.controller;

import com.mysite.sbb.dao.QuestionRepository;
import com.mysite.sbb.domain.Question;
import com.mysite.sbb.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/question")
public class QuestionController {
    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private QuestionService questionService;

    @RequestMapping("list")
    public String showList(Model model){
        List<Question> questions = questionService.getList();
        model.addAttribute("questions",questions);
        return "usr/question/question_list";
    }

    @RequestMapping("detail/{id}")
    public String showDetail(@PathVariable("id") Integer id, Model model){
        Question question = this.questionService.getQuestion(id);
        model.addAttribute("question", question);
        return "usr/question/question_detail";
    }
}
