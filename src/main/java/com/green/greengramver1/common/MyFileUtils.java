package com.green.greengramver1.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.SimpleTimeZone;
import java.util.SplittableRandom;
import java.util.UUID;

@Slf4j
@Component // 빈등록
// 빈등록은 스프링컨테이너에가 객체등록을 맞김(new 작업을 스프링컨테이너에서 해주는것)
// 싱글톤이라 하나만 만들어서 부를때마다 같은 주소값을 주는것
public class MyFileUtils {
    private final String uploadPath;

    public MyFileUtils(@Value("${file.directory}") String uploadPath) {
        // yaml에서 설정한 경로를 uploadPath에 넣어주는것
        log.info("MFU 생성자: {}", uploadPath);
        this.uploadPath = uploadPath;
    }
    public String makeFolder(String path){
        File file = new File(uploadPath, path);
        file.mkdirs();
        return file.getAbsolutePath();
    }

    // 파일명에서 확장자 추출
    public String getExt(String fileName){
        int lastIdx = fileName.lastIndexOf(".");
        return fileName.substring(lastIdx);
        // .의 위치를 찾고 거기서부터 끝까지의 문자를 반환
    }
    // 랜덤 파일명 생성
    public String makeRandomFileName(){
        return UUID.randomUUID().toString();
    }
    // 랜덤파일명 + 확장자 생성
    public String makeRandomFileName(String originalFileName){
//        int lastIdx = originalFileName.lastIndexOf(".");
//        String a = originalFileName.substring(lastIdx);
//        String b = UUID.randomUUID().toString();
//        String c = a + b;
//        어쳐피 위에서 다 작성해둔것이니 호출만 했으면 됬다.

        return makeRandomFileName()+getExt(originalFileName);
    }
    public String makeRandomFileName(MultipartFile file){
        return makeRandomFileName(file.getOriginalFilename());
    }

    //파일을 원하는 경로에 저장
    public void transferTo(MultipartFile mf, String path) throws IOException{
        File file = new File(uploadPath, path);
        // File 클래스와 new File 로 생성자를 이용하여 uploadPath와 Path 로 나눠진 경로를 합쳐줌
        mf.transferTo(file); // MultipartFile 의 메소드
    }
}

class Test{
    public static void main(String[] args) {
        MyFileUtils mf = new MyFileUtils("C:\\Users\\jin\\Desktop\\test");
        String rfn = mf.makeRandomFileName("15379_2468.jpg");
        System.out.println(rfn);
    }
}