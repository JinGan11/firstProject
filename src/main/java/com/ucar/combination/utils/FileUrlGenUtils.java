package com.ucar.combination.utils;

import org.springframework.web.multipart.MultipartFile;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class FileUrlGenUtils {
    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
    //文件根目录
    private static final String ROOT_DIR = "C:\\licenses\\";
    //随机数种子
    private static Random rnd = new Random();

    /**
     * desc:生成文件写路径后续 再考虑唯一性。。
     * @author qingyu.lan@ucarinc.com
     * @version 1.0
     * @param file
     * @return
     */
    public static String generateUrl(MultipartFile file)  {
        String fileName = file.getOriginalFilename();
        StringBuffer sb = new StringBuffer(ROOT_DIR);
        String fileType = fileName.substring(fileName.lastIndexOf(".") + 1);
        String dateTime = simpleDateFormat.format(new Date());
        sb.append("ucar").append(dateTime).append(rnd.nextInt(10000)).append(rnd.nextInt(999)).append("."+fileType);
        return sb.toString();
    }

}
