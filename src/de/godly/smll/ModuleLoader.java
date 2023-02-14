package de.godly.smll;

import de.godly.smll.util.ReflectionUtil;
import lombok.SneakyThrows;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.logging.Level;
import java.util.stream.Collectors;

public class ModuleLoader implements Loader<Module>{


    public static final Comparator<Class<? extends Module>> COMPARATOR = new Comparator<Class<? extends Module>>() {
        // Overriding the compare method to sort the priority
        @SneakyThrows
        public int compare(Class<? extends Module> d, Class<? extends Module> d1) {
            if(!d.isAnnotationPresent(ModuleInfo.class) || !d1.isAnnotationPresent(ModuleInfo.class)) throw new Module.ModuleInfoException("Tried to compare classes " + d.getName() + " " + d1.getName());
            int priorityOne = ((ModuleInfo)d.getAnnotation(ModuleInfo.class)).priority();
            int priorityTwo = ((ModuleInfo)d1.getAnnotation(ModuleInfo.class)).priority();
            return priorityOne - priorityTwo;
        }
    };

    /**
     *
     * @param path JarFile to load classes from
     * @param annotation annotation of classes to load; null = all !!!!obsolete as we only handle Modules
     * @param superClass superClass of classes to load; null = all !!!!obsolete as we only handle Modules
     * @return List of possible classes
     * @throws ClassNotFoundException
     * @throws IOException
     */
    @Override
    public List<Class<? extends Module>> load(String path, @Deprecated Class annotation, @Deprecated Class superClass) throws ClassNotFoundException, IOException, Module.ModuleInfoException, URISyntaxException {
        List<Class<? extends Module>> c = new ArrayList<>();

        JarFile jar = new JarFile(path);
        Enumeration<JarEntry> entries = jar.entries();
        //URLClassLoader urlCLASS = new URLClassLoader(new URL[]{new URL(path)}, this.getClass().getClassLoader());
        while (entries.hasMoreElements()) {
            String next = entries.nextElement().toString();
            next = next.replaceAll("/", ".");
            if (!next.endsWith(".class")) continue;
            next = next.replace(".class", "");
            System.out.println(next);
            //this.getClass().getClassLoader().loadClass(next);
            Class<?> cls = Class.forName(next, true, SimpleModuleLoader.INSTANCE.mainClassLoader);

            if ((ReflectionUtil.getSuperClasses(cls).contains(Module.class)) && (annotation == null || cls.getAnnotation(ModuleInfo.class) != null)) {
                c.add((Class<? extends Module>) cls);
            }
        }
        c.sort(COMPARATOR.reversed());
        jar.close();
        return c;
    }




}
