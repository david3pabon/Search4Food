package com.lifesum.test.search4food.models;

import com.orm.SugarRecord;
import com.orm.dsl.Ignore;

/**
 * Created by David on 7/5/14.
 */
public class Food extends SugarRecord<Food> {

    public Food() {

    }

    public Food(long apiId, String title, long categoryId, String category, int calories) {
        this.apiId = apiId;
        this.title = title;
        this.categoryId = categoryId;
        this.category = category;
        this.calories = calories;
    }

    public long apiId = -1;
    public String title = "";
    public long categoryId = -1;
    public String category = null;
    public int calories = 0;
}
