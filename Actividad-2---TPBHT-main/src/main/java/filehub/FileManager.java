package filehub;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.List;

public class FileManager {

    private static final String BASE_DIR = "filehub_files";

    public static void ensureBranchStructure(List<String> branches) {
        File base = new File(BASE_DIR);
        if (!base.exists()) {
            base.mkdirs();
        }
        for (String b : branches) {
            File dir = new File(base, b);
            if (!dir.exists()) {
                dir.mkdirs();
            }
        }
    }

    public static void saveFileToBranch(File source, String branch) throws IOException {
        File dir = new File(BASE_DIR, branch);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        File dest = new File(dir, source.getName());
        copyFile(source, dest);
    }

    private static void copyFile(File src, File dst) throws IOException {
        try (FileChannel in = new FileInputStream(src).getChannel();
             FileChannel out = new FileOutputStream(dst).getChannel()) {
            out.transferFrom(in, 0, in.size());
        }
    }
}
