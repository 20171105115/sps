package com.app.util;

public class PageUtil {

    public static int pageIndexToRowIndex(int pageIndex,int pageSize){
        return (pageIndex>0)?0:(pageIndex-1)*pageSize;
    }
}
