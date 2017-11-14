package com.salton123.xm.pattern.decoratorPattern;

/**
 * User: newSalton@outlook.com
 * Date: 2017/8/24 15:38
 * ModifyTime: 15:38
 * Description:
 */
public abstract class Decorator extends Component{
    private Component mComponent ;
    public Decorator(Component component){
        this.mComponent = component;
    }

    @Override
    public void operate() {
        mComponent.operate();
    }

}
