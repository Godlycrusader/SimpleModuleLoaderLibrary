package de.godly.smll.util;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.jar.JarFile;

public class ReflectionUtil {

    public static final HashMap<String, JarFile> CACHED_JAR_FILES = new HashMap<>();


    public static List<Class<?>> getSuperClasses(Class<?> cls){
        List<Class<?>> toReturn = new ArrayList<>();
        while (true) {
            cls = cls.getSuperclass();
            if(cls != null) {
                toReturn.add(cls);
            } else break;
        }
        return toReturn;
    }

    public static JarFile getJarFileFromPath(String path){
        if(CACHED_JAR_FILES.containsKey(path)) return CACHED_JAR_FILES.get(path);
        else {
            try {
                JarFile file = new JarFile(path);
                CACHED_JAR_FILES.put(path, file);
                return file;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

}
