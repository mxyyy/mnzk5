package com.bwie.mnzk5.model;

import com.bwie.mnzk5.inter.ICallBack;
import com.bwie.mnzk5.utils.HttpUtils;

import java.lang.reflect.Type;

/**
 * date:2018/11/17
 * author:mxy(M)
 * function:
 */
public class ShopModel {


    public void getShow(String url, ICallBack callback,Type type) {
        HttpUtils.getInstance().get(url,callback,type);
    }
}
