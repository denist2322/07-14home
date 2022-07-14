package com.mysite.sbb.controller;

import com.mysite.sbb.dao.ArticleRepository;
import com.mysite.sbb.domain.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/usr/article")
public class ArticleController {
    @Autowired
    private ArticleRepository articleRepository;

    @RequestMapping("/test")
    @ResponseBody
    public String testFunc() {
        return "test";
    }

    @RequestMapping("/list")
    @ResponseBody
    public List<Article> showArticleList(String title, String body) {
        if (title != null && body == null) {
            System.out.println(title);
            System.out.println(body);
            if (articleRepository.existsByTitle(title) == false) {

                System.out.println("제목과 일치하는 게시물이 없습니다.");
                return null;
            }
            return articleRepository.findByTitle(title);

        } else if (title == null && body != null) {
            System.out.println(title);
            System.out.println(body);
            if (articleRepository.existsByBody(body) == false) {

                System.out.println("내용과 일치하는 게시물이 없습니다.");
                return null;
            }
            return articleRepository.findByBody(body);

        } else if (title != null && body != null) {
            System.out.println(title);
            System.out.println(body);
            if (articleRepository.existsByTitleAndBody(title, body) == false) {

                System.out.println("제목, 내용과 일치하는 게시물이 없습니다.");
                return null;
            }
            return articleRepository.findByTitleAndBody(title, body);
        }
        System.out.println(title);
        System.out.println(body);
        return articleRepository.findAll();
    }


    @RequestMapping("/detail1")
    @ResponseBody
    public Article showDetail(int id) {
        List<Article> articles = articleRepository.findAll();
        Article article = articles.get(id);
        return article;
    }

    //    @RequestMapping("/detail")
//    @ResponseBody
//    public Article showDetail() {
//        return articleRepository.findById(1L).get();
//    }
//
    @RequestMapping("/detail2")
    @ResponseBody
    public Article showDetail2(@RequestParam Long id) {
        return articleRepository.findById(id).get();
    }

    @RequestMapping("/detail3")
    @ResponseBody
    public Article showDetail3(@RequestParam Long id) {
        Optional<Article> article = articleRepository.findById(id);
        return article.orElse(null);
    }

    @RequestMapping("/doModify")
    @ResponseBody
    public String articleModify(@RequestParam Long id, String title, String body) {
        Article article = articleRepository.findById(id).get();

        if (article.getTitle() == null) {
            return "제목 없다.";
        }

        if (article.getBody() == null) {
            return "내용 없다.";
        }

        article.setTitle(title);
        article.setBody(body);
        article.setUpdateDate(LocalDateTime.now());
        articleRepository.save(article);

        return "수정 됐다.";

    }

    @RequestMapping("/doDelete")
    @ResponseBody
    public String articleModify(@RequestParam Long id) {

        if (!articleRepository.existsById(id)) {
            return "게시물 없어유";
        }

        Article article = articleRepository.findById(id).get();

        articleRepository.delete(article);

        return "없어짐.";
    }

    @RequestMapping("/findByTitle")
    @ResponseBody
    public List<Article> findByTitle(String title) {
        List<Article> articles = articleRepository.findByTitle(title);

        return articles;
    }


    @RequestMapping("/doWrite")
    @ResponseBody
    public String doWrite(String title, String body) {
        if (title == null) {
            return "제목이 없어유";
        }

        if (body == null) {
            return "제목이 없어유";
        }

        Article article = new Article();
        article.setRegDate(LocalDateTime.now());
        article.setUpdateDate(LocalDateTime.now());
        article.setTitle(title);
        article.setBody(body);
        article.setUserId(1L);

        articleRepository.save(article);

        return "%d번 게시물이 생성되었어유".formatted(article.getId());
    }
}
