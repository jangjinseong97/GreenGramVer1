package com.green.greengramver1.feed;

import com.green.greengramver1.common.model.ResultResponse;
import com.green.greengramver1.feed.model.FeedPostReq;
import com.green.greengramver1.feed.model.FeedPostRes;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("feed")
@RestController
@Tag(name = "피드")
public class FeedController {
    private final FeedService service;

    @PostMapping
    public ResultResponse<FeedPostRes> postFeed(@RequestPart List<MultipartFile> pics
                                            , @RequestPart FeedPostReq p) {
        FeedPostRes res = service.postFeed(pics, p);
        return ResultResponse.<FeedPostRes>builder().resultMessage("ㅇ")
                .resultData(res).build();

    }
}
