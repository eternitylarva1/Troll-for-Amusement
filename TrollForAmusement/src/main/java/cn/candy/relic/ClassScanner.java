package cn.candy.relic;


import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ClassScanner {

    public static void main(String[] args) {
        System.out.println(1);
        String packageName = "com.megacrit"; // 要获取类的包名
        List<Class<?>> classes = getClasses(packageName);
        for (Class<?> clazz : classes) {
            System.out.println(clazz.getName());
        }
    }

    public static List<Class<?>> getClasses(String packageName) {
        String path = packageName.replace(".", "/");
        File dir = new File(path);
        List<Class<?>> classes = new ArrayList<>();
        if (dir.exists() && dir.isDirectory()) {
            File[] files = dir.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isDirectory()) {
                        String subPackageName = packageName + "." + file.getName();
                        classes.addAll(getClasses(subPackageName));
                    } else if (file.getName().endsWith(".class")) {
                        String className = packageName + "." + file.getName().substring(0, file.getName().length() - 6);
                        try {
                            Class<?> clazz = Class.forName(className);
                            classes.add(clazz);
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        return classes;
    }
}