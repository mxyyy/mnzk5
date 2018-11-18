package com.bwie.mnzk5;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bwie.mnzk5.adapter.ShopAdapter;
import com.bwie.mnzk5.bean.ShopBean;
import com.bwie.mnzk5.inter.ICallBack;
import com.bwie.mnzk5.presenter.ShopPresenter;
import com.bwie.mnzk5.utils.HttpUtils;
import com.bwie.mnzk5.view.IView;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, IView <ShopBean>{

    private ImageView imgBack;
    private ImageView imgStar;
    private RecyclerView rvShow;
    private List<ShopBean.DataBean> list;
    private ShopAdapter shopAdapter;
    private ShopPresenter mShopPresenter;
    private ImageView mImgYes;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        initView();

        mShopPresenter = new ShopPresenter();
        mShopPresenter.attach(this);
        mShopPresenter.getShow();

        list = new ArrayList<>();

        shopAdapter = new ShopAdapter(this,list);
        rvShow.setAdapter(shopAdapter);

        rvShow.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
/*        Type type = new TypeToken<ShopBean>(){}.getType();
        HttpUtils.getInstance().get("http://www.xieast.com/api/news/news.php", new ICallBack() {
            @Override
            public void onSuccess(Object obj) {
                ShopBean shopBean = (ShopBean) obj;
                if (shopBean!=null){
                    List<ShopBean.DataBean> data = shopBean.getData();
                    if (data!=null){
                        list.clear();
                        list.addAll(data);
                        shopAdapter.notifyDataSetChanged();

                    }
                }
            }

            @Override
            public void onFailed(Exception e) {

            }
        },type);*/
    }

    private void initView() {
        imgBack = findViewById(R.id.img_back);
        imgStar = findViewById(R.id.img_star);
        rvShow = findViewById(R.id.rv_show);
        mImgYes = findViewById(R.id.img_yes);


        imgBack.setOnClickListener(this);
        imgStar.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.img_back:
                finish();
                break;
                //属性动画
            case R.id.img_star:
                ObjectAnimator objectAnimatorY=ObjectAnimator.ofFloat(imgStar,"translationY",0,1700);
                ObjectAnimator objectAnimatorX=ObjectAnimator.ofFloat(imgStar,"translationX",0,-1000);
                ObjectAnimator alpha = ObjectAnimator.ofFloat(imgStar, "alpha", 1f, 0.7f, 0.5f, 0.3f, 0f);

                AnimatorSet animatorSet = new AnimatorSet();
                animatorSet.playTogether(objectAnimatorX,objectAnimatorY,alpha);
                animatorSet.setDuration(3000);
                animatorSet.start();

                animatorSet.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        //动画执行结束后变为选中
                        mImgYes.setImageDrawable(getResources().getDrawable(R.mipmap.yes));
                    }
                });

                break;

    }
  }

    @Override
    public void failed(Exception e) {
        Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();

    }

    @Override
    public void getShow(ShopBean shopBean) {
        if(shopBean != null){
            List<ShopBean.DataBean> data = shopBean.getData();
            if (data != null){
                list.clear();
                list.addAll(data);
                shopAdapter.notifyDataSetChanged();
            }
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mShopPresenter.detach();
    }
}
