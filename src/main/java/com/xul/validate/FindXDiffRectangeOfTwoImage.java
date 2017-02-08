package com.xul.validate;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;

import javax.imageio.ImageIO;

public class FindXDiffRectangeOfTwoImage {
	public synchronized static int findXDiffRectangeOfTwoImage(List<BufferedImage> imageList,int pianyiNum) {
        try {
            BufferedImage image1 = imageList.get(0);
            BufferedImage image2 = imageList.get(1);
            int width1 = image1.getWidth();
            int height1 = image1.getHeight();
            int width2 = image2.getWidth();
            int height2 = image2.getHeight();

            if (width1 != width2) return -1;
            if (height1 != height2) return -1;

            int left = 0;
            /**
             * ´Ó×óÖÁÓÒÉ¨Ãè
             */
            boolean flag = false;
            for (int i = 0; i < width1; i++) {
                for (int j = 0; j < height1; j++)
                    if (isPixelNotEqual(image1, image2, i, j)) {
                        left = i;
                        flag = true;
                        break;
                    }
                if (flag) break;
            }
            return left-pianyiNum;
        } catch (Exception ex) {
            ex.printStackTrace();
            return -1;
        }
    }

    private static boolean isPixelNotEqual(BufferedImage image1, BufferedImage image2, int i, int j) {
        int pixel1 = image1.getRGB(i, j);
        int pixel2 = image2.getRGB(i, j);

        int[] rgb1 = new int[3];
        rgb1[0] = (pixel1 & 0xff0000) >> 16;
        rgb1[1] = (pixel1 & 0xff00) >> 8;
        rgb1[2] = (pixel1 & 0xff);

        int[] rgb2 = new int[3];
        rgb2[0] = (pixel2 & 0xff0000) >> 16;
        rgb2[1] = (pixel2 & 0xff00) >> 8;
        rgb2[2] = (pixel2 & 0xff);

        for (int k = 0; k < 3; k++)
            if (Math.abs(rgb1[k] - rgb2[k]) > 50)//å› ä¸ºèƒŒæ™¯å›¾ä¼šæœ‰ä¸€äº›åƒç´ å·®å¼?
                return true;

        return false;
    }
}
