package com.demo.ctw.ui.activity.mine;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;

import com.demo.ctw.R;
import com.demo.ctw.base.BaseMvpActivity;
import com.demo.ctw.entity.RequestObject;
import com.demo.ctw.key.DefaultCode;
import com.demo.ctw.listener.IRecyclerViewClickListener;
import com.demo.ctw.presenter.MinePresenter;
import com.demo.ctw.rx.Notice;
import com.demo.ctw.rx.NoticeCode;
import com.demo.ctw.ui.adapter.AddPicGridViewAdapter;
import com.demo.ctw.utils.CommonUtil;
import com.demo.ctw.utils.MatisseGlideEngine;
import com.demo.ctw.view.ILoadView;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.internal.entity.CaptureStrategy;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnTextChanged;

/**
 * 订单反馈
 */
public class OrderResponseActivity extends BaseMvpActivity<MinePresenter> implements ILoadView {
    @BindView(R.id.txt_title)
    TextView titleTxt;
    @BindView(R.id.edt_intro)
    EditText introEdt;
    @BindView(R.id.txt_length)
    TextView lengthTxt;
    @BindView(R.id.view_grid)
    GridView gridView;
    private AddPicGridViewAdapter adapter;
    private ArrayList<String> imgs = new ArrayList<>();
    private String id;

    @Override
    protected MinePresenter createPresenter() {
        return new MinePresenter(this);
    }

    @Override
    protected void loadViewLayout() {
        setContentView(R.layout.activity_order_response);
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
        titleTxt.setText("订单反馈");
        addImgs();
    }

    private void addImgs() {
        int width = getResources().getDisplayMetrics().widthPixels;
        int margin = getResources().getDimensionPixelSize(R.dimen.dimen_75);
        adapter = new AddPicGridViewAdapter(this, width - margin, 3, new IRecyclerViewClickListener() {
            @Override
            public void onItemClickListener(Object object, String type) {
                int position = (int) object;
                imgs.remove(position);
                adapter.notifyDataSetChanged();
            }
        }, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Matisse.from(OrderResponseActivity.this).choose(MimeType.ofAll()) //图片类型
                        .countable(false)   //true：选中后显示数字  false：选中后显示对号
                        .maxSelectable(3 - imgs.size())   //可选的最大数
                        .capture(true)      //是否显示拍照
                        .captureStrategy(new CaptureStrategy(true, "com.demo.jg.fileprovider"))
                        .imageEngine(new MatisseGlideEngine())     //图片加载引擎
                        .forResult(DefaultCode.ORDER_RESPONSE);
            }
        });
        gridView.setAdapter(adapter);
        adapter.setData(imgs);
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void getBundleExtras(Bundle extras) {
        super.getBundleExtras(extras);
        if (extras.containsKey("id")) {
            id = extras.getString("id");
        }
    }

    @Override
    public void loadData(Object object, String type) {
        switch (type) {
            case "imgs":
                imgs.addAll(CommonUtil.stringToList(object.toString(), ","));
                adapter.notifyDataSetChanged();
                break;
            case "res":
                showDialogDismiss();
                CommonUtil.toast("订单反馈提交成功");
                post(new Notice(NoticeCode.ORDER_RESPONSE));
                finish();
                break;
        }
    }

    @Override
    public void loadFail(String msg, String type) {
        showDialogDismiss();
        CommonUtil.toast(msg);
    }

    @OnClick({R.id.img_back, R.id.txt_submit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back:

                break;
            case R.id.txt_submit:
                String intro = introEdt.getText().toString();
                if (CommonUtil.isEmpty(intro)) {
                    CommonUtil.toast("请输入反馈内容");
                    return;
                }
                if (imgs == null || imgs.size() == 0) {
                    CommonUtil.toast("请添加图片说明");
                    return;
                }
                RequestObject object = new RequestObject();
                object.setProveVideo(CommonUtil.listToString(imgs));
                object.setRemark(intro);
                object.setId(id);
                mvpPresenter.orderResponse(object);
                showDialogLoading();
                break;
        }
    }

    @OnTextChanged(R.id.edt_intro)
    public void onTextChanged() {
        int length = introEdt.getText().toString().length();
        lengthTxt.setText(length + "/100");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == DefaultCode.ORDER_RESPONSE) {
                ArrayList<String> pics = (ArrayList<String>) Matisse.obtainPathResult(data);
                mvpPresenter.updateImg(pics);
            }
        }
    }
}