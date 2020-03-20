package com.demo.ctw.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
 * Created by qiu on 2018/9/6.
 */

public abstract class BaseFragment extends Fragment {
    private VaryViewHelperController controller = null;
    private LoadAlertDialog alertDialog;
    private boolean isFirstResume = true;
    private boolean isFirstVisible = true;
    private boolean isFirstInvisible = true;
    private boolean isPrepared = true;
    private Disposable disposable;
    private Context context;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) getBundleExtras(bundle);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (getContentViewLayoutID() != 0) return inflater.inflate(getContentViewLayoutID(), null);
        else return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        context = getContext();
        initPrePare();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
    }

    protected void initView(View view) {
        ButterKnife.bind(this, view);
        if (controller == null) {
            controller = new VaryViewHelperController(getLoadingTargetView());
        }
        processLogic();
        if (isBindRxBusHere()) {
            disposable = toObservable().observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<Notice>() {
                @Override
                public void accept(Notice notice) throws Exception {
                    dealRxbus(notice);
                }
            });
        }
        setListener();
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

    /**
     * return fragment layout
     *
     * @return
     */
    public abstract int getContentViewLayoutID();

    /**
     * request
     */
    protected abstract void processLogic();

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

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            if (isFirstVisible) {
                isFirstVisible = false;
                initPrePare();
            } else {
                onUserVisible();
            }
        } else {
            if (isFirstInvisible) {
                isFirstInvisible = false;
                onFirstUserInvisible();
            } else {
                onUserInvisible();
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (isFirstResume) {
            isFirstResume = false;
            return;
        }
        if (getUserVisibleHint())
            onUserVisible();

    }

    @Override
    public void onPause() {
        super.onPause();
        if (getUserVisibleHint())
            onUserVisible();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (disposable != null && !disposable.isDisposed())
            disposable.dispose();
    }

    protected synchronized void initPrePare() {
        if (isPrepared) onFirstUserVisible();
        else isPrepared = true;
    }

    /**
     * bundle data
     */
    protected void getBundleExtras(Bundle bundle) {

    }

    /**
     * when fragment is invisible for the first time
     */
    protected void onFirstUserInvisible() {
        // here we do not recommend do something
    }

    /**
     * when fragment is visible for the first time, here we can do some initialized work or refresh data only once
     */
    protected void onFirstUserVisible() {

    }

    /**
     * this method like the fragment's lifecycle method onResume()
     */
    protected void onUserVisible() {

    }

    /**
     * this method like the fragment's lifecycle method onPause()
     */
    protected void onUserInvisible() {

    }

    /**
     * show toast
     *
     * @param msg
     */
    protected void showToast(String msg) {
        if (!TextUtils.isEmpty(msg))
            Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    /**
     * startActivity
     *
     * @param clazz
     */
    public void readyGo(Class<?> clazz) {
        Intent intent = new Intent(getContext(), clazz);
        startActivity(intent);
    }

    /**
     * startActivity
     */
    public void readyGo(Class<?> clazz, Bundle bundle) {
        Intent intent = new Intent(getContext(), clazz);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    protected void readyGoForResult(Class<?> clazz, int requestCode, Bundle bundle) {
        Intent intent = new Intent(getActivity(), clazz);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
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
        alertDialog = new LoadAlertDialog(getContext());
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
