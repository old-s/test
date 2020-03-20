package com.demo.ctw.ui.fragment;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.demo.ctw.R;
import com.demo.ctw.base.BaseMvpFragment;
import com.demo.ctw.entity.HomeItemObject;
import com.demo.ctw.entity.HomeObject;
import com.demo.ctw.presenter.HomePresenter;
import com.demo.ctw.rx.ApiService;
import com.demo.ctw.ui.activity.WebActivity;
import com.demo.ctw.ui.activity.home.HomeListActivity;
import com.demo.ctw.ui.adapter.CasesAdapter;
import com.demo.ctw.ui.adapter.NewsAdapter;
import com.demo.ctw.ui.view.BannerAdView;
import com.demo.ctw.ui.view.NoScrollGridView;
import com.demo.ctw.ui.view.NoScrollListView;
import com.demo.ctw.utils.CommonUtil;
import com.demo.ctw.view.ILoadView;

import butterknife.BindView;
import butterknife.OnClick;

public class HomeFragment extends BaseMvpFragment<HomePresenter> implements ILoadView {
    @BindView(R.id.scrollview)
    ScrollView scrollView;
    @BindView(R.id.view_banner)
    BannerAdView bannerView;
    @BindView(R.id.view_filpper)
    ViewFlipper filpperView;
    @BindView(R.id.list_news)
    NoScrollListView newsList;
    @BindView(R.id.grid_cases)
    NoScrollGridView casesGrid;

    private NewsAdapter newsAdapter = new NewsAdapter();
    private CasesAdapter casesAdapter = new CasesAdapter();
    private HomeObject homeObject;

    @Override
    public int getContentViewLayoutID() {
        return R.layout.fragment_home;
    }

    @Override
    protected void processLogic() {
        super.processLogic();
        int width = getResources().getDisplayMetrics().widthPixels;
        bannerView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, width / 5 * 2));
        newsList.setAdapter(newsAdapter);
        casesGrid.setAdapter(casesAdapter);
        mvpPresenter.homeList();
        showLoading();
    }

    @Override
    protected void setListener() {
        super.setListener();
        newsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                HomeItemObject itemObject = homeObject.getXwen().get(position);
                Bundle bundle = new Bundle();
                bundle.putString("url", ApiService.API_SERVICE_URL + itemObject.getId());
                bundle.putString("title", itemObject.getTitle());
                readyGo(WebActivity.class, bundle);
            }
        });
        casesGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CommonUtil.toast(homeObject.getCasew().get(position).getName());
            }
        });
    }

    @Override
    public void loadData(final Object object, String type) {
        hideLoading();
        homeObject = (HomeObject) object;
        bannerView.setAdList(homeObject.getBanner());

        for (int i = 0; i < homeObject.getGgao().size(); i++) {
            View view = View.inflate(getContext(), R.layout.item_filpper, null);
            TextView filpperTxt = view.findViewById(R.id.txt_filpper);
            filpperTxt.setText(homeObject.getGgao().get(i).getTitle());
            final int finalI = i;
            filpperTxt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    HomeItemObject itemObject = homeObject.getGgao().get(finalI);
                    Bundle bundle = new Bundle();
                    bundle.putString("url", ApiService.API_SERVICE_URL + itemObject.getId());
                    bundle.putString("title", itemObject.getTitle());
                    readyGo(WebActivity.class, bundle);
                }
            });
            filpperView.addView(view);
        }

        newsAdapter.setData(homeObject.getXwen());
        newsAdapter.notifyDataSetChanged();
        casesAdapter.setData(homeObject.getCasew());
        casesAdapter.notifyDataSetChanged();
    }

    @Override
    public void loadFail(String msg, String type) {
        CommonUtil.toast(msg);
        showError(msg, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mvpPresenter.homeList();
                showLoading();
            }
        });
    }

    @OnClick({R.id.txt_more, R.id.txt_news, R.id.txt_cases})
    public void onClick(View view) {
        Bundle bundle = new Bundle();
        switch (view.getId()) {
            case R.id.txt_more:
                bundle.putString("title", "公告");
                bundle.putString("type", "2");
                break;
            case R.id.txt_news:
                bundle.putString("title", "新闻");
                bundle.putString("type", "1");
                break;
            case R.id.txt_cases:
                bundle.putString("title", "合作案例");
                bundle.putString("type", "3");
                break;
        }
        readyGo(HomeListActivity.class, bundle);
    }

    @Override
    protected HomePresenter createPresenter() {
        return new HomePresenter(this);
    }

    @Override
    protected View getLoadingTargetView() {
        return scrollView;
    }
}
