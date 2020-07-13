/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2020 All Rights Reserved.
 */

import org.rocksdb.NativeLibraryLoader;
import org.rocksdb.util.Environment;

import static java.lang.System.out;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

/**
 * @author xiangxu
 * @version : RocksDBTest.java, v 0.1 2020年07月11日 11:04 下午 xiangxu Exp $
 */
public class RocksDBTest {
    public static void main(String[] args) throws IOException {
        String sharedLibraryName = Environment.getSharedLibraryName("rocksdb");
        String jniLibraryName = Environment.getJniLibraryName("rocksdb");
        String jniLibraryFileName = Environment.getJniLibraryFileName("rocksdb");
        String OS = System.getProperty("os.name").toLowerCase();
        String ARCH = System.getProperty("os.arch").toLowerCase();

        out.printf("shardLibraryName=%s\n", sharedLibraryName);
        out.printf("jniLibraryName=%s\n", jniLibraryName);
        out.printf("jniLibraryFileName=%s\n", jniLibraryFileName);
        out.printf("OS=%s\n", OS);
        out.printf("ARCH=%s\n", ARCH);

        String tempFileSuffix = Environment.getJniLibraryExtension();
        File tempFile = File.createTempFile("librocksdbjni", tempFileSuffix);
        if (!tempFile.exists()) {
            throw new RuntimeException("File " + tempFile.getAbsolutePath() + " does not exist.");
        }
        InputStream jniLibraryStream = NativeLibraryLoader.getInstance().getClass().getClassLoader().getResourceAsStream(jniLibraryFileName);
        if (jniLibraryStream == null) {
            throw new RuntimeException(jniLibraryFileName + " was not found inside JAR.");
        }
        String tempPath = tempFile.getAbsolutePath();
        Files.copy(jniLibraryStream, tempFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
        jniLibraryStream.close();
        out.printf("file: %s, existed: %s\n", tempPath, new File(tempPath).exists());
        System.load(tempPath);
        out.println("load success");

    }
}