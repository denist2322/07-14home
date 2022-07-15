package com.mysite.sbb.domain;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@Setter
public class QuestionForm {
    @NotEmpty(message="좋은말로 할 때 제목 넣어라 ㅡㅡ :)")
    @Size(max=200)
    private String subject;

    @NotEmpty(message="좋은말로 할 때 내용 넣어라 ㅡㅡ :)")
    private String content;

}
