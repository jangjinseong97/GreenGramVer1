package com.green.greengramver1.feed.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(title = "피드 등록")
public class FeedPostReq {
    @Schema(title = "로그인 유저 PK")
    private long writerUserId; // user_id 의 fk 이므로
    @Schema(title = "내용")
    private String contents;
    @Schema(title = "글 위치")
    private String location;
    @JsonIgnore
    private long feedId;
}
