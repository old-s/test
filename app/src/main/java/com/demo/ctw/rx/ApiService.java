package com.demo.ctw.rx;

import com.demo.ctw.entity.AboutObject;
import com.demo.ctw.entity.AuctionDetailObject;
import com.demo.ctw.entity.AuctionObject;
import com.demo.ctw.entity.AuctionRecordObject;
import com.demo.ctw.entity.AuthObject;
import com.demo.ctw.entity.BrandObject;
import com.demo.ctw.entity.HomeItemObject;
import com.demo.ctw.entity.HomeObject;
import com.demo.ctw.entity.MarketObject;
import com.demo.ctw.entity.MessageObject;
import com.demo.ctw.entity.OrderOptionObject;
import com.demo.ctw.entity.PayRecordObject;
import com.demo.ctw.entity.ProveFormworkObject;
import com.demo.ctw.entity.RequestObject;
import com.demo.ctw.entity.SincerityMoneyObject;
import com.demo.ctw.entity.UserObject;
import com.demo.ctw.pay.AlipyInfoObject;
import com.demo.ctw.pay.WechatInfoObject;

import java.util.ArrayList;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * Created by qiu on 2018/9/6.
 */

public interface ApiService {
    //孟
    String API_SERVICE = "http://47.114.116.231/";
    // 正式
//    String API_SERVICE = "http://123.232.114.60:9090/adv/";
    //    String API_SERVICE = API_SERVICE_IMG + "/";
    String API_SERVICE_IMG = "http://123.232.114.60:9090";
    String API_SERVICE_URL = API_SERVICE + "/new/detail/";

    @FormUrlEncoded
    @POST("sqApi/auth")
    Observable<ResultResponse<AuthObject>> token(@Field("userName") String userName, @Field("password") String password);

    //--------------------------------------登录注册---------------------------------

    /**
     * 注册 判断手机号是否可使用
     *
     * @param object
     * @return
     */
    @POST("sqApi/member/audit")
    Observable<ResultResponse> telIsUse(@Body RequestObject object);

    /**
     * 注册
     *
     * @param object
     * @return
     */
    @POST("sqApi/member/add")
    Observable<ResultResponse<UserObject>> register(@Body RequestObject object);

    /**
     * 获取验证码
     * @param object
     * @return
     */
    @POST("/sqApi/member/phone")
    Observable<ResultResponse> reqCode(@Body RequestObject object);

    /**
     * 登录
     *
     * @param object
     * @return
     */
    @POST("sqApi/member/login")
    Observable<ResultResponse<UserObject>> login(@Body RequestObject object);

    /**
     * 退出登录
     *
     * @param object
     * @return
     */
    @POST("sqApi/member/loginout")
    Observable<ResultResponse> cancel(@Body RequestObject object);

    /**
     * 上传极光注册id
     *
     * @param object
     * @return
     */
    @POST("sqApi/member/pushregister")
    Observable<ResultResponse> jpushId(@Body RequestObject object);

    /**
     * 忘记密码
     *
     * @param object
     * @return
     */
    @POST("sqApi/member/forgetPassword")
    Observable<ResultResponse<UserObject>> forgetPsw(@Body RequestObject object);

    /**
     * 品牌下拉
     *
     * @return
     */
    @POST("sqApi/brand/list")
    Observable<ResultResponse<ArrayList<BrandObject>>> brandList();

    /**
     * 证明模板
     *
     * @return
     */
    @POST("sqApi/templet/list")
    Observable<ResultResponse<ProveFormworkObject>> proveFormwork();

    //--------------------------------------首页---------------------------------

    /**
     * 首页列表
     *
     * @param object
     * @return
     */
    @POST("sqApi/banner/firstPage")
    Observable<ResultResponse<HomeObject>> homeList(@Body RequestObject object);

    /**
     * 新闻/广告列表
     *
     * @param object
     * @return
     */
    @POST("sqApi/article/list")
    Observable<ResultResponse<ArrayList<HomeItemObject>>> lists(@Body RequestObject object);

    /**
     * 案例列表
     *
     * @param object
     * @return
     */
    @POST("sqApi/casew/list")
    Observable<ResultResponse<ArrayList<HomeItemObject>>> caseLists(@Body RequestObject object);

    /**
     * 公告新闻详情
     *
     * @param object
     * @return
     */
    @POST("new/detail")
    Observable<ResultResponse> itemDetail(@Body RequestObject object);

    //--------------------------------------竞拍---------------------------------

