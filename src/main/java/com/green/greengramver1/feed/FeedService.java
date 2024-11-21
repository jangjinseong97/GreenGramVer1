package com.green.greengramver1.feed;

import com.green.greengramver1.common.MyFileUtils;
import com.green.greengramver1.feed.model.FeedPicDto;
import com.green.greengramver1.feed.model.FeedPostReq;
import com.green.greengramver1.feed.model.FeedPostRes;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class FeedService {
    private final FeedMapper mapper;
    private final MyFileUtils mf;
    public FeedPostRes postFeed(List<MultipartFile> pics, FeedPostReq p){
        int result = mapper.insFeed(p);

        //파일 저장
//        String middlePath = String.format("feed/%d",p.getFeedId());
        // feedId 를 사용할 장소가 많을 수도 있으니 따로 외부에 저장하는것도 좋음
        long feedId = p.getFeedId();
        String middlePath = String.format("feed/%d",feedId);

        //위치: feed/${feedId}/

        mf.makeFolder(middlePath);
        FeedPicDto feedPicDto = new FeedPicDto();
        feedPicDto.setFeedId(feedId);

//        FeedPostRes res = new FeedPostRes(); //
//        res.setFeedId(feedId); //
//        List<String> list = new ArrayList<>(); //
//        for(int i=0;i<pics.size();i++){
//            String a = mf.makeRandomFileName(pics.get(i));
//            String b = String.format("%s/%s",middlePath,a);
//            try {
//                mf.transferTo(pics.get(i), b);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            list.add(a); //
//
//            feedPicDto.setPic(a);
//            mapper.insFeedPic(feedPicDto);
//        }
//        res.setPics(list); //

        FeedPostRes res = new FeedPostRes();
        res.setFeedId(feedId);
        res.setPics(new ArrayList<>());
        for(int i=0;i<pics.size();i++){
            String a = mf.makeRandomFileName(pics.get(i));
            String b = String.format("%s/%s",middlePath,a);
            try {
                mf.transferTo(pics.get(i), b);
            } catch (IOException e) {
                e.printStackTrace();
            }
            res.getPics().add(a);

            feedPicDto.setPic(a);
            mapper.insFeedPic(feedPicDto);
        }

        return res;
    }
}
