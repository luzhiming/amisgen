package com.luzm.amis.gen;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;

public class DaoClassLoader extends ClassLoader {
    private String classLoaderName;
    private final String fileExtention = ".class";
    private String path = "";


    public DaoClassLoader(String classLoaderName) {
        // 将系统类加载器当作该类加载器的父类
        super();
        this.classLoaderName = classLoaderName;
    }

    public DaoClassLoader(ClassLoader classLoader) {
        super(classLoader);
    }

    public DaoClassLoader(String classLoaderName, ClassLoader parentClassLaoder) {
        // 将自定义的类加载器当作类加载器的父类
        super(parentClassLaoder);
        this.classLoaderName = classLoaderName;
    }

    public void setPath(String path) {
        this.path = path;
    }

    /**
     * 父类在loadClass的时候会调用到该方法，模版方法模式。
     *
     * @param className
     * @return
     * @throws ClassNotFoundException
     */
    @Override
    protected Class<?> findClass(String className) throws ClassNotFoundException {
        /**
         * 实际上这个方法并没有被执行
         * 说明:--双亲委托，即委托给父类加载器去加载了。
         * 若有一个类加载器能够成功加载某个类，那么这个类加载器被称为:定义类加载器
         * 所有能成功返回Class对象引用的类加载器（包括定义类加载器）称为:初始类加载器
         *
         * 所以:
         * 系统类加载器是定义类加载器
         * 系统类加载器和MyTest11是初始类加载器
         */
        System.out.println("findClass invoked!");
        byte[] bytes = null;
        FileInputStream is = null;
        ByteArrayOutputStream bos = null;

        try {
            is = new FileInputStream(new File(this.path + className.replace(".", "\\") + fileExtention));
            // is = new FileInputStream(new File(className+fileExtention));
            bos = new ByteArrayOutputStream();
            int ch = 0;
            while (-1 != (ch = is.read())) {
                bos.write(ch);
            }
            bytes = bos.toByteArray();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        // 将bytes 转换成一个Class对象
        return super.defineClass(className, bytes, 0, bytes.length);
    }
}

