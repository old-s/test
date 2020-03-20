package com.demo.ctw.presenter;

import com.demo.ctw.base.BasePresenter;
import com.demo.ctw.entity.MessageObject;
import com.demo.ctw.entity.RequestObject;
import com.demo.ctw.rx.AppClient;
import com.demo.ctw.rx.ObserverCallBack;
import com.demo.ctw.utils.CommonUtil;
import com.demo.ctw.view.ILoadView;

import java.util.ArrayList;

public class MessagePresenter extends BasePresenter<ILoadView> {
    private ArrayList<MessageObject> messageObjects = new ArrayList<>();
    private int PAGESIZE = 20;
    private int offset = 0;
    private boolean isMore = false;

    public MessagePresenter(ILoadView mvpView) {
        super(mvpView);
    }

    public boolean isMore() {
        return isMore;
    }

    public void messageList() {
        offset = 0;
        messageList(offset);
    }

    public void moreMessageList() {
        offset += PAGESIZE;
        messageList(offset);
    }

    /**
     * 消息列表
     *
     * @param offset
     */
    public void messageList(final int offset) {
        RequestObject object = new RequestObject();
        object.setLimit(PAGESIZE + "");
        object.setOffset(offset + "");
        addSubscription(AppClient.getApiService().messageList(CommonUtil.getRequest(object)), new ObserverCallBack<ArrayList<MessageObject>>() {
            @Override
            protected void onSuccess(ArrayList<MessageObject> response) {
                if (offset == 0)
                    messageObjects.clear();
                messageObjects.addAll(response);
                if (mvpView != null) mvpView.loadData(messageObjects, "pay");
                if (messageObjects != null)
                    isMore = offset * PAGESIZE == messageObjects.size();
            }

            @Override
            protected void onFail(String msg) {
                if (msg != null) mvpView.loadFail(msg, "pay");
            }
        });
    }
}
