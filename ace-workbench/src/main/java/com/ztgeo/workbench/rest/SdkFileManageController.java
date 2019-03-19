package com.ztgeo.workbench.rest;

import com.github.wxiaoqi.security.auth.client.annotation.CheckClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Slf4j
@RestController
//@CheckClientToken
//@CheckUserToken
@RequestMapping("/sdkFileManage")
public class SdkFileManageController {


    @Value("${customAttributes.sdkFilePath}")
    private String sdkFilePath;
    @Value("${customAttributes.sdkSourceFilePath}")
    private String sdkSourceFilePath;
    @Value("${customAttributes.sdkDemoFilePath}")
    private String sdkDemoFilePath;

    /**
     * 下载sdk文件
     *
     * @param typeId 类型ID
     */
    @RequestMapping(value = "downloadSDKFile/{typeId}", method = RequestMethod.GET)
    public void downloadSDKFile(@PathVariable("typeId") String typeId, HttpServletResponse response) {
        try {
            InputStream in = null;
            if ("1".equals(typeId)) { // jar包
                log.info("下载sdk包,路径:" + sdkFilePath);
                Path path = Paths.get(sdkFilePath);
                in = Files.newInputStream(path);
                response.setContentType("application/octet-stream");
                response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode("ztgeo-sdk.jar", "UTF-8"));
            } else if ("2".equals(typeId)) { // 源码
                log.info("下载sdk源码包,路径:" + sdkSourceFilePath);
                Path path = Paths.get(sdkSourceFilePath);
                in = Files.newInputStream(path);
                response.setContentType("application/octet-stream");
                response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode("ztgeo-sdk-source.zip", "UTF-8"));
            } else if ("3".equals(typeId)) { // demo
                log.info("下载demo项目,路径:" + sdkDemoFilePath);
                Path path = Paths.get(sdkDemoFilePath);
                in = Files.newInputStream(path);
                response.setContentType("application/octet-stream");
                response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode("ztgeo-sdk-demo.zip", "UTF-8"));
            }
            // 将本地文件发送给客户端
            OutputStream out = response.getOutputStream();
            byte[] buffer = new byte[1024];
            int len = 0;
            while ((len = in.read(buffer)) != -1) {
                out.write(buffer, 0, len);
            }
            in.close();
            out.flush();
            out.close();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
