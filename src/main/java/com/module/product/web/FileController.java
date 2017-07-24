package com.module.product.web;

import com.google.common.collect.Maps;
import com.module.core.common.Result;
import com.module.core.common.ResultGenerator;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

/**
 * Created by  on 2017/5/25.
 */
@RestController
@RequestMapping("/file")
public class FileController {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Value("${file.upload.server.origin}")
    private String origin;
    @Value("${file.upload.server.path}")
    private String realPath;
    @Value("${file.upload.size.max.image}")
    private int imageFileMaxSize;

    @PostMapping("/upload/image")
    public Result uploadImage(@RequestParam MultipartFile imageFile, @RequestParam(defaultValue = "images") String dirName) {
        if(StringUtils.isNotEmpty(validateImageFile(imageFile))){
            return ResultGenerator.genFailResult(validateImageFile(imageFile));
        }
        String dateDIR = DateFormatUtils.format(new Date(), "yyyyMMdd");
        String path = dirName + "/" + dateDIR + "/";
        // 为上传的文件进行重命名（避免同名文件的相互覆盖）使用UUID + 文件后缀
        String suffix = imageFile.getOriginalFilename().substring(imageFile.getOriginalFilename().lastIndexOf("."));
        String fileName = UUID.randomUUID().toString() + suffix;
        File file = new File(realPath + path + fileName);
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        //将临时文件保存到磁盘
        try {
            imageFile.transferTo(file);
        } catch (IOException e) {
            logger.error("文件上传失败", e);
             return ResultGenerator.genFailResult("上传失败：" + e.getMessage());
        }
        Map<Object, Object> data = Maps.newLinkedHashMap();
        data.put("url", origin + path + fileName);
        return ResultGenerator.genSuccessResult(data);
    }

    private String validateImageFile(MultipartFile imageFile){
        String error = "";
        //校验类型
        if (imageFile.getContentType().indexOf("image") == -1) {
            error = "上传失败，仅支持图片类型！";
        }
        if (imageFile.getSize() > (imageFileMaxSize * 1024 * 1024)) {
            error = "上传失败，文件大小不能超过" + imageFileMaxSize + "MB！";
        }
        return error;
    }

    /**
     * 上传图像裁剪预览
     * @param mv
     * @param imgUrl
     * @return
     */
    @GetMapping("/preview")
    public ModelAndView imagePreview(ModelAndView mv,String imgUrl) {
        mv.setViewName("preview_clip");
        mv.addObject("imgUrl",imgUrl);
        return  mv;
    }

    /**
     * 上传裁剪后的图像文件
     * @param imgUrl
     * @param x
     * @param y
     * @param w
     * @param h
     * @return
     */
    @RequestMapping("/clip")
    @ResponseBody
    public Result uploadClipImage(String dirName,String imgUrl,int x,int y,int w,int h){
        try{
            String suffix = imgUrl.substring(imgUrl.lastIndexOf(".")+1);
            String fileName = UUID.randomUUID().toString() +"."+ suffix ;
            String dateDIR =  DateFormatUtils.format(new Date(), "yyyyMMdd");
            String path = dirName + dateDIR + "/";
            String oldPath = imgUrl.replace(origin,realPath);
            String destPath = realPath + path +  fileName;

            cutImage(oldPath,destPath,x,y,w,h);

            Map<Object, Object> data = Maps.newLinkedHashMap();
            data.put("url", origin + path + fileName);
            return ResultGenerator.genSuccessResult(data);
        }catch (Exception e){
            e.printStackTrace();
            return ResultGenerator.genFailResult("图像裁剪失败");
        }
    }

    /**
     * 图片裁剪通用接口
     * src：图片位置，dest：图片保存位置
     * 若要覆盖原图片，只需src == dest即可
     * @param src
     * @param dest
     * @param x
     * @param y
     * @param w
     * @param h
     * @throws IOException
     */
    public void cutImage(String src,String dest,int x,int y,int w,int h) throws IOException{

        File srcImg =new File(src);
        //获取后缀名
        String suffix = srcImg.getName().substring(srcImg.getName().lastIndexOf(".") + 1);
        //根据不同的后缀获取图片读取器
        Iterator iterator = ImageIO.getImageReadersBySuffix(suffix);
        ImageReader reader = (ImageReader)iterator.next();

        InputStream in=new FileInputStream(src);
        ImageInputStream iis = ImageIO.createImageInputStream(in);

        reader.setInput(iis, true);
        ImageReadParam param = reader.getDefaultReadParam();

        //设置裁剪位置
        Rectangle rect = new Rectangle(x, y, w,h);
        param.setSourceRegion(rect);

        //保存裁剪后的图片
        BufferedImage bi = reader.read(0,param);
        ImageIO.write(bi, suffix, new File(dest));

    }

}
