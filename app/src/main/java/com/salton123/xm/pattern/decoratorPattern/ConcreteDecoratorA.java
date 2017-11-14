package com.salton123.xm.pattern.decoratorPattern;

/**
 * User: newSalton@outlook.com
 * Date: 2017/8/24 15:40
 * ModifyTime: 15:40
 * Description:
 */
public class ConcreteDecoratorA extends Decorator {

    public ConcreteDecoratorA(Component component) {
        super(component);
    }

    @Override
    public void operate() {
        super.operate();
        operateA();
    }

    public void operateA(){
        System.out.println("operateA");
    }
}
