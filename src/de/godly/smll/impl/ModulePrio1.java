package de.godly.smll.impl;

import de.godly.smll.Module;
import de.godly.smll.ModuleInfo;
import de.godly.smll.SimpleModuleLoader;

@ModuleInfo(name = "ModulePrio1", priority = 1)
public class ModulePrio1 extends Module {
    public ModulePrio1() throws ModuleInfoException {

    }
    @Override
    public void onLoad() {
        SimpleModuleLoader.INSTANCE.running = false;
    }

    @Override
    public void onUnload() {
        System.out.println("UNLOADING " + this.getName());
    }
}