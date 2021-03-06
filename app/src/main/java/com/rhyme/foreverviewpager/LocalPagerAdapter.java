package com.rhyme.foreverviewpager;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.io.InputStream;

/**
 * Created by rhyme on 2017/9/30.
 * 本地加载图片
 */

public class LocalPagerAdapter extends PagerAdapter{
    private Integer[] images;
    private Context context;
    private int type;
    private ForeverViewPager.OnItemClickListener clickListener;
    LocalPagerAdapter(Context context, Integer[] images, int type, ForeverViewPager.OnItemClickListener clickListener){
        this.images=images;
        this.context=context;
        this.type=type;
        this.clickListener=clickListener;
    }

    @Override
    public int getCount() {
        return images.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        final ImageView imageView=new ImageView(context);
        BitmapFactory.Options options=new BitmapFactory.Options();
        options.inPurgeable=true;
        options.inInputShareable=true;
        options.inPreferredConfig= Bitmap.Config.RGB_565;
        InputStream is=imageView.getContext().getResources().openRawResource(images[position]);

        imageView.setImageBitmap(BitmapFactory.decodeStream(is,null,options));
        switch (type){
            case 0:
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                break;
            case 1:
                imageView.setScaleType(ImageView.ScaleType.CENTER);
                break;
            case 2:
                imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                break;
            case 3:
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                break;
        }
        final int pos=position-1;
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (clickListener!=null){
                    clickListener.ClickItem(imageView,pos);
                }
            }
        });
        container.addView(imageView);
        return imageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);

    }
}
