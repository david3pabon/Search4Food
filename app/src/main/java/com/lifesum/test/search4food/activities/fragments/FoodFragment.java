package com.lifesum.test.search4food.activities.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.lifesum.test.search4food.R;
import com.lifesum.test.search4food.activities.adapters.FoodListAdapter;
import com.lifesum.test.search4food.models.Food;

import java.util.ArrayList;
import java.util.List;

public class FoodFragment extends Fragment implements
        FoodListAdapter.DataProvider,
        ListView.MultiChoiceModeListener {

    private TextView mEmptyState;
    private ListView mListView;
    private FoodListAdapter mAdapter;
    private List<Food> mFoods = new ArrayList<Food>();

    public static FoodFragment newInstance() {
        FoodFragment fragment = new FoodFragment();
        return fragment;
    }

    public FoodFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    //================================================================================
    // Lifecycle Methods
    //================================================================================

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_food, container, false);

        mEmptyState = (TextView) view.findViewById(R.id.food_list_empty_state);
        mAdapter = new FoodListAdapter(getActivity(), this);
        mListView = (ListView) view.findViewById(R.id.food_list);
        configListView();

        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    //================================================================================
    // DataProvider Methods
    //================================================================================

    @Override
    public List<Food> getFoods() {
        return mFoods;
    }

    //================================================================================
    // FoodFragment Methods
    //================================================================================

    private void configListView() {
        mListView.setAdapter(mAdapter);
        mListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        mListView.setMultiChoiceModeListener(this);
        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                boolean state = mAdapter.isSelected(position);
                mListView.setItemChecked(position, !state);
                view.setActivated(!state);
                mAdapter.setSelected(position, !state);

                return state;
            }
        });
    }

    public void setFoods(List<Food> foods) {
        mFoods = foods;
        if (foods!=null && foods.size()>0) {
            mEmptyState.setVisibility(View.INVISIBLE);
            mListView.setVisibility(View.VISIBLE);
            mAdapter.notifyDataSetChanged();
        } else {
            mEmptyState.setVisibility(View.VISIBLE);
            mListView.setVisibility(View.INVISIBLE);
        }
    }

    private void deleteSelectedItems() {
        for (int i=0; i<mFoods.size(); i++) {
            if (mAdapter.isSelected(i)){
                Food food = mFoods.get(i);
                food = mAdapter.findModelById(food.apiId);
                if (food!=null) food.delete();
            }
        }
    }

    private void saveSelectedItems() {
        for (int i=0; i<mFoods.size(); i++) {
            if (mAdapter.isSelected(i)){
                Food food = mFoods.get(i);
                if (!mAdapter.isSaved(food.apiId)) {
                    food.setId(null);
                    food.save();
                }
            }
        }
    }

    //================================================================================
    // ActionMode.Callback Interface
    //================================================================================

    @Override
    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
        MenuInflater inflater = mode.getMenuInflater();
        inflater.inflate(R.menu.selection, menu);
        mAdapter.clearSelection();
        return true;
    }

    @Override
    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
        return false;
    }

    @Override
    public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
        // Respond to clicks on the actions in the CAB
        switch (item.getItemId()) {

            case R.id.action_delete:
                deleteSelectedItems();
                mode.finish();
                return true;

            case R.id.action_save:
                saveSelectedItems();
                mode.finish();
                return true;

            default:
                return false;
        }
    }

    @Override
    public void onDestroyActionMode(ActionMode mode) {
        mAdapter.notifyDataSetChanged();
        mAdapter.clearSelection();
    }

    @Override
    public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {
        Log.d("onItemCheckedStateChanged", "Position: " + position + ", Id: " + id);
    }
}
