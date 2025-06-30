package Zhenghuo.utils;

import java.io.File;

public class GetFreeSpace {
    public static int GetFreeSpaces() {
        // 创建代表 C 盘的 File 对象
        File cDrive = new File("C:\\");
        // 获取 C 盘的可用空间（字节）
        long freeSpaceInBytes = cDrive.getFreeSpace();
        // 将字节转换为 GB 并取整
        int freeSpaceInGB = (int) (freeSpaceInBytes / (1024L * 1024L * 1024L));
        System.out.println("C 盘剩余空间: " + freeSpaceInGB + " GB");
        return freeSpaceInGB;
    }
    public static int GetFullSpaces() {
        File cDrive = new File("C:\\");
        long totalSpaceInBytes = cDrive.getTotalSpace();
        int totalSpaceInGB = (int) (totalSpaceInBytes / (1024L * 1024L * 1024L));
        System.out.println("C 盘总空间: " + totalSpaceInGB + " GB");
        return totalSpaceInGB;
    }
}
