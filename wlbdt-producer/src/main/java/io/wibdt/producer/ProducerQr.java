package io.wibdt.producer;

import com.google.zxing.WriterException;
import io.wibdt.common.util.BinaryUtils;
import io.wibdt.common.util.FileUtils;
import io.wibdt.common.util.ZingUtils;
import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.*;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static io.wibdt.common.Config.*;

/**
 * type：二维码方式
 * description：bytes>hexStr>qr
 */
public class ProducerQr extends Application {

    /**
     * Modify this parameter!
     */
    private static final String PROD_FILE = COMMON_PROD_RESOURCE_PATH + "10kb.svg";

    private static final Integer PROD_QRCODE_SIZE = 512;
    private static final Integer PROD_QRCODE_TOGGLE_TIME = 60;
    private static final String PROD_TEMP_PATH = COMMON_PROD_TEMP_PATH;
    private final ImageView imageView = new ImageView(new Image(Paths.get(COMMON_PROD_RESOURCE_PATH + "readygo.jpeg").toUri().toURL().toString()));
    private final List<Image> images = new ArrayList<>();

    public ProducerQr() throws MalformedURLException {
    }

    @Override
    public void start(Stage primaryStage) throws IOException, WriterException {
        generate();
        Files.list(Paths.get(PROD_TEMP_PATH)).sorted().forEach(e -> images.add(new Image(String.valueOf(e.toUri()))));
        show(primaryStage);
    }

    private void show(Stage primaryStage) {
        BorderPane pane = new BorderPane();
        Rectangle2D screenRectangle = Screen.getPrimary().getBounds();
        double width = screenRectangle.getWidth();
        double height = screenRectangle.getHeight();
        Scene scene = new Scene(pane, width, height, Color.WHITE);
        pane.setCenter(imageView);
        primaryStage.setScene(scene);
        primaryStage.show();

        new Refresh().start();
    }

    private void generate() throws IOException, WriterException {
        FileUtils.initDirectory(PROD_TEMP_PATH);

        InputStream os = new FileInputStream(PROD_FILE);

        StringBuilder contentAllBuilder = new StringBuilder();
        StringBuilder contentBuilder = new StringBuilder();
        byte[] rbs = new byte[PROD_QRCODE_SIZE];
        int qrIndex = 1;
        while (os.read(rbs) != -1) {
            for (byte rb : rbs) {
                String hex = BinaryUtils.byteToHex(rb);
                contentBuilder.append(hex);
            }
            contentAllBuilder.append(contentBuilder);
            String filepath = PROD_TEMP_PATH + System.currentTimeMillis() + "_" + qrIndex + ".png";
            ZingUtils.encode(contentBuilder.toString().toUpperCase(), filepath);

            contentBuilder.delete(0, contentBuilder.length());
            qrIndex++;
        }
        System.out.println("全文：" + contentAllBuilder);
        System.out.println("长度：" + contentAllBuilder.length());
    }

    class Refresh extends Thread {

        @Override
        public void run() {
            try {
                Thread.sleep(2000);
                for (Image image : images) {
                    imageView.setImage(image);
                    Thread.sleep(PROD_QRCODE_TOGGLE_TIME);
                }
                System.exit(0);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
