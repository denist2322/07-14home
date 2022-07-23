package com.mysite.sbb.domain;
// 회원가입 폼
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@Setter
public class UserCreateForm {
    // 크기제한
    @Size(min = 3, max = 25)
    // 빈칸일 수 없음.
    @NotEmpty(message = "사용자ID는 필수항목입니다.")
    private String username;

    @NotEmpty(message = "비밀번호는 필수항목입니다.")
    private String password1;

    @NotEmpty(message = "비밀번호 확인은 필수항목입니다.")
    private String password2;

    @NotEmpty(message = "이메일은 필수항목입니다.")
    // 이메일로 제한함
    @Email
    private String email;
    /*
    여기에있는 어노테이션은 @Valid에 의해 유효성 검사가 가능하다.
    상세한 정보는 아래 참고
    [유효값과 예외 처리]
    https://wiken.io/ken/8816 위캔
    https://smhope.tistory.com/412 smhope (강추)
     */

}