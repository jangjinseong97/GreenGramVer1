package com.green.greengramver1.feed;

import com.green.greengramver1.common.model.Paging;
import com.green.greengramver1.common.model.ResultResponse;
import com.green.greengramver1.feed.model.FeedGetReq;
import com.green.greengramver1.feed.model.FeedGetRes;
import com.green.greengramver1.feed.model.FeedPostReq;
import com.green.greengramver1.feed.model.FeedPostRes;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("feed")
@RestController
@Tag(name = "피드")
public class FeedController {
    private final FeedService service; /// DI주입을 위해

    @PostMapping
    public ResultResponse<FeedPostRes> postFeed(@RequestPart  List<MultipartFile> pics
                                            , @RequestPart FeedPostReq p) {
        FeedPostRes res = service.postFeed(pics, p);
        return ResultResponse.<FeedPostRes>builder().resultMessage("ㅇ")
                .resultData(res).build();

    }
    @GetMapping
    public ResultResponse<List<FeedGetRes>> getFeedList(@ParameterObject FeedGetReq p) {
//        if(p.getSize()==0){
//            p.setSize(20);
//        }
//        >> paging 에 있으면 여기저기 쓰기 더 좋았을것

        // ModelAttribute 생략되어있는거 생각(쿼리스트링으로 받기위해)
        log.info("p: {}",p);
        List<FeedGetRes> list = service.getFeedList(p);
//        return new ResultResponse<>(String.format("%d row",list.size())
//                , list);
        return  ResultResponse.<List<FeedGetRes>>builder()
                .resultMessage(String.format("%d row", list.size()))
                .resultData(list).build();
    }
}
