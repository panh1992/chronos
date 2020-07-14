package org.core.util;


import org.junit.Test;

import java.util.List;

public class PathUtilTest {

    @Test
    public void getFileNames() {
        String filePath = "chronos:/a/b//c\\d//s.txt";
        List<String> fileNames = PathUtil.getFileNames(filePath);
        System.out.println(fileNames);
        filePath = "/a/b//c\\d//s.txt";
        fileNames = PathUtil.getFileNames(filePath);
        System.out.println(fileNames);
    }

    @Test
    public void getExtName() {
        String fileName = "abc.txt";
        System.out.println(PathUtil.getExtName(fileName));
    }

}