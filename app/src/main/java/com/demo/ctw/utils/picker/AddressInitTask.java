package com.demo.ctw.utils.picker;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.widget.Toast;


import com.demo.ctw.utils.GsonTools;

import java.util.ArrayList;

/**
 * 获取地址数据并显示地址选择器
 *
 * @author 李玉江[QQ:1032694760]
 * @version 2015/12/15
 */
public class AddressInitTask extends AsyncTask<String, Void, ArrayList<AddressPicker.Province>> {
    private Activity activity;
    private ProgressDialog dialog;
    private String selectedProvinceId = "", selectedCityId = "", selectedCountyId = "";
    private CharSequence submitText;
    private boolean topLineVisible = true;

    public AddressInitTask(Activity activity) {
        this.activity = activity;
        dialog = ProgressDialog.show(activity, null, "正在初始化数据...", true, true);
    }

    public void setSubmitText(CharSequence txt) {
        this.submitText = txt;
    }

    public void setTopLineVisible(boolean isTopLineVisible) {
        this.topLineVisible = isTopLineVisible;
    }

    @Override
    protected ArrayList<AddressPicker.Province> doInBackground(String... params) {
        if (params != null) {
            switch (params.length) {
                case 1:
                    selectedProvinceId = params[0];
                    break;
                case 2:
                    selectedProvinceId = params[0];
                    selectedCityId = params[1];
                    break;
                case 3:
                    selectedProvinceId = params[0];
                    selectedCityId = params[1];
                    selectedCountyId = params[2];
                    break;
                default:
                    break;
            }
        }
        ArrayList<AddressPicker.Province> data = new ArrayList<>();
        try {
            String json = AssetsUtils.readText(activity, "city.json");
            data.addAll(GsonTools.changeGsonToList(json, AddressPicker.Province.class));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    @Override
    protected void onPostExecute(ArrayList<AddressPicker.Province> result) {
        dialog.dismiss();
        if (result.size() > 0) {
            AddressPicker picker = new AddressPicker(activity, result);

            if (!TextUtils.isEmpty(submitText))
                picker.setSubmitText(submitText);
            picker.setTopLineVisible(topLineVisible);
            picker.setSelectedItem(selectedProvinceId, selectedCityId, selectedCountyId);
            picker.setOnAddressPickListener(new AddressPicker.OnAddressPickListener() {
                @Override
                public void onAddressPicked(AddressPicker.Area province, AddressPicker.Area city, AddressPicker.Area county) {
                    if (null != listener)
                        listener.onResult(province, city, county);
                }
            });
            picker.show();
        } else {
            Toast.makeText(activity, "数据初始化失败", Toast.LENGTH_SHORT).show();
        }
    }

    OnAddressResultListener listener;

    public void setOnAddressResultListener(OnAddressResultListener l) {
        this.listener = l;
    }

    public interface OnAddressResultListener {
        void onResult(AddressPicker.Area province, AddressPicker.Area city, AddressPicker.Area county);
    }

}
