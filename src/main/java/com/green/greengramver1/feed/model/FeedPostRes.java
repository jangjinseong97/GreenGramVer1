package com.green.greengramver1.feed.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.List;

@Getter
@Setter
public class FeedPostRes {
    // feed의 pk 값과 파일 이름 여러개를 리턴할 수 있어야 함.
    private long feedId;
    private List<String> pics;
}
