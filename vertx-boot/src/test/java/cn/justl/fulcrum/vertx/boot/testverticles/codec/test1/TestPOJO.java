package cn.justl.fulcrum.vertx.boot.testverticles.codec.test1;

import cn.justl.fulcrum.vertx.boot.annotation.CodeC;

/**
 * @Date : 2019/12/12
 * @Author : Jingl.Wang [jingl.wang123@gmail.com]
 * @Desc :
 */
@CodeC
public class TestPOJO {

    private int a;
    private String b;

    public int getA() {
        return a;
    }

    public void setA(int a) {
        this.a = a;
    }

    @Override
    public String toString() {
        return "TestPOJO{" +
            "a=" + a +
            ", b='" + b + '\'' +
            '}';
    }
}
