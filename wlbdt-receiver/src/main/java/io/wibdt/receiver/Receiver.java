package io.wibdt.receiver;

import io.wibdt.common.util.VideoUtils;
import io.wibdt.common.util.ZingUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

import static io.wibdt.common.Config.COMMON_RECV_TEMP_PATH;

public class Receiver {

    /**
     * Modify this parameter!
     */
    private static final String VIDEO_PATH = System.getProperty("user.home") + "/Downloads/VID20220320234657.mp4";

    private static final String RECV_TEMP_PATH = COMMON_RECV_TEMP_PATH;

    public static void main(String[] args) throws IOException {
        File video = new File(VIDEO_PATH);
        VideoUtils.dealVideoPic(video, RECV_TEMP_PATH);

        Set<String> contentSet = new HashSet<>();
        StringBuilder contents = new StringBuilder();
        Files.list(Paths.get(RECV_TEMP_PATH)).sorted().forEach(e -> {
            try {
                String decode = ZingUtils.decode(e.toFile().getAbsoluteFile().getAbsolutePath());
                System.out.println(e.getFileName() + ":" + decode);
                if (decode != null && !contentSet.contains(decode)) {
                    contents.append(decode);
                    contentSet.add(decode);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        System.out.println("全文：" + contents);
    }

}
