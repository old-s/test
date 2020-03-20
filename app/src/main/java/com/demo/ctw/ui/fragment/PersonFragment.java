package com.demo.ctw.ui.fragment;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.demo.ctw.R;
import com.demo.ctw.base.BaseMvpFragment;
import com.demo.ctw.entity.UserObject;
import com.demo.ctw.key.ShareKey;
import com.demo.ctw.presenter.MinePresenter;
import com.demo.ctw.rx.ApiService;
import com.demo.ctw.rx.Notice;
import com.demo.ctw.rx.NoticeCode;
import com.demo.ctw.ui.activity.mine.AuctionRecordActivity;
import com.demo.ctw.ui.activity.mine.CompileUserActivity;
import com.demo.ctw.ui.activity.mine.MarketStatusActivity;
import com.demo.ctw.ui.activity.mine.OptionActivity;
import com.demo.ctw.ui.activity.mine.OrderOptionActivity;
import com.demo.ctw.ui.activity.mine.PayRecordActivity;
import com.demo.ctw.ui.activity.mine.SetActivity;
import com.demo.ctw.ui.view.BorderTextView;
import com.demo.ctw.utils.CommonUtil;
import com.demo.ctw.utils.GsonTools;
import com.demo.ctw.utils.SharePrefUtil;
import com.demo.ctw.view.ILoadView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 我
 */
public class PersonFragment extends BaseMvpFragment<MinePresenter> implements ILoadView {
    @BindView(R.id.img)
    ImageView img;
    @BindView(R.id.layout_ad)
    LinearLayout adLayout;
    @BindView(R.id.img_user)
    ImageView userImg;
    @BindView(R.id.txt_status)
    BorderTextView statusTxt;
    @BindView(R.id.txt_name)
    TextView nameTxt;
    @BindView(R.id.txt_tel)
    TextView telTxt;
    @BindView(R.id.txt_auction)
    TextView auctionTxt;
    @BindView(R.id.txt_pay)
    TextView payTxt;

    @BindView(R.id.txt_user)
    TextView userTxt;
    @BindView(R.id.txt_market)
    TextView marketTxt;

    private UserObject userObject;

    @Override
    public int getContentViewLayoutID() {
        return R.layout.fragment_person;
    }

    @Override
    protected void processLogic() {
        if (CommonUtil.isAd()) {
            img.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, getContext().getResources().getDimensionPixelOffset(R.dimen.dimen_170)));
            adLayout.setVisibility(View.VISIBLE);
            userTxt.setText("个人资料");
            marketTxt.setVisibility(View.GONE);
        } else {
            img.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, getContext().getResources().getDimensionPixelOffset(R.dimen.dimen_140)));
            adLayout.setVisibility(View.GONE);
            userTxt.setText("超市资料");
            marketTxt.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onFirstUserVisible() {
        super.onFirstUserVisible();
        String info = SharePrefUtil.getString(getContext(), ShareKey.USERINFO, "");
        if (!CommonUtil.isEmpty(info)) {
            userObject = GsonTools.changeGsonToBean(info, UserObject.class);
            loadUser();
        }
        mvpPresenter.userInfo();
        showDialogLoading();
    }

    private void loadUser() {
        nameTxt.setText(userObject.getNickName());
        Glide.with(getContext()).load(ApiService.API_SERVICE + userObject.getImg())
                .apply(RequestOptions.bitmapTransform(new CircleCrop()).placeholder(R.mipmap.ic_default_circle)).into(userImg);
        telTxt.setText(userObject.getTel());
        auctionTxt.setText(userObject.getAuctionNum());
        payTxt.setText(userObject.getPaymentNum());
        if (CommonUtil.isAd()) {
            statusTxt.setVisibility(View.GONE);
        } else {
            statusTxt.setVisibility(View.VISIBLE);
            if (userObject.getStatus().equals("1")) {
                statusTxt.setContentColorResource(R.color.app);
                statusTxt.setText("正常");
            } else {
                statusTxt.setContentColorResource(R.color.C4);
                statusTxt.setText("暂停");
            }
        }
    }

    @OnClick({R.id.layout_auction, R.id.layout_pay, R.id.txt_status,R.id.txt_market, R.id.txt_user, R.id.txt_set, R.id.txt_option})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.layout_auction:
                readyGo(AuctionRecordActivity.class);
                break;
            case R.id.layout_pay:
                readyGo(PayRecordActivity.class);
                break;
            case R.id.txt_status:
                readyGo(MarketStatusActivity.class);
                break;
            case R.id.txt_market:
                readyGo(OrderOptionActivity.class);
                break;
            case R.id.txt_user:
                Bundle bundle = new Bundle();
                bundle.putString("name", userObject.getNickName());
                bundle.putString("img", userObject.getImg());
                readyGo(CompileUserActivity.class, bundle);
                break;
            case R.id.txt_set:
                readyGo(SetActivity.class);
                break;
            case R.id.txt_option:
                readyGo(OptionActivity.class);
                break;
        }
    }

    @Override
    public void loadData(Object object, String type) {
        showDialogDismiss();
        userObject = (UserObject) object;
        loadUser();
    }

    @Override
    public void loadFail(String msg, String type) {
        showDialogDismiss();
        CommonUtil.toast(msg);
    }

    @Override
    protected boolean isBindRxBusHere() {
        return true;
    }

    @Override
    protected void dealRxbus(Notice notice) {
        super.dealRxbus(notice);
        if (notice.type == NoticeCode.UPDATE_USERINFO || notice.type == NoticeCode.UPDATE_ORDER || notice.type == NoticeCode.CHANGE_MARKET_STATUS) {
            mvpPresenter.userInfo();
        }
    }

    @Override
    protected MinePresenter createPresenter() {
        return new MinePresenter(this);
    }
}
