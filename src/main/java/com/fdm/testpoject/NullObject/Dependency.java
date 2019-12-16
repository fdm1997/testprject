package com.fdm.testpoject.NullObject;

/**
 * @author fdm
 * @date 2019/11/22 11:03
 * @Description:
 */
public class Dependency implements DependencyBase,Nullable {
    @Override
    public void Operation() {
        System.out.println("test");
    }

    @Override
    public boolean isNull() {
        return false;
    }
}
