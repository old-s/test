package com.demo.ctw.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.demo.ctw.R;
import com.demo.ctw.listener.IRecyclerViewClickListener;
import com.demo.ctw.rx.ApiService;

import java.util.ArrayList;

/**
 * gridview适配器
 */
public class AddPicGridViewAdapter extends BaseAdapter {
    protected ArrayList<String> data;
    private Context context;
    private IRecyclerViewClickListener itemClickListener;            //删除点击
    private View.OnClickListener clickListener;
    private AbsListView.LayoutParams itemLayoutParams;
    private int maxNumber = 9;                                      //最大选择图片数目
    private int horizentalNum = 4;          //默认横向最大4张
    private int widthPixels;

    public void setMaxNumber(int maxNumber) {
        this.maxNumber = maxNumber;
    }

    public void setHorizentalNum(int horizentalNum) {
        this.horizentalNum = horizentalNum;
    }

    public AddPicGridViewAdapter(Context context, int widthPixels, IRecyclerViewClickListener listener, View.OnClickListener clickListener) {
        this.context = context;
        this.widthPixels = widthPixels;
        this.itemClickListener = listener;
        this.clickListener = clickListener;
        setItemWidth();
    }

    public AddPicGridViewAdapter(Context context, int widthPixels, int horizentalNum, IRecyclerViewClickListener listener, View.OnClickListener clickListener) {
        this.context = context;
        this.widthPixels = widthPixels;
        this.itemClickListener = listener;
        this.clickListener = clickListener;
        setHorizentalNum(horizentalNum);
        setItemWidth();
    }

    @Override
    public int getCount() {
        int length = data == null ? 0 : data.size();
        if (length < maxNumber) {
            length++;
        }
        return length;
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * 设置每一个Item的宽高
     */
    public void setItemWidth() {
        int horizentalNum = this.horizentalNum;
        int itemWidth = widthPixels / horizentalNum;
        this.itemLayoutParams = new AbsListView.LayoutParams(itemWidth, itemWidth);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (data.size() != maxNumber && position == data.size()) {
            TalkAddGridHolder photographHolder = new TalkAddGridHolder(parent.getContext(), clickListener);
            photographHolder.setLayoutParams(itemLayoutParams);
            return photographHolder;
        } else {
            TalkDeleteGridHolder holder = null;
            if (convertView == null || !(convertView instanceof TalkDeleteGridHolder)) {
                holder = new TalkDeleteGridHolder(parent.getContext());
                holder.setLayoutParams(itemLayoutParams);
                convertView = holder;
                convertView.setTag(holder);
            } else {
                holder = (TalkDeleteGridHolder) convertView;
            }
            String img = data.get(position);
            holder.fillData(img, position);
            return convertView;
        }
    }

    public void setData(ArrayList<String> data) {
        this.data = data;
    }

    class TalkAddGridHolder extends LinearLayout {
        private ImageView photoImg;                 //图片

        public TalkAddGridHolder(Context context) {
            this(context, null);
        }

        public TalkAddGridHolder(Context context, OnClickListener listener) {
            super(context);
            LayoutInflater.from(context).inflate(R.layout.item_add_pic, this, true);
            photoImg = findViewById(R.id.img_pic);
            photoImg.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickListener.onClick(v);
                }
            });
        }
    }

    class TalkDeleteGridHolder extends LinearLayout {

        private ImageView photoImg;                //图片
        private ImageView deleteBtn;                  //选中

        public TalkDeleteGridHolder(Context context) {
            super(context);
            LayoutInflater.from(context).inflate(R.layout.item_delete_pic, this, true);
            photoImg = findViewById(R.id.img_pic);
            deleteBtn = findViewById(R.id.img_delete);
        }

        /**
         * 设置数据
         *
         * @param object
         */
        public void fillData(String object, final int position) {
            if (object == null)
                return;
            deleteBtn.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemClickListener.onItemClickListener(position, "");
                }
            });
            Glide.with(getContext()).load(ApiService.API_SERVICE + object).apply(new RequestOptions().centerCrop()).into(photoImg);
        }
    }
}
