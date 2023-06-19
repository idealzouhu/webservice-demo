package utils;

public class MyUtilBackup {
    /**
     * description: 获取当前系统的临时目录
     *
     * @return String
     * @author:jinshengyuan
     * @date: 2022-1-4
     * @since v1.0.0
     */
    private String getTmpdir() {
        return System.getProperty("java.io.tmpdir");
    }
}
