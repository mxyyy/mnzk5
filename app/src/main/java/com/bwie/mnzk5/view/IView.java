package com.bwie.mnzk5.view;

/**
 * date:2018/11/17
 * author:mxy(M)
 * function:
 */
public interface IView <T>{


    void failed(Exception e);
    void getShow(T t);

}
