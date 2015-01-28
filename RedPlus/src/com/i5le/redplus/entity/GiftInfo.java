package com.i5le.redplus.entity;
// Generated 2014-10-15 17:16:53 by Hibernate Tools 3.2.2.GA


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.i5le.framwork.core.entity.IdEntity;

/**
 * GiftInfo generated by hbm2java
 */
@Entity
@Table(name="gift_info"
)
public class GiftInfo  extends IdEntity implements java.io.Serializable {


     /**
	 * 
	 */
	private static final long serialVersionUID = 8700434915813970984L;
	private String giftname;
     private String giftinfo;
     private String remark;
     private String resv1;
     private String resv2;
     private String resv3;

    public GiftInfo() {
    }

    
    @Column(name="giftname", nullable=false, length=100)
    public String getGiftname() {
        return this.giftname;
    }
    
    public void setGiftname(String giftname) {
        this.giftname = giftname;
    }
    
    @Column(name="giftinfo", nullable=false, length=100)
    public String getGiftinfo() {
        return this.giftinfo;
    }
    
    public void setGiftinfo(String giftinfo) {
        this.giftinfo = giftinfo;
    }
    
    @Column(name="remark", nullable=false, length=220)
    public String getRemark() {
        return this.remark;
    }
    
    public void setRemark(String remark) {
        this.remark = remark;
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




}


