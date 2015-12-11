package yjmyzz.jetty.demo.util;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Properties;


/**
 * 文件工具类
 */
public class FileUtil {

    private static Logger logger = LoggerFactory.getLogger(FileUtil.class);

    private FileUtil() {
        //工具类无需对象实例化
    }

    public static String getJarExecPath(Class clazz) {
        String path = clazz.getProtectionDomain().getCodeSource().getLocation().getPath();
        if (OSUtil.getOSname().equals(OSType.Windows)) {
            return path.substring(1);
        }
        return path;
    }

    /**
     * 判断文件是否存在
     *
     * @param fileName
     * @return
     */
    public static boolean exist(String fileName) {
        File f = new File(fileName);
        return f.exists();
    }


    /**
     * 根据完整文件名，得到文件所在目录
     *
     * @param filePath
     * @return
     */
    public static String getDirName(String filePath) {
        File f = new File(filePath);
        return f.getParent();
    }

    /**
     * 当前目录路径
     */
    public static String currentWorkDir = System.getProperty("user.dir") + "/";



    /**
     * 加载classPath下的属性文件
     *
     * @param fileName 比如：“/properties/mail.properties”
     * @return
     * @throws Exception
     */
    public static Properties loadProperties(String fileName) {
        try {
            Properties p = new Properties();
            p.load(FileUtil.class.getResourceAsStream(fileName));
            return p;
        } catch (Exception e) {
            //logger.error(fileName + " loadProperties error", e);
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 删除文件
     *
     * @param fileName 待删除的完整文件名
     * @return
     */
    public static boolean delete(String fileName) {
        boolean result = false;
        File f = new File(fileName);
        if (f.exists()) {
            result = f.delete();

        } else {
            result = true;
        }
        return result;
    }

    /**
     * 递归获取指定目录下的所有的文件（不包括文件夹）
     *
     * @return
     */
    public static ArrayList<File> getAllFiles(String dirPath) {
        File dir = new File(dirPath);

        ArrayList<File> files = new ArrayList<File>();

        if (dir.isDirectory()) {
            File[] fileArr = dir.listFiles();
            for (int i = 0; i < fileArr.length; i++) {
                File f = fileArr[i];
                if (f.isFile()) {
                    files.add(f);
                } else {
                    files.addAll(getAllFiles(f.getPath()));
                }
            }
        }
        return files;
    }


    /**
     * 获取指定目录下的所有下一级子目录(不递归)
     *
     * @param dirPath
     * @return
     */
    public static ArrayList<File> getAllDirectory(String dirPath) {
        return getAllDirectory(dirPath, false);
    }


    /**
     * 获取指定路径下的所有目录
     *
     * @param dirPath
     * @param isRecursive
     * @return
     */
    public static ArrayList<File> getAllDirectory(String dirPath, boolean isRecursive) {
        File dir = new File(dirPath);
        ArrayList<File> files = new ArrayList<File>();
        if (dir.isDirectory()) {
            File[] fileArr = dir.listFiles();
            for (int i = 0; i < fileArr.length; i++) {
                File f = fileArr[i];
                if (f.isDirectory()) {
                    files.add(f);
                    if (isRecursive) {
                        files.addAll(getAllDirectory(f.getPath(), true));
                    }
                }
            }
        }
        return files;
    }

    /**
     * 获取指定目录下的所有文件(不包括子文件夹)
     *
     * @param dirPath
     * @return
     */
    public static ArrayList<File> getDirFiles(String dirPath) {
        File path = new File(dirPath);
        File[] fileArr = path.listFiles();
        ArrayList<File> files = new ArrayList<File>();

        if (fileArr == null) {
            return files;
        }

        for (File f : fileArr) {
            if (f.isFile()) {
                files.add(f);
            }
        }
        return files;
    }

    /**
     * 获取指定目录下特定文件后缀名的文件列表(不包括子文件夹)
     *
     * @param dirPath 目录路径
     * @param suffix  文件后缀
     * @return
     */
    public static List<File> getDirFiles(String dirPath,
                                         final String suffix) {
        File path = new File(dirPath);
        File[] fileArr = path.listFiles(new FilenameFilter() {
            public boolean accept(File dir, String name) {
                String lowerName = name.toLowerCase();
                String lowerSuffix = suffix.toLowerCase();
                if (lowerName.endsWith(lowerSuffix)) {
                    return true;
                }
                return false;
            }

        });
        List<File> files = new ArrayList<File>();

        for (File f : fileArr) {
            if (f.isFile()) {
                files.add(f);
            }
        }
        return files;
    }

    /**
     * 批量删除文件
     *
     * @param files
     */
    public static void delete(List<File> files) {
        for (int i = files.size() - 1; i >= 0; i--) {
            File f = files.get(i);
            logger.debug("准备删除文件：" + f.getAbsolutePath());
            if (f.exists()) {
                if (f.delete()) {
                    logger.debug("文件：" + f.getAbsolutePath() + " 删除成功！");
                } else {
                    logger.debug("文件：" + f.getAbsolutePath() + " 删除失败！");
                }
            }
        }

    }


    /**
     * 通过前缀得到指定文件夹下的文件列表
     *
     * @param dirPath
     * @param prefix
     * @return
     */
    public static List<File> getDirFilesByPrefix(String dirPath,
                                                 final String prefix) {
        File path = new File(dirPath);
        File[] fileArr = path.listFiles(new FilenameFilter() {
            public boolean accept(File dir, String name) {
                String lowerName = name.toLowerCase();
                String lowerPrefix = prefix.toLowerCase();
                if (lowerName.startsWith(lowerPrefix)) {
                    return true;
                }
                return false;
            }

        });
        List<File> files = new ArrayList<File>();

        for (File f : fileArr) {
            if (f.isFile()) {
                files.add(f);
            }
        }
        return files;
    }

    public static List<File> getDirFilesByPrefixAndSurfix(String dirPath,
                                                          final String prefix, final String surfix) {
        File path = new File(dirPath);
        File[] fileArr = path.listFiles(new FilenameFilter() {
            public boolean accept(File dir, String name) {
                String lowerName = name.toLowerCase();
                String lowerPrefix = prefix.toLowerCase();
                if (lowerName.startsWith(lowerPrefix) && lowerName.endsWith(surfix)) {
                    return true;
                }
                return false;
            }

        });
        List<File> files = new ArrayList<File>();

        for (File f : fileArr) {
            if (f.isFile()) {
                files.add(f);
            }
        }
        return files;
    }


    public static List<File> getDirFilesByPrefix(String dirPath,
                                                 final String prefix, final String execludePrefix) {
        File path = new File(dirPath);
        File[] fileArr = path.listFiles(new FilenameFilter() {
            public boolean accept(File dir, String name) {
                String lowerName = name.toLowerCase();
                String lowerPrefix = prefix.toLowerCase();
                String lowerExecludePrefix = execludePrefix.toLowerCase();
                if (lowerName.startsWith(lowerPrefix) && !lowerName.startsWith(lowerExecludePrefix)) {
                    return true;
                }
                return false;
            }

        });
        List<File> files = new ArrayList<File>();

        for (File f : fileArr) {
            if (f.isFile()) {
                files.add(f);
            }
        }
        return files;
    }

    /**
     * 读取文件内容
     *
     * @param fileName 待读取的完整文件名
     * @return 文件内容
     * @throws IOException
     */
    public static String read(String fileName) throws IOException {
        File f = new File(fileName);
        if (!f.exists()) {
            return null;
        }
        FileInputStream fs = new FileInputStream(f);
        String result = null;
        byte[] b = new byte[fs.available()];
        fs.read(b);
        fs.close();
        result = new String(b);
        return result;
    }

    /**
     * 读取文件内容
     *
     * @param f
     * @return
     * @throws IOException
     */
    public static String read(File f) throws IOException {
        FileInputStream fs = new FileInputStream(f);
        String result = null;
        byte[] b = new byte[fs.available()];
        fs.read(b);
        fs.close();
        result = new String(b);
        return result;
    }

    /**
     * 写文件
     *
     * @param fileName    目标文件名
     * @param fileContent 写入的内容
     * @return
     * @throws IOException
     */
    public static void write(String fileName, String fileContent)
            throws IOException {
        write(fileName, fileContent, true, true);
    }

    /**
     * 写文件
     *
     * @param fileName      完整文件名(类似：/usr/a/b/c/d.txt)
     * @param fileContent   文件内容
     * @param autoCreateDir 目录不存在时，是否自动创建(多级)目录
     * @param autoOverwrite 目标文件存在时，是否自动覆盖
     * @return
     * @throws IOException
     */
    public static void write(String fileName, String fileContent,
                             boolean autoCreateDir, boolean autoOverwrite) throws IOException {
        write(fileName, fileContent.getBytes(), autoCreateDir,
                autoOverwrite);
    }

    /**
     * 写文件
     *
     * @param fileName      完整文件名(类似：/usr/a/b/c/d.txt)
     * @param contentBytes  文件内容的字节数组
     * @param autoCreateDir 目录不存在时，是否自动创建(多级)目录
     * @param autoOverwrite 目标文件存在时，是否自动覆盖
     * @return
     * @throws IOException
     */
    public static void write(String fileName, byte[] contentBytes,
                             boolean autoCreateDir, boolean autoOverwrite) throws IOException {

        if (autoCreateDir) {
            createDirs(fileName);
        }
        if (autoOverwrite) {
            delete(fileName);
        }
        File f = new File(fileName);
        FileOutputStream fs = new FileOutputStream(f);
        fs.write(contentBytes);
        fs.flush();
        fs.close();
    }

    /**
     * 追加内容到指定文件
     *
     * @param fileName
     * @param fileContent
     * @return
     * @throws IOException
     */
    public static void append(String fileName, String fileContent, boolean createFileIfNoExist)
            throws IOException {
        File f = new File(fileName);
        if (f.exists()) {
            RandomAccessFile rFile = new RandomAccessFile(f, "rw");
            byte[] b = fileContent.getBytes();
            long originLen = f.length();
            rFile.setLength(originLen + b.length);
            rFile.seek(originLen);
            rFile.write(b);
            rFile.close();
        } else if (createFileIfNoExist) {
            write(fileName, fileContent);
        }
    }

    public static void append(String fileName, String fileContent) throws IOException {
        append(fileName, fileContent, true);
    }


    /**
     * 根据文件名，比较文件
     *
     * @author yjmyzz@126.com
     */
    private static class FileComparator implements Comparator<File> {
        public int compare(File o1, File o2) {
            return o1.getName().compareToIgnoreCase(o2.getName());
        }
    }

    /**
     * 创建(多级)目录
     *
     * @param filePath 完整的文件名(类似：/usr/a/b/c/d.xml)
     */
    public static void createDirs(String filePath) {


        File file = new File(filePath);
        if (file.exists()) {
            return;
        }
        File parent = file.getParentFile();
        if (parent != null && !parent.exists()) {
            parent.mkdirs();
        }

    }

    /**
     * 移动文件
     *
     * @param fileNameFrom
     * @param fileNameTo
     * @throws IOException
     */
    public static void moveFile(String fileNameFrom, String fileNameTo) throws IOException {
        write(fileNameTo, read(fileNameFrom));
        delete(fileNameFrom);

    }

    /**
     * 分割处理Runnable
     *
     * @author yjmyzz@126.com
     */
    private static class SplitRunnable implements Runnable {
        int byteSize;
        String partFileName;
        File originFile;
        int startPos;

        public SplitRunnable(int byteSize, int startPos, String partFileName,
                             File originFile) {
            this.startPos = startPos;
            this.byteSize = byteSize;
            this.partFileName = partFileName;
            this.originFile = originFile;
        }

        public void run() {
            RandomAccessFile rFile;
            OutputStream os;
            try {
                rFile = new RandomAccessFile(originFile, "r");
                byte[] b = new byte[byteSize];
                rFile.seek(startPos);// 移动指针到每“段”开头
                int s = rFile.read(b);
                os = new FileOutputStream(partFileName);
                os.write(b, 0, s);
                os.flush();
                os.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 合并处理Runnable
     *
     * @author yjmyzz@126.com
     */
    private static class MergeRunnable implements Runnable {
        long startPos;
        String mergeFileName;
        File partFile;

        public MergeRunnable(long startPos, String mergeFileName, File partFile) {
            this.startPos = startPos;
            this.mergeFileName = mergeFileName;
            this.partFile = partFile;
        }

        public void run() {
            RandomAccessFile rFile;
            try {
                rFile = new RandomAccessFile(mergeFileName, "rw");
                rFile.seek(startPos);
                FileInputStream fs = new FileInputStream(partFile);
                byte[] b = new byte[fs.available()];
                fs.read(b);
                fs.close();
                rFile.write(b);
                rFile.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
