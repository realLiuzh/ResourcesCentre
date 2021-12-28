package com.example;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author 志昊的刘
 * @date 2021/12/27
 */
@RestController
public class TestController {

    @RequestMapping("/test")
    public String test() {
        return "success";
    }


    @GetMapping("/file")
    public String reads() {
        return read("html");
    }

    @GetMapping("/file/{path}")
    public String read(@PathVariable(value = "path", required = false) String path) {
        if (path.length() == 0)
            path = "html";
        System.out.println();
        String imgSrc="<img src=\"http://47.96.86.132:1227/image/1.png\" height=500px weight=600px/>  <br><br>";
        return imgSrc+readFile("/" + path, 0);
    }

    public static String readFile(String path, int cengji) {
        StringBuilder space = new StringBuilder();
        for (int i = 0; i < cengji; i++) {
            space.append("|-------");
        }
        String dirName = path;
        File file = new File(dirName);
        StringBuilder stringBuilder = new StringBuilder();
        if (file.isDirectory()) {
            //stringBuilder.append("正在读取").append(dirName).append("目录....").append("\n");
            String[] list = file.list();
            Arrays.sort(list);
            assert list != null;
            for (String s : list) {
                File file2 = new File(dirName + File.separator + s);
                if (file2.isDirectory()) {
                    stringBuilder.append(space).append("文件夹：").append(s).append("<br>");
                    stringBuilder.append(space).append(readFile(dirName + File.separator + s, cengji + 1));
                } else {
                    getSrcPath(space, stringBuilder, s, dirName + File.separator + s);
                }
            }
        } else {
            stringBuilder.append(space).append(dirName).append("不是一个目录。").append("<br>");
        }
        return stringBuilder.toString();
    }

    private static void getSrcPath(StringBuilder space, StringBuilder stringBuilder, String s, String path) {
        stringBuilder.append(space).append("文件：").
                append(s).
                append("<a href=http://47.96.86.132:1227" + path + ">").
                append("✨").
                append("</a>").
                append("<br>");
    }

    @GetMapping("/add/{path}")
    public String addFiles(@PathVariable("path") String path, HttpServletRequest request) {
        path = path.replaceAll("-", "/");
        return addFile("/" + path + "/", request);
    }


    @PostMapping("/add")
    public String addFile(String filePath, HttpServletRequest request) {

        List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("file");
        for (int i = 0; i < files.size(); i++) {
            MultipartFile file = files.get(i);
            if (file.isEmpty()) continue;
            String fileName = file.getOriginalFilename();

            File file1 = new File(filePath);
            if (!file1.exists()){
                file1.mkdirs();
            }

            File dest = new File(filePath + fileName);
            System.out.println(fileName+"将上传到"+dest.getAbsolutePath()+"....");
            try {
                file.transferTo(dest);
                System.out.println("第" + (i + 1) + "个文件上传成功!");
            } catch (IOException e) {
                System.out.println("第" + (i + 1) + "个文件上传失败!");
                e.printStackTrace();
                System.out.println();
            }
        }
        return "success";
    }


/*    public static void main(String[] args) {
        System.out.println(readFile("C:\\Users\\志昊的刘\\Desktop\\temp",0));
    }*/


}
