package com.example.heyao.activityanimation.dagger2;

import javax.inject.Inject;

/**
 * Created by heyao on 2017/9/20.
 */

public class SimpleMaker implements CoffeeMaker {

    private Cooker cooker;

    @Inject
    public SimpleMaker(Cooker cooker) {
        this.cooker = cooker;
    }

    @Override
    public String makeCoffee() {
        return cooker.make();
    }
}
