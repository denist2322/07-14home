package com.mysite.sbb.controller;
// 유저 컨트롤러
import com.mysite.sbb.domain.UserCreateForm;
import com.mysite.sbb.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;


@Controller
@RequestMapping("/user")
public class UserController {
    // 유저 서비스와 연결
    @Autowired
    private UserService userService;
    // 회원가입 맵핑을 받았을 때 진행 (회원가입 폼으로 안내해줌)
    @GetMapping("/signup")
    // ※ 매개변수를 객체로 받는경우 자동으로 model이 진행되어 폼에서 바로 사용가능하다. (따로 Model.addAttribute를 하지 않아도 됨)
    public String signup(UserCreateForm userCreateForm) {
        return "usr/user/signup_form.html";
    }
    // 회원가입 폼에서 정보를 받았을 때 진행 (홰원가입을 진행함)
    @PostMapping("/signup")
    // @Valid는 유효성 검사. 유효성 검사에 대한 결과를 bindingResult에 준다.
    public String signup(@Valid UserCreateForm userCreateForm, BindingResult bindingResult) {
        // 유효성 검사에 에러가 있는 경우 회원가입 폼으로 다시 돌아가 에러 메시지를 알려준다.
        if (bindingResult.hasErrors()) {
            return "usr/user/signup_form.html";
        }
// NotEmpty를 UserCreateForm에 선언하지 않았을 경우 다음과 같이 조건을 달아서 검사가 가능하다.

//        if(userCreateForm.getPassword1() == null || userCreateForm.getPassword1().trim().length() == 0){
//            bindingResult.reject("signupFailed", "비밀번호는 필수 기입 입니다.");
//            return "usr/user/signup_form.html";
//        }

//        if(userCreateForm.getPassword1() == null || userCreateForm.getPassword1().trim().length() == 0){
//            bindingResult.reject("signupFailed", "비밀번호확인은 필수 기입 입니다.");
//            return "usr/user/signup_form.html";
//        }

        // 비밀번호와 비밀번호 확인이 일치하지 않는다면 에러 표시
        if (!userCreateForm.getPassword1().equals(userCreateForm.getPassword2())) {
            /*
            bingingResult에 에러 메시지를 담는 과정으로 passwordInCorrect라는 에러 코드 발생시 2개의 패스워드가 일치하지 않습니다.를 표시함.
            field는 생략 가능. 밑에있는 bindingResult.reject랑 같은 문법 차이점은 field 유무로 field는 어떤 변수에서 에러가 발생했는지 나타냄.
             */
            bindingResult.rejectValue("password1","passwordInCorrect","2개의 패스워드가 일치하지 않습니다.");
            return "usr/user/signup_form.html";
        }

        /*
        if대신 try로 한 이유 : 정상적으로 값을 받았을 경우 일단 시도를 해봐야 중복에러가 있는지 다른에러가 있는지 혹은 정상인지 알 수 있기 때문
        생성을 먼저 시도함 생성할때 (유저이름, 유저이메일, 유저 비밀번호)를 전달함.
         */
        try {
        userService.create(userCreateForm.getUsername(),
                userCreateForm.getEmail(), userCreateForm.getPassword1());
        }
        // 데이터 중복이 발생했을 경우 들어가는 예외 구문
        catch(DataIntegrityViolationException e) {
            // 에러메시지를 추적해서 출력해줌 (백엔드에서 보기 위함임)
            e.printStackTrace();
            // 위에 bindingResult.rejectValue와 같음.
            bindingResult.reject("signupFailed", "이미 등록된 사용자입니다.");
            return "usr/user/signup_form.html";
        }
        // 예사이 못한 에러가 발생했을 경우 들어가는 얘외 구문
        catch(Exception e) {
            // 에러메시지를 추적해서 출력해줌 (백엔드에서 보기 위함임)
            e.printStackTrace();
            // 위에 bindingResult.rejectValue와 같음. 출력 메시지로는 에러가 표시되는 메시지 그대로 출력함
            bindingResult.reject("signupFailed", e.getMessage());
            return "usr/user/signup_form.html";
        }

        return "redirect:/";
    }
    
    // 로그인 폼으로 가게 해줌
    @GetMapping("/login")
    public String login() {
        return "usr/user/login_form";
    }

}