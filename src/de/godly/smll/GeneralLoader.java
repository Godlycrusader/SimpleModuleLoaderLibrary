package de.godly.smll;

import de.godly.smll.util.ReflectionUtil;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class GeneralLoader implements Loader{

    @Override
    public List<Class> load(String path, Class annotation, Class superClass) throws ClassNotFoundException, IOException, Module.ModuleInfoException, URISyntaxException {
        List<Class> c = new ArrayList<>();
        JarFile jar = new JarFile(path);
        Enumeration<JarEntry> entries = jar.entries();

        while (entries.hasMoreElements()) {
            String next = entries.nextElement().toString();
            next = next.replaceAll("/", ".");
            //EconomyServer.getInstance().getLogger().log(Level.INFO,next);
            if (!next.endsWith(".class")) continue;
            next = next.replace(".class", "");
            Class<?> cls = Class.forName(next);

            if ((superClass == null || ReflectionUtil.getSuperClasses(cls).contains(superClass)) && (annotation == null || cls.getAnnotation(annotation) != null)) {
                c.add( cls);
            }
        }
        jar.close();
        return c;
    }
}
