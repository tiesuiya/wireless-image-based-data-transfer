package io.wibdt.common.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

public final class FileUtils {

    public static void initDirectory(String tempDirectoryPath) throws IOException {
        File tempDirectory = new File(tempDirectoryPath);
        if (tempDirectory.exists() || tempDirectory.mkdirs()) {
            Path directory = Paths.get(tempDirectory.getPath());
            Files.walkFileTree(directory, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attributes) throws IOException {
                    Files.delete(file);
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                    if (dir != directory) {
                        Files.delete(dir);
                    }
                    return FileVisitResult.CONTINUE;
                }
            });
        }
    }

}
