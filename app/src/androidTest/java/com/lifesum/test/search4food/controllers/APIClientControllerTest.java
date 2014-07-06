package com.lifesum.test.search4food.controllers;

import android.test.AndroidTestCase;

import com.lifesum.test.search4food.models.Food;

import junit.framework.Assert;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by David on 6/29/14.
 */
public class APIClientControllerTest extends AndroidTestCase {

    APIClientController apiClient;

    //================================================================================
    // Test Lifecycle
    //================================================================================

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        apiClient = new APIClientController();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    //================================================================================
    // Tests
    //================================================================================

    public void testPopulateResultList() {
        List<Food> foods = apiClient.populateResultList(null);
        Assert.assertNotNull("Error creating list of models", foods);

        try {
            JSONObject jsonObject = new JSONObject(JSON_MOCK);
            foods = apiClient.populateResultList(jsonObject);
            Assert.assertTrue("List is not been populated", foods != null && foods.size() > 0);

            Food food = foods.get(0);

            Assert.assertNotNull("Model is a null instance", food);

            Assert.assertEquals("Field values don't match", food.apiId, 1013);

            Assert.assertNotNull("Tags are not instantiated", food.title);

            Assert.assertEquals("There was an error parsing tags", food.title, "Thick crust cheese pizza");

        } catch (JSONException e) {
           Assert.fail("Error parsing json object, Test error");
        }
    }

    //================================================================================
    // Mock Response - JSON
    //================================================================================

    public static String JSON_MOCK =
            "{\n" +
            "    \"meta\": {\n" +
            "        \"code\": 200\n" +
            "    },\n" +
            "    \"response\": {\n" +
            "        \"list\": [\n" +
            "            {\n" +
            "                \"categoryid\": 56,\n" +
            "                \"fiber\": 1.5,\n" +
            "                \"headimage\": \"\",\n" +
            "                \"pcsingram\": 106,\n" +
            "                \"brand\": \"\",\n" +
            "                \"unsaturatedfat\": 5.323,\n" +
            "                \"fat\": 10.99,\n" +
            "                \"servingcategory\": 15,\n" +
            "                \"typeofmeasurement\": 0,\n" +
            "                \"protein\": 12,\n" +
            "                \"defaultserving\": 445,\n" +
            "                \"mlingram\": 0,\n" +
            "                \"id\": 1013,\n" +
            "                \"saturatedfat\": 4.679,\n" +
            "                \"category\": \"Pizza\",\n" +
            "                \"verified\": true,\n" +
            "                \"title\": \"Thick crust cheese pizza\",\n" +
            "                \"pcstext\": \"Slice\",\n" +
            "                \"sodium\": 533,\n" +
            "                \"carbohydrates\": 31.33,\n" +
            "                \"showonlysametype\": 0,\n" +
            "                \"calories\": 272,\n" +
            "                \"serving_version\": 3,\n" +
            "                \"sugar\": 3.18,\n" +
            "                \"measurementid\": 1,\n" +
            "                \"cholesterol\": 24,\n" +
            "                \"gramsperserving\": 75,\n" +
            "                \"showmeasurement\": 2,\n" +
            "                \"potassium\": 157\n" +
            "            }\n" +
            "       ]\n" +
            "    }\n" +
            "}";
}
