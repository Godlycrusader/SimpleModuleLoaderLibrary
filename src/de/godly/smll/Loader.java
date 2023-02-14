package de.godly.smll;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.net.URISyntaxException;
import java.util.List;
import java.util.jar.JarFile;

public interface Loader<T> {


    /**
     * @param path to load classes from
     * @param annotation annotation of classes to load; null = all
     * @param superClass superClass of classes to load; null = all
     * @return List of found classes.
     */
    public List<Class<? extends T>> load(String path, Class<? extends Annotation> annotation, Class<?> superClass) throws ClassNotFoundException, IOException, Module.ModuleInfoException, URISyntaxException;


}
