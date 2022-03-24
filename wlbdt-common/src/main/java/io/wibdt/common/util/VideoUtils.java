package io.wibdt.common.util;

import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.FrameGrabber;

import java.io.File;
import java.io.IOException;

public final class VideoUtils {

    public static void dealVideoPic(File video, String tempPath) {
        System.out.printf("视频时长：%ss", getVideoDuration(video));
        FFmpegFrameGrabber ff = new FFmpegFrameGrabber(video);
        try {
            ff.start();

            FileUtils.initDirectory(tempPath);
            int length = ff.getLengthInFrames();
            Frame frame;
            int qrIndex = 1;
            for (int i = 0; i < length; i++) {
                frame = ff.grabImage();

                if (frame != null) {
                    String path = tempPath + System.currentTimeMillis() + "_" + qrIndex + ".png";
                    ImageUtils.drawPic(frame, path);
                    qrIndex++;
                }
            }

            ff.stop();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static long getVideoDuration(File video) {
        long duration = 0L;
        FFmpegFrameGrabber ff = new FFmpegFrameGrabber(video);
        try {
            ff.start();
            duration = ff.getLengthInTime() / (1000 * 1000);
            ff.stop();
        } catch (FrameGrabber.Exception e) {
            e.printStackTrace();
        }
        return duration;
    }

}
