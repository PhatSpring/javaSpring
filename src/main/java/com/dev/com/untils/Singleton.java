/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dev.com.untils;

import com.cloudinary.Cloudinary;
import java.util.HashMap;
import java.util.Map;
import org.springframework.context.annotation.Bean;

/**
 *
 * @author Admin
 */
public class Singleton {

    private static Cloudinary cloudinary;
    private static final String CLOUDINARY_CLOUD_NAME = "sai-gon";
    private static final String CLOUDINARY_API_KEY = "123351383481558";
    private static final String CLOUDINARY_API_SECRET = "qyfK6p5ctZpqC4975ORJb2ewjaw";

    @Bean
    public static Cloudinary getCloudinary() {
        Cloudinary cloudinary = null;
        Map config = new HashMap();
        config.put("cloud_name", CLOUDINARY_CLOUD_NAME);
        config.put("api_key", CLOUDINARY_API_KEY);
        config.put("api_secret", CLOUDINARY_API_SECRET);
        cloudinary = new Cloudinary(config);
        return cloudinary;
    }
}
