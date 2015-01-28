package com.i5le.redplus.service;

import java.util.List;

import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import sun.swing.StringUIClientPropertyKey;

import com.i5le.framwork.core.repository.BaseJapRepository;
import com.i5le.framwork.core.service.BaseService;
import com.i5le.framwork.sys.entity.User;
import com.i5le.redplus.entity.Collect;
import com.i5le.redplus.entity.UserInfo;
import com.i5le.redplus.repository.UserInfoRepository;

@Component
@Transactional
public class UserInfoService extends BaseService<UserInfo, Long> {

	private transient static final String NO_PARAMS = "0";
	private transient static final String FEN_GE = "-";
	@Autowired
	private UserInfoRepository userInfoRepository;

	@Override
	public BaseJapRepository<UserInfo, Long> getRepository() {
		return this.userInfoRepository;
	}

	@Transactional(readOnly = true)
	public UserInfo findByLoginname(String loginname) {
		return this.userInfoRepository.findByLoginname(loginname);
	}

	public List<UserInfo> searchByNativeSql(String age, String height,
			String width, String areaName, String gender,String type) {

		System.out.println("age : "+ age +"height: "  +height+ "width:" + width + "areaName:" + areaName + "gender:" + gender );
		StringBuffer hql = new StringBuffer(
				" select * from   user_info  u where 1=1");

		if (!StringUtils.isEmpty(age) &&!age.equals(NO_PARAMS)) {
			String[] ages = age.split(FEN_GE);
			hql.append(" and   age  >=   " + ages[0] + " and age <= " + ages[1]);

		}
		if (!StringUtils.isEmpty(height) &&!height.equals(NO_PARAMS)) {

			String[] heights = height.split(FEN_GE);
			hql.append(" and   height  >=   " + heights[0] + " and height <= "
					+ heights[1]);

		}
		if (!StringUtils.isEmpty(width)&&!width.equals(NO_PARAMS)) {
			String[] weights = width.split(FEN_GE);
			hql.append(" and   weight  >=   " + weights[0] + " and weight <= "
					+ weights[1]);

		}

		if (!StringUtils.isEmpty(areaName)&&!areaName.equals(NO_PARAMS)) {
			hql.append(" and   area  in  ( select id from areacode where parentarea in (SELECT  id FROM areacode WHERE areacode.areaname = '"+areaName+"')  or areacode.areaname = '"+areaName+"' )");
		}
		
		if (!StringUtils.isEmpty(type)&&!type.equals(NO_PARAMS)) {
			hql.append(" and   type_map  in  ( select id from type_map where typeName = '"+type+"' )");
		}
		if (!StringUtils.isEmpty(gender)&&!areaName.equals(NO_PARAMS)) {
		hql.append(" and   gender= " + gender);
		
		}
        
		hql.append(" ORDER BY sort_value DESC");
		Query query = entityManager.createNativeQuery(hql.toString(),
				UserInfo.class);
		return query.getResultList();
		// TODO Auto-generated method stub

	}
	
	public List<UserInfo> getDealerDataList(String telphone){
		StringBuffer hql = new StringBuffer(
				" select * from   user_info  u where 1=1");
		if(StringUtils.isEmpty(telphone)){
			hql.append(" and refree = '"+telphone+"'");
			Query query = entityManager.createNativeQuery(hql.toString(),
					UserInfo.class);
			return query.getResultList();
		}else {
			return null;
		}
	}
	
	public List<UserInfo> findCollectionAll(Long host) {

		return this.entityManager.createNativeQuery(
				" select * from  user_info as ui where ui.id in ( select co.user_id from collect as co where co.host_id ="+host+" )",
				UserInfo.class).getResultList();

	}
	
	public long getUserCount(){
		return userInfoRepository.count();
	}
}
