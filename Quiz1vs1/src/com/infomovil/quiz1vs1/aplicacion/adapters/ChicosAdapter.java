package com.infomovil.quiz1vs1.aplicacion.adapters;

import com.infomovil.quiz1vs1.R;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class ChicosAdapter extends BaseAdapter {
    private Context mContext;

    public ChicosAdapter(Context c) {
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
            imageView.setPadding(4, 4, 4, 4);
        } else {
            imageView = (ImageView) convertView;
        }
        
        imageView.setBackgroundResource(mThumbIds[position]);
        //imageView.setImageResource(mThumbIds[position]);
        return imageView;
    }

    // references to our images
    private Integer[] mThumbIds = {
    	R.drawable.chico1,R.drawable.chico2,R.drawable.chico3,R.drawable.chico4,R.drawable.chico5,
    	R.drawable.chico6,R.drawable.chico7,R.drawable.chico8,R.drawable.chico9,R.drawable.chico10,
    	R.drawable.chico11,R.drawable.chico12,R.drawable.chico13,
    };
    
    
}
