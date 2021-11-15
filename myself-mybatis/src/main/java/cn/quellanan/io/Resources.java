package cn.quellanan.io;

import java.io.InputStream;

public class Resources {

    public  static InputStream getResources(String path){
        //使用系统自带的类Resources加载器来获取文件。
        return Resources.class.getClassLoader().getResourceAsStream(path);
    }
}
