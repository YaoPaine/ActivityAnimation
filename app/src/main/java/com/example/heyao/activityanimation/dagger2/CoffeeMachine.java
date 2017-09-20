package com.example.heyao.activityanimation.dagger2;

import javax.inject.Inject;

/**
 * Created by heyao on 2017/9/20.
 */

public class CoffeeMachine {

    private CoffeeMaker coffeeMaker;

    @Inject
    public CoffeeMachine(CoffeeMaker coffeeMaker) {
        this.coffeeMaker = coffeeMaker;
    }

    public String makeCoffee() {
        return coffeeMaker.makeCoffee();
    }

}
