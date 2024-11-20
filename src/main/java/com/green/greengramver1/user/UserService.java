package com.green.greengramver1.user;

import com.green.greengramver1.common.MyFileUtils;
import com.green.greengramver1.user.model.UserInsReq;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserMapper mapper;
    private final MyFileUtils myFileUtils;
    public int postSignUp(MultipartFile pic, UserInsReq p){
//        String savedPicName = myFileUtils.makeRandomFileName(pic.getOriginalFilename());
        String savedPicName = myFileUtils.makeRandomFileName(pic);
        // pic는 MultipartFile 이걸 string 으로 변환 해야되는건가?
        // makeRadonFileName 메소도를 오버로딩하여 추가로 MultipartFile을 받아도 작동하도록
        // 하나 더 작성해주는것이 정답
//        myFileUtils.makeFolder("abc/def");

        String hashedPassword = BCrypt.hashpw(p.getUpw(), BCrypt.gensalt());
        log.info("hashedPassword:{} ", hashedPassword);
        p.setUpw(hashedPassword); // 위에서 암호화 한걸로 set
        p.setPic(savedPicName); // 사진을 랜덤pic으로 바꾼걸로 set

        // 위가 암호화
        int res = mapper.insUser(p);
        long userId = p.getUserId();// userId가 없었으므로 Req에 추가
        // 이후 xml에 값을 받아오겠다는 명령어를 안적었었으므로
        // useGeneratedKey 와 keyProperty 설정
        // user/${userId}/${savedPicName}
        String middlePath = String.format("user/%d",userId);
        // user/1 로 경로를 새로만듬
        myFileUtils.makeFolder(middlePath);
        // 위에서 만든 경로에 맞는 폴더 생성
        log.info("middlePath: {}",middlePath);
        String filePath = String.format("%s/%s",middlePath,savedPicName);
        // 위에서 만든 경로/랜덤이름+확장자(pic의 확장자) 여기서 부터 애매함
        try{
            myFileUtils.transferTo(pic,filePath);
            // 그걸 transferTo를 이용하여 보냄?
        } catch (IOException e) {
            e.printStackTrace();
        }
        return res;

    }
}
