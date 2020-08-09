package org.core.util;

import org.junit.Test;

import java.util.Arrays;

public class CommonTest {

    @Test(expected = RuntimeException.class)
    public void test() {

        Arrays.stream(new int[]{1, 2, 3, 2, 3, 1, 3}).forEach(x -> {
            if (x == 3) {
                throw new RuntimeException("测试异常信息");
            }
            System.out.println(x);
        });

    }

}
