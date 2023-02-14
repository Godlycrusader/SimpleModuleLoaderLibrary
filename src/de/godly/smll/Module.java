package de.godly.smll;

import lombok.Data;

import java.util.logging.Logger;

@Data
public abstract class Module implements Comparable<Module>{


    private final String name, version;
    private final Logger logger;
    public Module() throws ModuleInfoException {
        if(!this.getClass().isAnnotationPresent(ModuleInfo.class)) throw new ModuleInfoException("Module " + this.getClass() + "has no ModuleInfo attached!");
        ModuleInfo info = this.getClass().getAnnotation(ModuleInfo.class);
        name = info.name();
        version = info.version();
        logger = Logger.getLogger(name);
    }

    public abstract void onLoad();

    public abstract void onUnload();

    public int compareTo(Module other){
        return this.getPriority() - other.getPriority();
    }

    private int getPriority() {
        return this.getClass().getAnnotation(ModuleInfo.class).priority();
    }

    public static class ModuleInfoException extends Exception {

        public String message;

        public ModuleInfoException(String message){
            this.message = message;
        }
        @Override
        public String getMessage() {
            return message;
        }
    }

}
