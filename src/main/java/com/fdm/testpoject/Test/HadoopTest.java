package com.fdm.testpoject.Test;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import java.io.IOException;
import java.net.URI;

/**
 * @author fdm
 * @date 2019/9/6 10:23
 * @Description:
 */


public class HadoopTest {
    public static void main(String[] args) throws Exception {
        Configuration conf = new Configuration();
        FileSystem fs = FileSystem.get(new URI("hdfs://192.168.20.101:9000"),conf,"root");
        fs.mkdirs(new Path("/20190906"));
        fs.copyFromLocalFile(new Path("C:\\Users\\Lenovo\\Desktop\\云货架数据库字段.txt"), new Path("/20190906/esars.txt"));


        fs.close();
        System.out.println("end");
    }
}
