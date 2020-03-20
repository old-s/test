package com.demo.ctw.presenter;

import com.demo.ctw.base.BaseApplication;
import com.demo.ctw.base.BasePresenter;
import com.demo.ctw.entity.AuthObject;
import com.demo.ctw.entity.BrandObject;
import com.demo.ctw.entity.ProveFormworkObject;
import com.demo.ctw.entity.RequestObject;
import com.demo.ctw.entity.UserObject;
import com.demo.ctw.key.ShareKey;
import com.demo.ctw.rx.AppClient;
import com.demo.ctw.rx.ObserverCallBack;
import com.demo.ctw.utils.CommonUtil;
import com.demo.ctw.utils.SharePrefUtil;
import com.demo.ctw.view.ILoadView;

import java.io.File;
import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class LoginPresenter extends BasePresenter<ILoadView> {
    public LoginPresenter(ILoadView mvpView) {
        super(mvpView);
    }

    /**
     * 获取token
     */
    public void token() {
        addSubscription(AppClient.getApiService().token("admin", "admin"), new ObserverCallBack<AuthObject>() {
            @Override
            protected void onSuccess(AuthObject response) {
                if (mvpView != null) mvpView.loadData(response, "token");
                SharePrefUtil.saveString(BaseApplication.getInstance(), ShareKey.RANDOMKEY, response.getRandomKey());
                SharePrefUtil.saveString(BaseApplication.getInstance(), "Bearer " + ShareKey.TOKEN, response.getToken());
                BaseApplication.getInstance().setToken("Bearer " + response.getToken());
            }

            @Override
            protected void onFail(String msg) {
                if (mvpView != null) mvpView.loadFail(msg, "token");
            }
        });
    }

    /**
     * 判断手机号是否被占用
     *
     * @param object
     */
    public void telIsUse(RequestObject object) {
        addSubscription(AppClient.getApiService().telIsUse(CommonUtil.getRequest(object)), new ObserverCallBack() {
            @Override
            protected void onSuccess(Object response) {
                if (mvpView != null) mvpView.loadData(response, "isUse");
            }

            @Override
            protected void onFail(String msg) {
                if (msg != null) mvpView.loadFail(msg, "isUse");
            }
        });
    }

    /**
     * 注册
     *
     * @param object
     */
    public void register(RequestObject object) {
        addSubscription(AppClient.getApiService().register(CommonUtil.getRequest(object)), new ObserverCallBack<UserObject>() {
            @Override
            protected void onSuccess(UserObject response) {
                if (mvpView != null) mvpView.loadData(response, "register");
            }

            @Override
            protected void onFail(String msg) {
                if (msg != null) mvpView.loadFail(msg, "register");
            }
        });
    }

    /**
     * 登录
     *
     * @param object
     */
    public void login(RequestObject object) {
        addSubscription(AppClient.getApiService().login(CommonUtil.getRequest(object)), new ObserverCallBack<UserObject>() {
            @Override
            protected void onSuccess(UserObject response) {
                if (mvpView != null) mvpView.loadData(response, "login");
            }

            @Override
            protected void onFail(String msg) {
                if (msg != null) mvpView.loadFail(msg, "login");
            }
        });
    }

    /**
     * 忘记密码
     *
     * @param object
     */
    public void forgetPsw(RequestObject object) {
        addSubscription(AppClient.getApiService().forgetPsw(CommonUtil.getRequest(object)), new ObserverCallBack<UserObject>() {
            @Override
            protected void onSuccess(UserObject response) {
                if (mvpView != null) mvpView.loadData(response, "forget");
            }

            @Override
            protected void onFail(String msg) {
                if (msg != null) mvpView.loadFail(msg, "forget");
            }
        });
    }

    /**
     * 超市入住-》品牌列表
     */
    public void branList() {
        addSubscription(AppClient.getApiService().brandList(), new ObserverCallBack<ArrayList<BrandObject>>() {
            @Override
            protected void onSuccess(ArrayList<BrandObject> response) {
                if (mvpView != null) mvpView.loadData(response, "brand");
            }

            @Override
            protected void onFail(String msg) {
                if (msg != null) mvpView.loadFail(msg, "brand");
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
     * 上传极光注册id
     *
     * @param jpushId
     */
    public void jpushId(String jpushId) {
        RequestObject object = new RequestObject();
        object.setPushid(jpushId);
        addSubscription(AppClient.getApiService().jpushId(CommonUtil.getRequest(object)), new ObserverCallBack() {
            @Override
            protected void onSuccess(Object response) {
                if (mvpView != null) mvpView.loadData("", "jpush");
            }

            @Override
            protected void onFail(String msg) {
                if (mvpView != null) mvpView.loadFail(msg, "jpush");
            }
        });
    }

    /**
     * 证明模板
     */
    public void proveFormwork() {
        addSubscription(AppClient.getApiService().proveFormwork(), new ObserverCallBack<ProveFormworkObject>() {
            @Override
            protected void onSuccess(ProveFormworkObject response) {
                if (mvpView != null) mvpView.loadData(response, "proveFormwork");
            }

            @Override
            protected void onFail(String msg) {
                if (mvpView != null) mvpView.loadFail(msg, "proveFormwork");
            }
        });
    }

    /**
     * 退出登录
     */
    public void cancel() {
        addSubscription(AppClient.getApiService().cancel(CommonUtil.getRequest(new RequestObject())), new ObserverCallBack() {
            @Override
            protected void onSuccess(Object response) {
                if (mvpView != null) mvpView.loadData(response, "proveFormwork");
            }

            @Override
            protected void onFail(String msg) {
                if (mvpView != null) mvpView.loadFail(msg, "proveFormwork");
            }
        });
    }

    public void reqCode(RequestObject object) {
        addSubscription(AppClient.getApiService().reqCode(CommonUtil.getRequest(object)), new ObserverCallBack() {
            @Override
            protected void onSuccess(Object response) {
                if (mvpView != null) mvpView.loadData(response, "code");
            }

            @Override
            protected void onFail(String msg) {
                if (msg != null) mvpView.loadFail(msg, "code");
            }
        });
    }
}
