package com.example.amit.viewpagerexample;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by AMIT on 16-03-2018.
 */

public class WordAdapter extends ArrayAdapter<Word> {
    private int mColorReasourceId;
    WordAdapter(Context context, ArrayList<Word> list, int acolor) {
        super(context, 0, list);
        mColorReasourceId=acolor;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }

        Word currentItem = getItem(position);

        TextView miwokTranslation = (TextView) listItemView.findViewById(R.id.miwok_text_view);
        miwokTranslation.setText(currentItem.getmMiwokTranslation());

        TextView defaultTranslation = (TextView) listItemView.findViewById(R.id.default_text_view);
        defaultTranslation.setText(currentItem.getmDefaultTranslation());

        ImageView itemIcon= (ImageView) listItemView.findViewById(R.id.image);
        if(currentItem.hasImage()) {
            itemIcon.setImageResource(currentItem.getResourceId());
            itemIcon.setVisibility(View.VISIBLE);
        }
        else {
            ///so we can reuse it
            itemIcon.setVisibility(View.GONE);
        }

        //setting color
        View textContainer=listItemView.findViewById(R.id.text_container);
        int color= ContextCompat.getColor(getContext(),mColorReasourceId);
        textContainer.setBackgroundColor(color);

        return listItemView;

    }

}
