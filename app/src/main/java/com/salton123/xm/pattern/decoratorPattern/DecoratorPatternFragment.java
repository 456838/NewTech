package com.salton123.xm.pattern.decoratorPattern;

import android.os.Bundle;

import com.salton123.base.BaseSupportFragment;
import com.salton123.xm.R;

/**
 * User: newSalton@outlook.com
 * Date: 2017/8/24 15:36
 * ModifyTime: 15:36
 * Description:
 */
public class DecoratorPatternFragment extends BaseSupportFragment {
    @Override
    public int GetLayout() {
        return R.layout.fm_decorator_pattern;
    }

    @Override
    public void InitVariable(Bundle savedInstanceState) {

    }

    @Override
    public void InitViewAndData() {
        Component component = new ConcreteComponent();
        Decorator decorator = new ConcreteDecoratorA(component);
        decorator.operate();
    }

    @Override
    public void InitListener() {

    }
}
