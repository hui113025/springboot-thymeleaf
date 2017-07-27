package com.module.product.common;

import com.google.code.kaptcha.GimpyEngine;

import java.awt.image.BufferedImage;

/**
 * Created by zhenghui on 2016/10/31.
 */
public class BlankGimpy implements GimpyEngine {
    @Override
    public BufferedImage getDistortedImage(BufferedImage bufferedImage) {
        return bufferedImage;
    }
}
