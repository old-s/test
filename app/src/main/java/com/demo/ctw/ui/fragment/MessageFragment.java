package com.demo.ctw.ui.fragment;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.demo.ctw.R;
import com.demo.ctw.base.BaseMvpFragment;
import com.demo.ctw.entity.MessageObject;
import com.demo.ctw.listener.IRecyclerViewClickListener;
import com.demo.ctw.presenter.MessagePresenter;
import com.demo.ctw.ui.adapter.MessageAdapter;
import com.demo.ctw.utils.CommonUtil;
import com.demo.ctw.view.ILoadView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * 消息
 */
public class MessageFragment extends BaseMvpFragment<MessagePresenter> implements ILoadView {
    @BindView(R.id.layout_refresh)
    SmartRefreshLayout refrshLayout;
    @BindView(R.id.list)
    RecyclerView list;
    private MessageAdapter adapter = new MessageAdapter();

    @Override
    public int getContentViewLayoutID() {
        return R.layout.fragment_message;
    }

    @Override
    protected void processLogic() {
        super.processLogic();
    }

    @Override
    protected void onFirstUserVisible() {
        super.onFirstUserVisible();
        refrshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                if (mvpPresenter.isMore()) mvpPresenter.moreMessageList();
                else refreshLayout.finishLoadMore();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mvpPresenter.messageList();
            }
        });
        list.setLayoutManager(new LinearLayoutManager(getContext()));
        list.setAdapter(adapter);
        mvpPresenter.messageList();
        showLoading();
    }

    @Override
    protected void setListener() {
        super.setListener();
        adapter.setOnItemClicktListener(new IRecyclerViewClickListener() {
            @Override
            public void onItemClickListener(Object object, String type) {
                CommonUtil.toast(((MessageObject) object).getContent());
            }
        });
    }

    @Override
    public void loadData(Object object, String type) {
        CommonUtil.endRefresh(refrshLayout);
        ArrayList<MessageObject> data = (ArrayList<MessageObject>) object;
        if (data.size() == 0 || data == null) showEmpty();
        else hideLoading();
        adapter.setItems(data);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void loadFail(String msg, String type) {
        CommonUtil.endRefresh(refrshLayout);
        CommonUtil.toast(msg);
        showError(msg, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mvpPresenter.messageList();
                showLoading();
            }
        });
    }

    @Override
    protected MessagePresenter createPresenter() {
        return new MessagePresenter(this);
    }

    @Override
    protected View getLoadingTargetView() {
        return list;
    }
}
