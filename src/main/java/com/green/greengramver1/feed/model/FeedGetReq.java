package com.green.greengramver1.feed.model;

import com.green.greengramver1.common.model.Paging;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class FeedGetReq extends Paging {

    public FeedGetReq(Integer page, Integer size) {

//        super(page, size == 0 ? 20 : size);
        super(page, size);
    } // paging 에 기본생성자가 없어서 빨간줄이 그였던것
    // 따라서 paging에 기본생성자를 넣던지 여기에 paging에 있는 생성자와 같은 파라미터를
    // 가진 생성자를 넣어야 밑줄 x
}
