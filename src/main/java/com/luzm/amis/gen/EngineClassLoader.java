package com.luzm.amis.gen;

import java.net.URL;
import java.net.URLClassLoader;

public class EngineClassLoader extends URLClassLoader {
    public EngineClassLoader() {
        this(getSystemClassLoader());
    }

    public EngineClassLoader(ClassLoader parent) {
        super(new URL[]{}, parent);
    }

    public void addURL(URL... urls) {
        if (urls != null) {
            for (URL url : urls) {
                super.addURL(url);
            }
        }
    }
}