package com.green.greengramver1.user.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserSignUpReq {
    @Schema(description = "아이디", requiredMode = Schema.RequiredMode.REQUIRED)
    private String uid;
    @Schema(description = "비밀번호", requiredMode = Schema.RequiredMode.REQUIRED)
    private String upw;
    @Schema(description = "닉네임")
    private String nickName;
    @JsonIgnore
    private String pic;
    @JsonIgnore
    private long userId;
    //xml 에 작성한 keyProperty="userId"로 인하여 값을 받아오기 위해
}
