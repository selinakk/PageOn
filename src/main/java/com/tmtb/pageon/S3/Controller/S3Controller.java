package com.tmtb.pageon.S3.Controller;

import com.tmtb.pageon.S3.Service.S3Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class S3Controller {

    private static final Logger log = LoggerFactory.getLogger(S3Controller.class);
    @Autowired
    S3Service s3Service;

    @GetMapping("/s3")
    String s3FileUploadtest(){
        return "test.html";
    }

    @GetMapping("/presigned-url")
    @ResponseBody
    String getURL(@RequestParam String filename){
        var result = s3Service.createPresignedUrl("img/" + filename);
        log.info("URL: " + result);
        // 쿼리 파라미터 제거
        String cleanUrl = result.split("\\?")[0];
        log.info("Clean URL: " + cleanUrl);

        return result;
    }


}
