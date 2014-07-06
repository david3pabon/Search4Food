package com.lifesum.test.search4food.controllers;

import com.lifesum.test.search4food.models.Food;

import java.util.List;

/**
 * Created by David on 7/5/14.
 */
public interface SuccessCallback {

    public void onSuccess(List<Food> foods);
}
