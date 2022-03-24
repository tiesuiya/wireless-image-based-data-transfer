package io.wibdt.common.util;

import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.Java2DFrameConverter;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public final class ImageUtils {

    public static void drawPic(Frame frame, String targetPath) throws IOException {
        System.out.printf("正在绘制图片：%s\n", targetPath);
        // 截取的帧图片
        Java2DFrameConverter converter = new Java2DFrameConverter();
        BufferedImage srcImage = converter.getBufferedImage(frame);
        int srcImageWidth = srcImage.getWidth();
        int srcImageHeight = srcImage.getHeight();

        BufferedImage thumbnailImage = new BufferedImage(srcImageWidth, srcImageHeight, BufferedImage.TYPE_3BYTE_BGR);
        thumbnailImage.getGraphics().drawImage(srcImage.getScaledInstance(srcImageWidth, srcImageHeight, Image.SCALE_SMOOTH), 0, 0, null);

        File picFile = new File(targetPath);
        ImageIO.write(thumbnailImage, "jpg", picFile);
    }

}
