package net.tunie.learn;

import org.junit.jupiter.api.Test;

public class TypeTest {

    @Test
    public void test1(){
        Integer i = 1000;
        Integer k = 1000;
        int j = 1000;
        System.out.println(i == j);
        System.out.println(i == k);

        foo(j);

        foo(i);
    }

    public void foo(Integer integer) {
        System.out.println(integer == 1000);
    }
}
