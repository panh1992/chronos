package org.core.util;

import com.google.common.base.Strings;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 路径处理工具类
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class PathUtil {

    private static final String STORAGE_PREFIX = "chronos:";

    /**
     * 判断是否为存储路径
     */
    public static boolean isPath(String path, boolean isDir) {
        return true;
    }

    /**
     * 根据文件路径 获取 文件名集合
     */
    public static List<String> getFileNames(String path) {
        return Arrays.stream(path.replaceFirst(STORAGE_PREFIX, "").split("[/\\\\]"))
                .filter(x -> !Strings.isNullOrEmpty(x)).collect(Collectors.toList());
    }

    /**
     * 根据文件名获取文件扩展名
     */
    public static String getExtName(String fileName) {

        int index = fileName.lastIndexOf('.');

        return index == -1 ? null : fileName.substring(index + 1);

    }

}
