package com.demo.ctw.entity;

import com.demo.ctw.base.BaseApplication;

public class RequestObject {
    private String userId = BaseApplication.getInstance().getUserId();
    private String pushid;
    private String sign;
    private String object;

    private String offset;
    private String limit;

    private String tel;            //注册手机号
    private String code;
    private String telCode;            //注册账号验证码
    private String password;            //密码

    private String companyName;            //店名
    private String type;            //用户类型  1超市 3超市总部 2广告
    private String brank;            //超市品牌
    private String phone;            //联系电话
    private String region;            //所在地区
    private String address;            //具体地址
    private String comType;            //超市类型 1连锁 2单店
    private String area;            //营业面积
    private String areaimg;            //营业面积证明文件
    private String shopcart;            //推车数量
    private String shopcartimg;            //推车数量证明文件
    private String advScreen;            //购物筐数量
    private String footfall;            //日客流量
    private String turnover;            //年营业额
    private String companyBank;            //公司名称
    private String bank;            //开户行
    private String bankNum;            //账号
    private String businessLicense;            //营业执照
    private String companyImg;            //店面照片
    private String cardimgo;            //身份证正面
    private String cardimgt;            //身份证反面
    private String personName;            //负责人名字
    private String email;            //邮箱
    private String wechat;            //微信
    private String adminTel;            //联系人手机
    private String telCodet;            //验证码
    private String personImg;            //负责人证明文件
    private String aarea;
    private String carea;

    private String nickName;       //用户名
    private String img;     //头像

    private String id;

    private String date;

    private String orderid;
    private String outTradeNo;

    private String content;
    private String spaceId;
    private String cashPledge;
    private String year;
    private String month;
    private String location;
    private String price;
    private String totalPrice;
    private String num;
    private String zaras;
    private String gwknum;
    private String orderno;
    private String status;
    private String opassword;
    private String tpassword;
    private String installStatus;
    private String proveVideo;
    private String remark;
    private String completeStatus;

    public void setCode(String code) {
        this.code = code;
    }

    public void setCompleteStatus(String completeStatus) {
        this.completeStatus = completeStatus;
    }

    public void setAarea(String aarea) {
        this.aarea = aarea;
    }

    public void setCarea(String carea) {
        this.carea = carea;
    }

    public void setProveVideo(String proveVideo) {
        this.proveVideo = proveVideo;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public void setInstallStatus(String installStatus) {
        this.installStatus = installStatus;
    }

    public void setOpassword(String opassword) {
        this.opassword = opassword;
    }

    public void setTpassword(String tpassword) {
        this.tpassword = tpassword;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setOrderno(String orderno) {
        this.orderno = orderno;
    }

    public void setCashPledge(String cashPledge) {
        this.cashPledge = cashPledge;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public void setZaras(String zaras) {
        this.zaras = zaras;
    }

    public void setGwknum(String gwknum) {
        this.gwknum = gwknum;
    }

    public void setSpaceId(String spaceId) {
        this.spaceId = spaceId;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
    }

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setPushid(String pushid) {
        this.pushid = pushid;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setOffset(String offset) {
        this.offset = offset;
    }

    public void setLimit(String limit) {
        this.limit = limit;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public void setTelCode(String telCode) {
        this.telCode = telCode;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setBrank(String brank) {
        this.brank = brank;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setComType(String comType) {
        this.comType = comType;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public void setAreaimg(String areaimg) {
        this.areaimg = areaimg;
    }

    public void setShopcart(String shopcart) {
        this.shopcart = shopcart;
    }

    public void setShopcartimg(String shopcartimg) {
        this.shopcartimg = shopcartimg;
    }

    public void setAdvScreen(String advScreen) {
        this.advScreen = advScreen;
    }

    public void setFootfall(String footfall) {
        this.footfall = footfall;
    }

    public void setTurnover(String turnover) {
        this.turnover = turnover;
    }

    public void setCompanyBank(String companyBank) {
        this.companyBank = companyBank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public void setBankNum(String bankNum) {
        this.bankNum = bankNum;
    }

    public void setBusinessLicense(String businessLicense) {
        this.businessLicense = businessLicense;
    }

    public void setCompanyImg(String companyImg) {
        this.companyImg = companyImg;
    }

    public void setCardimgo(String cardimgo) {
        this.cardimgo = cardimgo;
    }

    public void setCardimgt(String cardimgt) {
        this.cardimgt = cardimgt;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setWechat(String wechat) {
        this.wechat = wechat;
    }

    public void setAdminTel(String adminTel) {
        this.adminTel = adminTel;
    }

    public void setTelCodet(String telCodet) {
        this.telCodet = telCodet;
    }

    public void setPersonImg(String personImg) {
        this.personImg = personImg;
    }
}
