package com.demo.ctw.presenter;

import com.demo.ctw.base.BaseApplication;
import com.demo.ctw.base.BasePresenter;
import com.demo.ctw.entity.AboutObject;
import com.demo.ctw.entity.AuctionRecordObject;
import com.demo.ctw.entity.OrderOptionObject;
import com.demo.ctw.entity.PayRecordObject;
import com.demo.ctw.entity.RequestObject;
import com.demo.ctw.entity.UserObject;
import com.demo.ctw.key.ShareKey;
import com.demo.ctw.rx.AppClient;
import com.demo.ctw.rx.ObserverCallBack;
import com.demo.ctw.utils.CommonUtil;
import com.demo.ctw.utils.GsonTools;
import com.demo.ctw.utils.SharePrefUtil;
import com.demo.ctw.view.ILoadView;

import java.io.File;
import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class MinePresenter extends BasePresenter<ILoadView> {
    private ArrayList<PayRecordObject> payRecords = new ArrayList<>();
    private ArrayList<AuctionRecordObject> recordObjects = new ArrayList<>();
    private ArrayList<OrderOptionObject> optionObjects = new ArrayList<>();
    private int PAGESIZE = 20;
    private int offset = 0;
    private boolean isMore = false;

    public MinePresenter(ILoadView mvpView) {
        super(mvpView);
    }

    public boolean isMore() {
        return isMore;
    }

    /**
     * 获取个人信息
     */
    public void userInfo() {
        addSubscription(AppClient.getApiService().userInfo(CommonUtil.getRequest(new RequestObject())), new ObserverCallBack<UserObject>() {
            @Override
            protected void onSuccess(UserObject response) {
                SharePrefUtil.saveString(BaseApplication.getInstance(), ShareKey.USERINFO, GsonTools.createGsonString(response));
                if (mvpView != null) mvpView.loadData(response, "user");
            }

            @Override
            protected void onFail(String msg) {
                if (msg != null) mvpView.loadFail(msg, "user");
            }
        });
    }

    /**
     * 单张图片上传
     *
     * @param imgs
     * @param type
     */
    public void updateImg(String imgs, final String type) {
        File file = new File(imgs);
        RequestBody body = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part part = MultipartBody.Part.createFormData("file", file.getName(), body);
        addSubscription(AppClient.getApiService().updateImg(part), new ObserverCallBack() {
            @Override
            protected void onSuccess(Object response) {
                if (mvpView != null) mvpView.loadData(response, type);
            }

            @Override
            protected void onFail(String msg) {
                if (mvpView != null) mvpView.loadFail(msg, type);
            }
        });
    }

    /**
     * 多张图片上传
     *
     * @param imgs
     */
    public void updateImg(ArrayList<String> imgs) {
        MultipartBody.Part[] parts = new MultipartBody.Part[imgs.size()];
        for (int i = 0; i < imgs.size(); i++) {
            File file = new File(imgs.get(i));
            RequestBody body = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            MultipartBody.Part part = MultipartBody.Part.createFormData("file[]", file.getName(), body);
            parts[i] = part;
        }
        addSubscription(AppClient.getApiService().updateImg(parts), new ObserverCallBack() {

            @Override
            protected void onSuccess(Object response) {
                if (mvpView != null) mvpView.loadData(response.toString(), "imgs");
            }

            @Override
            protected void onFail(String msg) {
                if (mvpView != null) mvpView.loadFail(msg, "imgs");
            }
        });
    }

    /**
     * 修改个人信息
     *
     * @param object
     */
    public void updateUser(RequestObject object) {
        addSubscription(AppClient.getApiService().updateUser(CommonUtil.getRequest(object)), new ObserverCallBack<UserObject>() {
            @Override
            protected void onSuccess(UserObject response) {
                if (mvpView != null) mvpView.loadData(response, "update");
            }

            @Override
            protected void onFail(String msg) {
                if (msg != null) mvpView.loadFail(msg, "update");
            }
        });
    }

    public void payRecord(String date) {
        offset = 0;
        payRecord(date, offset);
    }

    public void morePayRecord(String date) {
        offset += PAGESIZE;
        payRecord(date, offset);
    }

    /**
     * 缴费记录
     *
     * @param date
     * @param offset
     */
    public void payRecord(String date, final int offset) {
        RequestObject object = new RequestObject();
        object.setDate(date);
        object.setLimit(PAGESIZE + "");
        object.setOffset(offset + "");
        addSubscription(AppClient.getApiService().payRecord(CommonUtil.getRequest(object)), new ObserverCallBack<ArrayList<PayRecordObject>>() {
            @Override
            protected void onSuccess(ArrayList<PayRecordObject> response) {
                if (offset == 0)
                    payRecords.clear();
                payRecords.addAll(response);
                if (mvpView != null) mvpView.loadData(payRecords, "pay");
                if (payRecords != null)
                    isMore = offset * PAGESIZE == payRecords.size();
            }

            @Override
            protected void onFail(String msg) {
                if (msg != null) mvpView.loadFail(msg, "pay");
            }
        });
    }

    public void auctionRecords(String type) {
        offset = 0;
        auctionRecords(type, offset);
    }

    public void moreAuctionRecords(String type) {
        offset += PAGESIZE;
        payRecord(type, offset);
    }

    /**
     * 缴费记录
     *
     * @param type
     * @param offset
     */
    public void auctionRecords(String type, final int offset) {
        RequestObject object = new RequestObject();
        object.setCompleteStatus(type);
        object.setLimit(PAGESIZE + "");
        object.setOffset(offset + "");
        addSubscription(AppClient.getApiService().auctionRecords(CommonUtil.getRequest(object)), new ObserverCallBack<ArrayList<AuctionRecordObject>>() {
            @Override
            protected void onSuccess(ArrayList<AuctionRecordObject> response) {
                if (offset == 0)
                    recordObjects.clear();
                recordObjects.addAll(response);
                if (mvpView != null) mvpView.loadData(recordObjects, "");
                if (recordObjects != null)
                    isMore = offset * PAGESIZE == recordObjects.size();
            }

            @Override
            protected void onFail(String msg) {
                if (msg != null) mvpView.loadFail(msg, "");
            }
        });
    }

    /**
     * 意见反馈
     *
     * @param object
     */
    public void option(RequestObject object) {
        addSubscription(AppClient.getApiService().option(CommonUtil.getRequest(object)), new ObserverCallBack() {
            @Override
            protected void onSuccess(Object response) {
                if (mvpView != null) mvpView.loadData(response, "option");
            }

            @Override
            protected void onFail(String msg) {
                if (msg != null) mvpView.loadFail(msg, "option");
            }
        });
    }


    /**
     * 关于我们
     */
    public void about() {
        addSubscription(AppClient.getApiService().about(CommonUtil.getRequest(new RequestObject())), new ObserverCallBack<AboutObject>() {
            @Override
            protected void onSuccess(AboutObject response) {
                if (mvpView != null) mvpView.loadData(response, "");
            }

            @Override
            protected void onFail(String msg) {
                if (mvpView != null) mvpView.loadFail(msg, "");
            }
        });
    }

    /**
     * 设置超市状态
     *
     * @param status
     */
    public void changeStatus(final String status) {
        RequestObject object = new RequestObject();
        object.setStatus(status);
        addSubscription(AppClient.getApiService().changeStatus(CommonUtil.getRequest(object)), new ObserverCallBack() {
            @Override
            protected void onSuccess(Object response) {
                if (mvpView != null) mvpView.loadData(response, status);
            }

            @Override
            protected void onFail(String msg) {
                if (mvpView != null) mvpView.loadFail(msg, status);
            }
        });
    }

    /**
     * 修改密码
     *
     * @param object
     */
    public void changePsw(RequestObject object) {
        addSubscription(AppClient.getApiService().changePsw(CommonUtil.getRequest(object)), new ObserverCallBack() {
            @Override
            protected void onSuccess(Object response) {
                if (mvpView != null) mvpView.loadData("", "changePsw");
            }

            @Override
            protected void onFail(String msg) {
                if (mvpView != null) mvpView.loadFail(msg, "changePsw");
            }
        });
    }

    public void orderOption(String status) {
        offset = 0;
        orderOption(status, offset);
    }

    public void moreOrderOption(String type) {
        offset += PAGESIZE;
        orderOption(type, offset);
    }

    /**
     * 订单反馈
     *
     * @param status
     */
    public void orderOption(String status, final int offset) {
        RequestObject object = new RequestObject();
        object.setInstallStatus(status.equals("0") ? "yes" : "no");
        object.setLimit(PAGESIZE + "");
        object.setOffset(offset + "");
        addSubscription(AppClient.getApiService().orderOption(CommonUtil.getRequest(object)), new ObserverCallBack<ArrayList<OrderOptionObject>>() {
            @Override
            protected void onSuccess(ArrayList<OrderOptionObject> response) {
                if (offset == 0)
                    optionObjects.clear();
                optionObjects.addAll(response);
                if (mvpView != null) mvpView.loadData(optionObjects, "");
                if (optionObjects != null)
                    isMore = offset * PAGESIZE == optionObjects.size();
            }

            @Override
            protected void onFail(String msg) {
                if (msg != null) mvpView.loadFail(msg, "");
            }
        });
    }

    /**
     * 提交订单反馈
     *
     * @param object
     */
    public void orderResponse(RequestObject object) {
        addSubscription(AppClient.getApiService().orderResponse(CommonUtil.getRequest(object)), new ObserverCallBack() {
            @Override
            protected void onSuccess(Object response) {
                if (mvpView != null) mvpView.loadData("", "res");
            }

            @Override
            protected void onFail(String msg) {
                if (msg != null) mvpView.loadFail(msg, "res");
            }
        });
    }
}
