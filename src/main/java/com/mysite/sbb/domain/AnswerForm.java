package com.mysite.sbb.domain;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class AnswerForm {
    @NotEmpty(message = "내용입력 해라 ㅡㅡ :)")
    private String content;
}
