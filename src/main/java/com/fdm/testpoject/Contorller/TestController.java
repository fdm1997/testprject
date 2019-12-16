package com.fdm.testpoject.Contorller;
import java.io.*;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.UUID;

/**
 * @author fdm
 * @date 2019/8/1 16:08
 * @Description:
 */
@RestController
public class TestController {
    @RequestMapping("/test")
    public String te(){
        return "test";

    }

    @PostMapping("/upload")
    public String uploadSingeFile(@RequestParam("files") List<MultipartFile> files,String user) throws Exception {

        for ( MultipartFile file: files) {
            System.out.println("文件名："+file.getName());
            System.out.println("文件大小："+file.getSize()/(1024)+"K");
            file.getOriginalFilename();
            String fomat = file.getOriginalFilename().substring(file.getOriginalFilename().indexOf(".")+1,file.getOriginalFilename().length());
            System.out.println("文件格式："+fomat);

            String fileName = UUID.randomUUID()+"."+fomat;
            String path = "E://"+fileName;
            File file1 = new File(path);
            InputStream is = file.getInputStream();
            OutputStream os = new FileOutputStream(file1);
            byte [] buffer = new byte [1024];
            while (is.read(buffer) != -1){
                os.write(buffer); 
            }

        }


        System.out.println("user:"+user);

        return "成功";
    }

    @PostMapping("/uploadfile")
    public String uploadFile(HttpServletRequest req){
        List<MultipartFile> files = ((MultipartHttpServletRequest) req).getFiles("file");

        int i = 1;
        for (MultipartFile file: files) {
            System.out.println(i+".文件名："+file.getName());
            System.out.println(i+".文件大小："+file.getSize());
            System.out.println(i+".文件格式："+file.getContentType());
            i++;
        }

        System.out.println("用户参数:"+req.getParameter("user"));

        return "成功";
    }

}
