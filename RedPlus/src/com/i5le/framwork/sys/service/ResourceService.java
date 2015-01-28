package com.i5le.framwork.sys.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.i5le.framwork.commom.bui.vo.TreeItem;
import com.i5le.framwork.core.repository.BaseJapRepository;
import com.i5le.framwork.core.service.BaseService;
import com.i5le.framwork.sys.entity.Resource;
import com.i5le.framwork.sys.repository.ResourceRepository;


@Component
@Transactional
public class ResourceService extends BaseService<Resource, Long>{
	
	@Autowired
	private ResourceRepository resourceRepository;

	@Override
	public BaseJapRepository<Resource, Long> getRepository() {
		return this.resourceRepository;
	}
	
	public List<TreeItem> getRsourceList(){
		List<Resource> resources = this.resourceRepository.findByLevel(1);
		List<TreeItem> trees = new ArrayList<TreeItem>(resources.size());
		for(Resource resource : resources){
			trees.add(this.resourceToTree(resource));
		}
		return trees;
	}
	

	private TreeItem resourceToTree(Resource resource){
		TreeItem tree = new TreeItem();
		tree.setId(resource.getId());
		if(resource.getParent()!=null){
			tree.setParentId(resource.getParent().getId());
		}
		tree.setText(resource.getName());
		tree.setLevel(resource.getLevel());
		tree.setValue(resource.getPermissionIds());
		List<Resource> list = resource.getResources();
		if(list!=null && list.size()>0){
			Iterator<Resource> it = resource.getResources().iterator();
			List<TreeItem> children = new ArrayList<TreeItem>(list.size());
			while(it.hasNext()){
				children.add(this.resourceToTree(it.next()));
			}
			tree.setChildren(children);
		}
		return tree;
	}

}
