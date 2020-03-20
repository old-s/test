package com.demo.ctw.ui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.PointF;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.demo.ctw.R;
import com.demo.ctw.entity.BannerObject;
import com.demo.ctw.rx.ApiService;

import java.util.ArrayList;

/**
 * Created by admin on 2017/10/16.
 */

public class BannerAdView extends RelativeLayout {
    private final int MAX_SIZE = 10000;                             //最大显示数据
    private final int START_INDEX = 5000;
    private RadioGroup radioGroup;                                  //圆点
    private ViewPager viewPager;                                    //页面位置
    private ArrayList<BannerObject> arrayList;                      //广告内容数据
    private ArrayList<RadioButton> groupBtn = new ArrayList<>();
    private AdViewpagerAdapter adapter;                             //viewpager
    private AdItemClick onAdItemClick;                              //点击监听
    private PointF downP = new PointF();                            // 触摸时按下的点
    private PointF curP = new PointF();                             // 触摸时当前的点
    private long startTime = 0;                                     //开始时间
    private MyOnTouchListener onTouchListener;                      //触摸监听
    private int currentIndex = 0;                                   //开始位置
    private ViewPagerTask viewPagerTask;                            //循环任务
    private int mBannerType = 0;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            viewPager.setCurrentItem(currentIndex, true);
            startScroll();
        }
    };

    public interface AdItemClick {
        void onItemClick(BannerObject data, int position);
    }

    public BannerAdView(Context context) {
        this(context, null);
    }

    public BannerAdView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.BannerAdView, 0, 0);
        mBannerType = a.getInt(R.styleable.BannerAdView_type, 0);
        a.recycle();

        View view = LayoutInflater.from(context).inflate(R.layout.view_banner, this, false);
        viewPager = (ViewPager) view.findViewById(R.id.pager_banner);
        viewPager.setPageMargin(getResources().getDimensionPixelOffset(R.dimen.dimen_5));
        radioGroup = (RadioGroup) view.findViewById(R.id.radio_banner);
        this.addView(view);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                currentIndex = position;
                int index = getPosition(position);
                if (index < groupBtn.size()) {
                    RadioButton btn = groupBtn.get(index);
                    if (groupBtn != null)
                        btn.setChecked(true);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        viewPagerTask = new ViewPagerTask();
        onTouchListener = new MyOnTouchListener();
    }

    /**
     * 设置数据
     *
     * @param list
     */
    public void setAdList(ArrayList<BannerObject> list) {
        this.arrayList = list;
        if (arrayList == null || arrayList.isEmpty())
            return;
        initRadioGroup();
        currentIndex = START_INDEX - START_INDEX % list.size();
        adapter = new AdViewpagerAdapter();
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(currentIndex, false);
        viewPager.setOffscreenPageLimit(2);
        startScroll();
    }

    /**
     * 开始播放
     */
    public void startScroll() {
        stopScroll();
        mHandler.postDelayed(viewPagerTask, 3000);
    }

    /**
     * 停止播放
     */
    public void stopScroll() {
        mHandler.removeCallbacks(viewPagerTask);
    }

    /**
     * 设置广告点击
     *
     * @param onAdItemClick
     */
    public void setOnAdItemClick(AdItemClick onAdItemClick) {
        this.onAdItemClick = onAdItemClick;
    }

    private void initRadioGroup() {
        radioGroup.removeAllViews();
        groupBtn.clear();
        int width = getResources().getDimensionPixelOffset(R.dimen.dimen_8);
        int height = getResources().getDimensionPixelOffset(R.dimen.dimen_8);
        int left = getResources().getDimensionPixelOffset(R.dimen.dimen_4);
        for (int i = 0; i < arrayList.size(); i++) {
            RadioButton view = (RadioButton) LayoutInflater.from(getContext()).inflate(R.layout.view_round, radioGroup, false);
            RadioGroup.LayoutParams layoutParams = new RadioGroup.LayoutParams(width, height);
            layoutParams.setMargins(left, 0, left, 0);
            view.setLayoutParams(layoutParams);
            radioGroup.addView(view);
            groupBtn.add(view);
            if (i == 0) {
                view.setChecked(true);
            }
            view.setEnabled(false);
        }
    }

    private int getPosition(int position) {
        if (arrayList == null || arrayList.size() == 0)
            return 0;
        return position % arrayList.size();
    }


    private long lastUpTime = 0;

    public class MyOnTouchListener implements OnTouchListener {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            curP.x = event.getX();
            curP.y = event.getY();
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    startTime = System.currentTimeMillis();
                    mHandler.removeCallbacksAndMessages(null);
                    // 记录按下时候的坐标
                    // 切记不可用 downP = curP ，这样在改变curP的时候，downP也会改变
                    downP.x = event.getX();
                    downP.y = event.getY();
                    // 此句代码是为了通知他的父ViewPager现在进行的是本控件的操作，不要对我的操作进行干扰
                    getParent().requestDisallowInterceptTouchEvent(true);
                    break;
                case MotionEvent.ACTION_MOVE:
                    mHandler.removeCallbacks(viewPagerTask);
                    getParent().requestDisallowInterceptTouchEvent(false);
                    // 此句代码是为了通知他的父ViewPager现在进行的是本控件的操作，不要对我的操作进行干扰
                    break;
                case MotionEvent.ACTION_CANCEL:
                    startScroll();
                    break;
                case MotionEvent.ACTION_UP:
                    downP.x = event.getX();
                    downP.y = event.getY();
                    long time = System.currentTimeMillis();
                    if (time - lastUpTime > 1000) {
                        lastUpTime = time;
                        long duration = time - startTime;
                        if (duration <= 500 && downP.x == curP.x && onAdItemClick != null) {
                            BannerObject object = arrayList.get(getPosition(currentIndex));
                            if (object != null) {
                                onAdItemClick.onItemClick(object, getPosition(currentIndex));
                            }
                        }
                        startScroll();
                    }
                    break;
            }
            return true;
        }
    }

    public void onResume() {
        currentIndex++;
        mHandler.obtainMessage().sendToTarget();
    }

    class ViewPagerTask implements Runnable {
        @Override
        public void run() {
            currentIndex++;
            if (currentIndex > (MAX_SIZE - 100)) {
                currentIndex = START_INDEX - START_INDEX % arrayList.size();
            }
            mHandler.obtainMessage().sendToTarget();
        }
    }

    class AdViewpagerAdapter extends PagerAdapter {
        private int size = 0;

        public AdViewpagerAdapter() {
            if (arrayList != null)
                size = arrayList.size();
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            ImageView view = (ImageView) object;
            if (view != null) {
                container.removeView(view);
            }
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            final int index = getPosition(position);
            ImageView imageView = new ImageView(container.getContext());
            imageView.setLayoutParams(new LayoutParams(BannerAdView.this.getWidth(), BannerAdView.this.getHeight()));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            container.addView(imageView, 0);
            imageView.setOnTouchListener(onTouchListener);
            if (index < arrayList.size()) {
                BannerObject object = arrayList.get(index);
                if (object == null)
                    return null;

                Glide.with(container.getContext()).load(ApiService.API_SERVICE + object.getBanner_path()).into(imageView);
            } else {
                return null;
            }
            return imageView;
        }

        private int getPosition(int position) {
            if (position >= 9999 || position <= 0)
                position = 5000;
            return position % size;
        }

        @Override
        public int getCount() {
//            return MAX_SIZE;
            return 10000;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        try {
            super.onRestoreInstanceState(state);
        } catch (Exception e) {

        }
        state = null;
    }
}

