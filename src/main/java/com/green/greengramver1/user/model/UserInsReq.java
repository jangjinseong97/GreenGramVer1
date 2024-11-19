package com.green.greengramver1.user.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserInsReq {
    private String uid;
    private String upw;
    private String nickName;
    private String pic;
}
