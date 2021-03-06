package com.i5le.redplus.entity;
// Generated 2014-10-15 17:16:53 by Hibernate Tools 3.2.2.GA


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.i5le.framwork.core.entity.IdEntity;

/**
 * TransRebateInfo generated by hbm2java
 */
@Entity
@Table(name="trans_rebate_info"
)
public class TransRebateInfo  extends IdEntity implements java.io.Serializable {


     /**
	 * 
	 */
	private static final long serialVersionUID = -4258928695882014921L;

     private int transRebateId;
     private int userId;
     private int money;
     private int statu;
     private String resv1;
     private String resv2;
     private String resv3;
     private float ratio;

    public TransRebateInfo() {
    }

  
    @Column(name="trans_rebate_id", nullable=false)
    public int getTransRebateId() {
        return this.transRebateId;
    }
    
    public void setTransRebateId(int transRebateId) {
        this.transRebateId = transRebateId;
    }
    
    @Column(name="user_id", nullable=false)
    public int getUserId() {
        return this.userId;
    }
    
    public void setUserId(int userId) {
        this.userId = userId;
    }
    
    @Column(name="money", nullable=false)
    public int getMoney() {
        return this.money;
    }
    
    public void setMoney(int money) {
        this.money = money;
    }
    
    @Column(name="statu", nullable=false)
    public int getStatu() {
        return this.statu;
    }
    
    public void setStatu(int statu) {
        this.statu = statu;
    }
    
    @Column(name="resv1", nullable=false, length=20)
    public String getResv1() {
        return this.resv1;
    }
    
    public void setResv1(String resv1) {
        this.resv1 = resv1;
    }
    
    @Column(name="resv2", nullable=false, length=100)
    public String getResv2() {
        return this.resv2;
    }
    
    public void setResv2(String resv2) {
        this.resv2 = resv2;
    }
    
    @Column(name="resv3", nullable=false, length=200)
    public String getResv3() {
        return this.resv3;
    }
    
    public void setResv3(String resv3) {
        this.resv3 = resv3;
    }
    
    @Column(name="ratio", nullable=false, precision=20, scale=0)
    public float getRatio() {
        return this.ratio;
    }
    
    public void setRatio(float ratio) {
        this.ratio = ratio;
    }




}


