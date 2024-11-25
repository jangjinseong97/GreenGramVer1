package com.green.greengramver1.user;

import com.green.greengramver1.common.model.ResultResponse;
import com.green.greengramver1.user.model.UserSignInReq;
import com.green.greengramver1.user.model.UserSignInRes;
import com.green.greengramver1.user.model.UserSignUpReq;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("user")
@Tag(name = "유저", description = "회원가입, 로그인")
public class UserController {
    private final UserService service;

    @PostMapping("sign-up")
    @Operation(summary = "회원 가입")
    public ResultResponse<Integer> signUp(@RequestPart UserSignUpReq p
                                        , @RequestPart(required = false) MultipartFile pic){
        // 파일 업로드시 RequestBody 사용 불가능
        // RequestPart 를 사용해야 된다.
        // MultipartFile 은 파일을 받아오는것
        // 따라서 위의 경우는 파일+데이터가 넘어와서 RequestPart 사용
        // 파일이 여러개라면 당연히 List<MultipartFile> 로 pic을 받으면 됨
//        log.info("userins: {},file: {}", p, pic.getOriginalFilename());
        log.info("UserInsReq: {}, file: {}", p, pic != null ? pic.getOriginalFilename() : null);
        int result = service.postSignUp(pic, p);
        return ResultResponse.<Integer>builder().resultMessage("회원가입 완료")
                .resultData(result).build();
    }
    @PostMapping("sign-in") // 아이디 비번이 보이면 안되서 쿼리스트링으로 보내면 안됨
    @Operation(summary = "로그인")
    public ResultResponse<UserSignInRes> signIn(@RequestBody UserSignInReq p){
        UserSignInRes res = service.postSignIn(p);

        return ResultResponse.<UserSignInRes>builder().resultMessage(res.getMessage())
                .resultData(res).build();
    }
}
