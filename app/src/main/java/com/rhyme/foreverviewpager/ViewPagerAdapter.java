package com.rhyme.foreverviewpager;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

/**
 * Created by rhyme on 2017/9/30.
 * 轮播加载View
 */

public class ViewPagerAdapter extends PagerAdapter{
    private static final String TAG="ViewPagerAdapter";
    private List<View> viewList;
    private Context context;
    private ForeverViewPager.OnItemClickListener clickListener;
    ViewPagerAdapter(Context context, List<View> viewList, ForeverViewPager.OnItemClickListener clickListener){
        this.viewList=viewList;
        this.context=context;
        this.clickListener=clickListener;
    }
    @Override
    public int getCount() {
        return viewList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        if (viewList.get(position).getTag()!=null){
            final ImageView view= (ImageView) viewList.get(position);
            final View tagView= (View) view.getTag();
            tagView.post(new Runnable() {
                @Override
                public void run() {
                    view.setImageBitmap(loadBitmapFromViewBySystem(tagView));

                }
            });
            final int pos=position;
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (clickListener!=null){
                        clickListener.ClickItem(view,pos);
                    }
                }
            });
            container.addView(view);
            return view;
        }else {
            View view=viewList.get(position);
            final int pos=position-1;
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (clickListener!=null){
                        clickListener.ClickItem(view,pos);
                    }
                }
            });
            container.addView(view);
            return view;
        }

    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
    public static Bitmap loadBitmapFromViewBySystem(View v) {
        if (v == null) {
            return null;
        }
        v.setDrawingCacheEnabled(true);
        v.buildDrawingCache();
        return v.getDrawingCache();
    }
}
