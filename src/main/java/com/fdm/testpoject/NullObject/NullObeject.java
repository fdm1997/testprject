package com.fdm.testpoject.NullObject;

/**
 * @author fdm
 * @date 2019/11/22 11:04
 * @Description:
 */
public class NullObeject implements DependencyBase {
    @Override
    public void Operation() {

    }

    @Override
    public boolean isNull() {
        return true;
    }
}
