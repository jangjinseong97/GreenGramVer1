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
        // feed_pics에 넣기 위해 feedId 넣기

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
        // 리턴 값을 위한 FeedPostRes 만들기
        // feedId를 받아오고 거기에 들어갈 사진들의 배열
        for(int i=0;i<pics.size();i++){
            String a = mf.makeRandomFileName(pics.get(i));
            String b = String.format("%s/%s",middlePath,a);
            try {
                mf.transferTo(pics.get(i), b);
                // 파일을 하나씩 폴더에 저장
            } catch (IOException e) {
                e.printStackTrace();
            }
            res.getPics().add(a);
            //pics는 List<MultipartFile> 타입이므로 for문이 돌 때마다
            // 위의 res.getPics().add(a); 로 내부에 파일을 하나하나씩 넣게 되는 것

            feedPicDto.setPic(a);
            // for문으로 사진을 하나 넣을때 마다 feed_pics에 따로 자료를 저장
            // 위에서 돌때마다 바뀌는 a를 넣고 그걸 아래의 mapper 로 매번 저장해주는 것
            mapper.insFeedPic(feedPicDto);
        }
        // 위의 for문이 끝나면 res에는 이미 feedId값과 그 값에 따른 사진들이 저장되어 있으니 그걸 리턴
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
        } // for문으로 각 feedId마다 사진을 불러와서(listPics) 넣는것을 반복(setPics)
        // 일단은 mapper를 여러번 사용하는식(야메로 만든 느낌)

        return list;
//        return mapper.selFeedList(p);
    }
}
