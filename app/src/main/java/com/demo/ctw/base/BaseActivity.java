package com.demo.ctw.base;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.demo.ctw.loading.LoadAlertDialog;
import com.demo.ctw.loading.VaryViewHelperController;
import com.demo.ctw.rx.Notice;
import com.demo.ctw.rx.RxBus;

import butterknife.ButterKnife;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by qiu on 2018/9/5.
 */

public abstract class BaseActivity extends AppCompatActivity {
    private VaryViewHelperController controller;
    private LoadAlertDialog alertDialog;
    private Disposable disposable;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (reqFullScreen()) {
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
        // 设定始终保持竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            getBundleExtras(extras);
        }
        if (isBindRxBusHere()) {
            disposable = toObservable().observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<Notice>() {
                @Override
                public void accept(Notice notice) throws Exception {
                    dealRxbus(notice);
                }
            });
        }
        initView(savedInstanceState);

        BaseApplication.getInstance().setActivityContext(this);
        ActivityManager.getInstance().addActivity(this);
    }

    /**
     * bundle data
     *
     * @param extras
     */
    protected void getBundleExtras(Bundle extras) {

    }

    /**
     * bind rxbus
     *
     * @return
     */
    protected boolean isBindRxBusHere() {
        return false;
    }

    /**
     * rxbus
     *
     * @param notice
     */
    protected void dealRxbus(Notice notice) {

    }

    /**
     * register notice
     */
    public Flowable<Notice> toObservable() {
        return RxBus.getDefault().toObservable(Notice.class);
    }

    /**
     * post message
     */
    public void post(Notice msg) {
        RxBus.getDefault().post(msg);
    }

    protected boolean reqFullScreen() {
        return false;
    }

    /**
     * init view
     *
     * @param savedInstanceState
     */
    private void initView(Bundle savedInstanceState) {
        loadViewLayout();
        ButterKnife.bind(this);
        if (getLoadingTargetView() != null) {
            controller = new VaryViewHelperController(getLoadingTargetView());
        }
        processLogic(savedInstanceState);
        setListener();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (disposable != null && !disposable.isDisposed())
            disposable.dispose();
    }

    /**
     * load layout
     */
    protected abstract void loadViewLayout();


    /**
     * request
     */
    protected abstract void processLogic(Bundle savedInstanceState);

    /**
     * listener
     */
    protected void setListener() {
    }

    /**
     * get loading target view
     */
    protected View getLoadingTargetView() {
        return null;
    }


    /**
     * show toast
     *
     * @param msg
     */
    protected void showToast(String msg) {
        if (!TextUtils.isEmpty(msg))
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    /**
     * startActivity
     *
     * @param clazz
     */
    public void readyGo(Class<?> clazz) {
        Intent intent = new Intent(this, clazz);
        startActivity(intent);
    }

    /**
     * startActivity
     */
    public void readyGo(Class<?> clazz, Bundle bundle) {
        Intent intent = new Intent(this, clazz);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    /**
     * startActivity then finish
     *
     * @param clazz
     */
    public void readyGoThenKill(Class<?> clazz) {
        Intent intent = new Intent(this, clazz);
        startActivity(intent);
        finish();
    }

    /**
     * startActivity then finish
     *
     * @param clazz
     */
    public void readyGoThenKill(Class<?> clazz, Bundle bundle) {
        Intent intent = new Intent(this, clazz);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
        finish();
    }

    /**
     * show loading
     */
    protected void showLoading() {
        if (null == controller) {
            throw new IllegalArgumentException("You must return a right target view for loading");
        }
        controller.showLoading("");
    }

    /**
     * show loading
     */
    protected void showLoading(String msg) {
        if (null == controller) {
            throw new IllegalArgumentException("You must return a right target view for loading");
        }
        controller.showLoading(msg);
    }

    /**
     * hide loading
     */
    protected void hideLoading() {
        if (null == controller) {
            throw new IllegalArgumentException("You must return a right target view for loading");
        }
        controller.restore();
    }

    /**
     * show empty
     */
    protected void showEmpty() {
        if (null == controller) {
            throw new IllegalArgumentException("You must return a right target view for loading");
        }
        controller.showEmpty("");
    }

    /**
     * show empty
     */
    protected void showEmpty(String msg) {
        if (null == controller) {
            throw new IllegalArgumentException("You must return a right target view for loading");
        }
        controller.showEmpty(msg);
    }

    /**
     * show error
     */
    protected void showError(String msg, View.OnClickListener onClickListener) {
        if (null == controller) {
            throw new IllegalArgumentException("You must return a right target view for loading");
        }
        controller.showError(msg, onClickListener);
    }

    /**
     * show network error
     */
    protected void showNetError(View.OnClickListener onClickListener) {
        if (null == controller) {
            throw new IllegalArgumentException("You must return a right target view for loading");
        }
        controller.showNetError(onClickListener);
    }

    /**
     * dialog loading
     */
    protected void showDialogLoading() {
        alertDialog = new LoadAlertDialog(this);
        alertDialog.show();
        alertDialog.setCancelable(true);
        alertDialog.setCanceledOnTouchOutside(true);
        alertDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
    }

    /**
     * dialog dismiss
     */
    protected void showDialogDismiss() {
        if (alertDialog != null)
            alertDialog.dismiss();
    }
}
