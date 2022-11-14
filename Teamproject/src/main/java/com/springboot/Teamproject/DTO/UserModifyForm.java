package com.springboot.Teamproject.DTO;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Size;

@Getter
@Setter
public class UserModifyForm {

    private String id;      //아이디

    @Size(min = 8, max = 15)
    private String beforePassword;   //기존 비밀번호

    @Size(min = 8, max = 15)
    private String afterPassword;   //변경할 비밀번호

    private String nickname;    //닉네임
}
