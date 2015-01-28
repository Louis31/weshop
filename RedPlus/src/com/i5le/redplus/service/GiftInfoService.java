package com.i5le.redplus.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.i5le.framwork.commom.bui.vo.ListItem;
import com.i5le.framwork.core.repository.BaseJapRepository;
import com.i5le.framwork.core.service.BaseService;
import com.i5le.framwork.sys.entity.Dictionary;
import com.i5le.framwork.sys.repository.DictionaryRepository;
import com.i5le.redplus.entity.GiftInfo;
import com.i5le.redplus.repository.GiftInfoRepository;

@Component
@Transactional
public class GiftInfoService extends BaseService<GiftInfo, Long> {

	@Autowired
	private GiftInfoRepository giftInfoRepository;


	@Override
	public BaseJapRepository<GiftInfo, Long> getRepository() {
		return this.giftInfoRepository;
	}

}
