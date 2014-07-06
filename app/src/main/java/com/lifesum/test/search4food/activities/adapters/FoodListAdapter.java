package com.lifesum.test.search4food.activities.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.lifesum.test.search4food.R;
import com.lifesum.test.search4food.models.Food;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by David on 7/5/14.
 */
public class FoodListAdapter extends BaseAdapter {

    DataProvider mDataProvider;
    LayoutInflater mLayoutInflater;
    Context mContext;
    Map<Integer, Boolean> mSelected;

    //================================================================================
    // Constructors
    //================================================================================

    public FoodListAdapter(Context context, DataProvider dataProvider) {
        mContext = context;
        mDataProvider = dataProvider;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    //================================================================================
    // Adapter Methods
    //================================================================================

    @Override
    public int getCount() {
        return mDataProvider.getFoods().size();
    }

    @Override
    public Object getItem(int position) {
        return mDataProvider.getFoods().get(position);
    }

    @Override
    public long getItemId(int position) {
        return mDataProvider.getFoods().get(position).apiId;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View cell;
        final Food model = mDataProvider.getFoods().get(position);


        if (convertView == null) {
            cell = mLayoutInflater.inflate(R.layout.food_item_layout, null);
        } else {
            cell = convertView;
        }

        TextView titleText = (TextView) cell.findViewById(R.id.title_text);
        TextView categoryText = (TextView) cell.findViewById(R.id.category_text);
        TextView caloriesText = (TextView) cell.findViewById(R.id.calories_text);
        titleText.setText(model.title);
        categoryText.setText(model.category);
        caloriesText.setText(String.valueOf(model.calories) + " cal");

        ImageButton saveButton = (ImageButton)cell.findViewById(R.id.food_saved);
        saveButton.setImageResource(isSaved(model.apiId) ? R.drawable.ic_action_rating_important :
                R.drawable.ic_action_rating_not_important);
        saveButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                ImageButton btn = (ImageButton)v;
                Food food = findModelById(model.apiId);

                if (food!=null) {
                    food.delete();
                    btn.setImageResource(R.drawable.ic_action_rating_not_important);
                } else {
                    model.setId(null);
                    model.save();
                    btn.setImageResource(R.drawable.ic_action_rating_important);
                }
            }
        });

        cell.setLongClickable(true);

        return cell;
    }

    //================================================================================
    // DataProvider Interface
    //================================================================================

    public interface DataProvider {
        public List<Food> getFoods();
    }


    //================================================================================
    // FoodListAdapter Interface
    //================================================================================

    public boolean isSelected(int position) {
        if (mSelected==null) {
            mSelected = new HashMap<Integer, Boolean>();
        }
        return mSelected.get(position) != null ? mSelected.get(position) : false;
    }

    public void setSelected(int position, boolean selected) {
        if (mSelected==null) {
            mSelected = new HashMap<Integer, Boolean>();
        }
        mSelected.put(position, selected);
    }

    public void clearSelection() {
        mSelected.clear();
    }

    public boolean isSaved(long id) {
        List<Food> foods = Food.find(Food.class, "API_ID = ?", new String[]{String.valueOf(id)});
        return foods!=null && foods.size()>0;
    }

    public Food findModelById(long id) {
        List<Food> foods = Food.find(Food.class, "API_ID = ?", new String[]{String.valueOf(id)});
        if (foods!=null && foods.size()>0) {
            return foods.get(0);
        } else {
            return null;
        }
    }
}
