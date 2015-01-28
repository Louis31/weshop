package com.i5le.framwork.sys.service;

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


@Component
@Transactional
public class DictionaryService extends BaseService<Dictionary, Long>{
	
	@Autowired
	private DictionaryRepository dictionaryRepository;
	
	
	public List<ListItem> searchListItem(Map<String, Object> searchParams, Sort sort){
		List<Dictionary> list = this.search(searchParams, sort);
		List<ListItem> items = new ArrayList<ListItem>(list.size());
		for(Dictionary dictionary : list){
			ListItem item = new ListItem(dictionary.getName(), dictionary.getValue());
			items.add(item);
		}
		return items;
	}

	@Override
	public BaseJapRepository<Dictionary, Long> getRepository() {
		return this.dictionaryRepository;
	}

}
