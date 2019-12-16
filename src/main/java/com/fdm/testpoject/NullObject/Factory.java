package com.fdm.testpoject.NullObject;

/**
 * @author fdm
 * @date 2019/11/22 11:05
 * @Description:
 */
public class Factory {

    public static DependencyBase get(Nullable dependencyBase){
        if (dependencyBase == null){
            return new NullObeject();
        }
        return new Dependency();
    }
}
