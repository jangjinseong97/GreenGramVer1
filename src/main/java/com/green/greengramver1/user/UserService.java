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
//        String savedPicName = myFileUtils.makeRandomFileName(pic);
        // pic는 MultipartFile 이걸 string 으로 변환 해야되는건가?
        // makeRadonFileName 메소드를 오버로딩하여 추가로 MultipartFile을 받아도 작동하도록
        // 하나 더 작성해주는것이 정답
//        myFileUtils.makeFolder("abc/def");

        // 위에껀 pic에 null값이 들어가면 작동x
        String savedPicName = pic != null ? myFileUtils.makeRandomFileName(pic) : null;
//      그래서 pic에 null이 들어갈 경우도 삼항식으로 만들어 줘야 된다.

        String hashedPassword = BCrypt.hashpw(p.getUpw(), BCrypt.gensalt());
        // 위가 암호화
        log.info("hashedPassword:{} ", hashedPassword);
        p.setUpw(hashedPassword); // 위에서 암호화 한걸로 set
        p.setPic(savedPicName); // 사진을 랜덤pic으로 바꾼걸로 set

        int res = mapper.insUser(p);
        // 해주지 않으면 새로 호출이 되지 않아 p.getUserId();를 하여도 기존에 있던 값인 0이 반환
        // 새로 지정해준다면 호출됨으로 인해 xml문에서 insert가 실행되고 거기서 pk값을 p객체의 userId에 받아 저장하게 되는것

        if(pic == null){
            return res;
        } // pic이 null인 경우 아래의 상황을 거치지 않고 리턴해주기 위해

        long userId = p.getUserId();
        // userId가 없었으므로 Req에 추가(이 주석을 달기전엔 없었다)
        // 이후 xml에 값을 받아오겠다는 명령어를 안적었었으므로
        // useGeneratedKey 와 keyProperty 설정


        // user/${userId}/${savedPicName} 라는 url을 주기 위하여
        String middlePath = String.format("user/%d",userId);
        // user/? ?가 userId에 따라 바뀌는 경로를 새로만듬
        myFileUtils.makeFolder(middlePath);
        // 위에서 만든 경로에 맞는 폴더 생성
        log.info("middlePath: {}",middlePath);
        String filePath = String.format("%s/%s",middlePath,savedPicName);
        // 위에서 만든 경로/파일(확장자와 이름은 savedPicName에서 다 결정함)
        try{
            myFileUtils.transferTo(pic,filePath);
            // 그걸 transferTo를 이용하여 그 pic을 보냄
        } catch (IOException e) {
            e.printStackTrace();
        }
        return res;

    }
}
