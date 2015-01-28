package com.i5le.framwork.core.utils;

import java.util.Comparator;

import com.i5le.redplus.entity.UserInfo;

public class SortBean implements Comparator<UserInfo>{

	@Override
	public int compare(UserInfo o1, UserInfo o2) {
		if(null!=o1 && null!=o2){
            if(o1.getSort_value() >o2.getSort_value()){
                return 1;
            }else if(o1.getSort_value() ==o2.getSort_value()){
                return 0;
            }
        }
        return -1;
	}
	
	

}
