package com.lifesum.test.search4food.activities;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.SearchView;

import com.lifesum.test.search4food.R;
import com.lifesum.test.search4food.activities.fragments.FoodFragment;
import com.lifesum.test.search4food.activities.fragments.ProgressDialogFragment;
import com.lifesum.test.search4food.controllers.APIClientController;
import com.lifesum.test.search4food.controllers.ErrorCallback;
import com.lifesum.test.search4food.controllers.SuccessCallback;
import com.lifesum.test.search4food.models.Food;
import com.lifesum.test.search4food.util.NetworkUtils;
import com.orm.query.Condition;
import com.orm.query.Select;

import java.util.List;


public class SearchActivity extends Activity {

    private SearchView mSearchView;
    private APIClientController mAPIClientController = new APIClientController();
    private DialogFragment mProgressDialog;

    private FoodFragment mFoodListFragment;

    //================================================================================
    // Lifecycle and Activity Methods
    //================================================================================

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_layout);

        mFoodListFragment = FoodFragment.newInstance();

        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, mFoodListFragment)
                    .commit();
        }

        if (Intent.ACTION_SEARCH.equals(getIntent().getAction())) {
            handleSearchAction(getIntent());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search, menu);


        MenuItem searchItem = menu.findItem(R.id.action_search);
        mSearchView = (SearchView) searchItem.getActionView();
        SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
        mSearchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        return true;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            handleSearchAction(intent);
        }
    }

    //================================================================================
    // SearchActivity Methods
    //================================================================================

    private void handleSearchAction(Intent intent) {
        final String query = intent.getStringExtra(SearchManager.QUERY);
        showProgress();

        if (NetworkUtils.verifyNetworkState(this)) {
            mAPIClientController.search(query, new SuccessCallback() {
                @Override
                public void onSuccess(List<Food> foods) {
                    mFoodListFragment.setFoods(foods);
                    hideProgress();
                }
            }, new ErrorCallback() {
                @Override
                public void onError() {
                    mFoodListFragment.setFoods(findFoodFromQuery(query));
                    hideProgress();
                }
            });
        } else {
            mFoodListFragment.setFoods(findFoodFromQuery(query));
            hideProgress();
        }
    }

    public List<Food> findFoodFromQuery(String query) {
        Select where = Select.from(Food.class).whereOr( Condition.prop("title").like("%"+query+"%"),
                                                        Condition.prop("category").like("%"+query+"%"));

        List<Food> result = where.list();

        return result;
    }

    private void showProgress() {
        if (mProgressDialog==null) {
            mProgressDialog = ProgressDialogFragment.newInstance();
        }
        if (!mProgressDialog.isVisible()) mProgressDialog.show(getFragmentManager(), getString(R.string.loading_text));
    }

    private void hideProgress() {
        if (mProgressDialog!=null && !isFinishing()) {
            mProgressDialog.dismiss();
        }
    }
}
