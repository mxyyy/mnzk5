package com.bwie.mnzk5.presenter;

import com.bwie.mnzk5.bean.ShopBean;
import com.bwie.mnzk5.inter.ICallBack;
import com.bwie.mnzk5.model.ShopModel;
import com.bwie.mnzk5.view.IView;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

/**
 * date:2018/11/17
 * author:mxy(M)
 * function:
 */
public class ShopPresenter {
    private IView iv;
    private ShopModel shopmodel;

    public void attach(IView iv){
        this.iv = iv;
        shopmodel = new ShopModel();
    }
    public  void  getShow(){
        String url = "http://www.xieast.com/api/news/news.php";
        Type type  = new TypeToken<ShopBean>(){}.getType();
        shopmodel.getShow(url,new ICallBack(){

            @Override
            public void onSuccess(Object obj) {
                iv.getShow(obj);
            }

            @Override
            public void onFailed(Exception e) {
                iv.failed(e);

            }
        },type);

    }
    public void detach(){
        if(iv != null){
            iv = null;
        }
    }
}
