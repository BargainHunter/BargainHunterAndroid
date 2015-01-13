package com.bargainhunter.bargainhunterandroid.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.bargainhunter.bargainhunterandroid.R;
import com.bargainhunter.bargainhunterandroid.models.entities.Category;

import java.util.List;

/**
 * Created by tommy on 28/12/2014.
 */
public class ImageAdapter extends BaseAdapter {
    private Context context;
    private List<Category> categories;

    public ImageAdapter(Context context, List<Category> categories) {
        this.context = context;
        this.categories = categories;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View gridView;

        if (convertView == null) {

            gridView = new View(context);

            // get layout from mobile.xml
            gridView = inflater.inflate(R.layout.fragment_category_grid_view_custom_item, null);

            // get the category description
            String categoryDescription = categories.get(position).getDescription();

            // set value into textview
            TextView textView = (TextView) gridView
                    .findViewById(R.id.grid_item_label);
            textView.setText(categoryDescription);

            // set image based on selected text
            ImageView imageView = (ImageView) gridView
                    .findViewById(R.id.grid_item_image);

            if (categoryDescription.equals("Ηλεκτρονικά")) {
                imageView.setImageResource(R.drawable.electronics);
            } else if (categoryDescription.equals("Τρόφημα")) {
                imageView.setImageResource(R.drawable.food);
            }
        } else {
            gridView = (View) convertView;
        }

        return gridView;
    }

    @Override
    public int getCount() {
        return categories.size();
    }

    @Override
    public Object getItem(int position) {
        return categories.get(position);
    }

    @Override
    public long getItemId(int position) {
        return ((Category) getItem(position)).getCategoryId();
    }

}
