package com.infomovil.quiz1vs1.aplicacion.adapters;

import com.infomovil.quiz1vs1.R;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class ChicasAdapter extends BaseAdapter {
    private Context mContext;

    public ChicasAdapter(Context c) {
        mContext = c;
    }

    public int getCount() {
        return mThumbIds.length;
    }

    public Object getItem(int position) {
        return mContext.getResources().getDrawable(mThumbIds[position]);
    }

    public long getItemId(int position) {
        return mThumbIds[position];
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {  // if it's not recycled, initialize some attributes
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(85, 85));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(8, 8, 8, 8);
        } else {
            imageView = (ImageView) convertView;
        }
        imageView.setBackgroundResource(mThumbIds[position]);
        //imageView.setImageResource(mThumbIds[position]);
        return imageView;
    }

    // references to our images
    private Integer[] mThumbIds = {
    	R.drawable.chica1,R.drawable.chica2,R.drawable.chica3,R.drawable.chica4,R.drawable.chica5,
    	R.drawable.chica6,R.drawable.chica7,R.drawable.chica8,R.drawable.chica9,R.drawable.chica10,
    	R.drawable.chica11,R.drawable.chica12,R.drawable.chica13,R.drawable.chica14,
    };
}
