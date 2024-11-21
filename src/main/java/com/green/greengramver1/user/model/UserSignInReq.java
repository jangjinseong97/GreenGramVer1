package com.green.greengramver1.user.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Schema(title = "로그인")
public class UserSignInReq {
    @Schema(title = "아이디", example = "test2", requiredMode = Schema.RequiredMode.REQUIRED)
    private String uid;
    @Schema(title = "비밀번호", example = "121", requiredMode = Schema.RequiredMode.REQUIRED)
    private String upw;

//    @JsonIgnore
//    private long userId; // 로그인 이후 필요해서 있어야 되지 않을까?
    // 필요하긴한데 받는곳에서 필요하니 Res 에서 필요
    // 원래는 jwp에서 토큰(정보)을 받아서 사용한다고함
}