    /**
     * 列表
     *
     * @param object
     * @return
     */
    @POST("sqApi/member/marketList")
    Observable<ResultResponse<ArrayList<AuctionObject>>> auctionList(@Body RequestObject object);

    /**
     * 超市详情
     *
     * @return
     */
    @POST("sqApi/member/detail")
    Observable<ResultResponse<MarketObject>> marketDetail(@Body RequestObject object);

    /**
     * 诚意金
     *
     * @return
     */
    @POST("sqApi/auction/earnestMoney")
    Observable<ResultResponse<SincerityMoneyObject>> sincerityMoney(@Body RequestObject object);

    /**
     * 提交订单
     *
     * @return
     */
    @POST("sqApi/auction/payorder")
    Observable<ResultResponse> submitOrder(@Body RequestObject object);

    /**
     * 订单详情
     *
     * @return
     */
    @POST("sqApi/auction/detailByOrderno")
    Observable<ResultResponse<AuctionDetailObject>> orderDetail(@Body RequestObject object);

    //--------------------------------------消息---------------------------------

    /**
     * 消息列表
     *
     * @param object
     * @return
     */
    @POST("sqApi/push/list")
    Observable<ResultResponse<ArrayList<MessageObject>>> messageList(@Body RequestObject object);
    //--------------------------------------我---------------------------------

    /**
     * 获取个人信息
     *
     * @param object
     * @return
     */
    @POST("sqApi/member/mydetails")
    Observable<ResultResponse<UserObject>> userInfo(@Body RequestObject object);

    /**
     * 修改个人信息
     *
     * @param object
     * @return
     */
    @POST("sqApi/member/update")
    Observable<ResultResponse<UserObject>> updateUser(@Body RequestObject object);

    /**
     * 缴费记录
     *
     * @param object
     * @return
     */
    @POST("sqApi/payment/list")
    Observable<ResultResponse<ArrayList<PayRecordObject>>> payRecord(@Body RequestObject object);


    /**
     * 竞拍记录
     *
     * @param object
     * @return
     */
    @POST("sqApi/auction/list")
    Observable<ResultResponse<ArrayList<AuctionRecordObject>>> auctionRecords(@Body RequestObject object);

    /**
     * 超市反馈
     *
     * @param object
     * @return
     */
    @POST("sqApi/auction/installist")
    Observable<ResultResponse<ArrayList<OrderOptionObject>>> orderOption(@Body RequestObject object);

    /**
     * 提交订单反馈
     *
     * @param object
     * @return
     */
    @POST("sqApi/auction/install")
    Observable<ResultResponse> orderResponse(@Body RequestObject object);

    /**
     * 意见反馈
     *
     * @param object
     * @return
     */
    @POST("sqApi/feedback/add")
    Observable<ResultResponse> option(@Body RequestObject object);

    /**
     * 关于我们
     *
     * @param object
     * @return
     */
    @POST("sqApi/about/list")
    Observable<ResultResponse<AboutObject>> about(@Body RequestObject object);

    /**
     * 设置超市状态
     *
     * @param object
     * @return
     */
    @POST("sqApi/member/update")
    Observable<ResultResponse> changeStatus(@Body RequestObject object);

    /**
     * 修改密码
     *
     * @param object
     * @return
     */
    @POST("sqApi/member/upPassword")
    Observable<ResultResponse<UserObject>> changePsw(@Body RequestObject object);
    //--------------------------------------上传图片---------------------------------

    /**
     * 上传图片列表
     */
    @Multipart
    @POST("file/uploads")
    Observable<ResultResponse> updateImg(@Part MultipartBody.Part[] file);

    /**
     * 上传图片
     */
    @Multipart
    @POST("file/upload")
    Observable<ResultResponse> updateImg(@Part MultipartBody.Part file);

    //--------------------------------------上传图片---------------------------------

    /**
     * 支付信息
     *
     * @return
     */
    @POST("payment")
    Observable<ResultResponse<AlipyInfoObject>> payInfo(@Body RequestObject object);

    /**
     * 支付宝支付状态
     *
     * @return
     */
    @FormUrlEncoded
    @POST("sys/zhifubaoRersult.shtml")
    Observable<ResultResponse> alipayResult(@Body RequestObject object);

    /**
     * 微信支付信息
     *
     * @return
     */
    @POST("payment")
    Observable<ResultResponse<WechatInfoObject>> wechatInfo(@Body RequestObject object);

    /**
     * 微信支付状态
     *
     * @return
     */
    @POST("sys/weixinRersult.shtml")
    Observable<ResultResponse> wechatResult(@Body RequestObject object);

}
