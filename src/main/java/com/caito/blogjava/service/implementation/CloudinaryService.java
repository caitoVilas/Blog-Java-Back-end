package com.caito.blogjava.service.implementation;

import com.caito.blogjava.constatnts.ConstantExeptionMessages;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class CloudinaryService {
    Cloudinary cloudinary;
    Map<String, String> valuesMap = new HashMap<>();

    public CloudinaryService(){
        valuesMap.put("cloud_name", "ddkneaqfw");
        valuesMap.put("api_key", "481589333419226");
        valuesMap.put("api_secret", "-saEIDeX0hCIwROn1hfXLKp2K6Y");
        cloudinary = new Cloudinary(valuesMap);
    }

    public Map upload(MultipartFile multipartFile) throws IOException {
        BufferedImage bi = ImageIO.read(multipartFile.getInputStream());
        if (bi == null){
            throw new IOException(ConstantExeptionMessages.MSG_FILE_NOT_IMAGE);
        }
        File file = convert(multipartFile);
        Map result = cloudinary.uploader().upload(file, ObjectUtils.emptyMap());
        file.delete();
        return result;
    }

    public Map delete(String id) throws IOException {
        Map result = cloudinary.uploader().destroy(id, ObjectUtils.emptyMap());
        return result;
    }

    private File convert(MultipartFile multipartFile) throws IOException {
        File file = new File(multipartFile.getOriginalFilename());
        FileOutputStream fo = new FileOutputStream(file);
        fo.write(multipartFile.getBytes());
        fo.close();
        return file;
    }
}
