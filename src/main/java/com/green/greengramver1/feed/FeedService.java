package com.green.greengramver1.feed;

import com.green.greengramver1.common.MyFileUtils;
import com.green.greengramver1.feed.model.*;
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
    public List<FeedGetRes> getFeedList(FeedGetReq p){
        List<FeedGetRes> list = mapper.selFeedList(p);
        // feedId를 가지기 위하여 여기선 feedId 가 순서대로 나옴(최신순)

        for(FeedGetRes r : list){
            List<String> listPics = mapper.selFeedPicList(r.getFeedId());
            // 위에서 list에서의 feedId를 사용하여 selFeedPicList 쿼리문 실행(where에서 쓰임)
            r.setPics(listPics);
            // r.setPics 로 FeedGetRes 객체에 리스트를 저장(List<String pics)에
        }

        return list;
//        return mapper.selFeedList(p);
    }
}
