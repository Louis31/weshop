package com.i5le.framwork.sys.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.i5le.framwork.commom.bui.vo.Menu;
import com.i5le.framwork.core.repository.BaseJapRepository;
import com.i5le.framwork.core.security.utils.Digests;
import com.i5le.framwork.core.service.BaseService;
import com.i5le.framwork.core.utils.Encodes;
import com.i5le.framwork.sys.entity.Permission;
import com.i5le.framwork.sys.entity.Resource;
import com.i5le.framwork.sys.entity.Role;
import com.i5le.framwork.sys.entity.RoleAuth;
import com.i5le.framwork.sys.entity.User;
import com.i5le.framwork.sys.repository.PermissionRepository;
import com.i5le.framwork.sys.repository.UserRepository;

@Component
@Transactional
public class UserService extends BaseService<User, Long> {

	private static Logger logger = LoggerFactory.getLogger(UserService.class);

	public static final String HASH_ALGORITHM = "SHA-1";
	public static final int HASH_INTERATIONS = 1024;
	private static final int SALT_SIZE = 8;

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private PermissionRepository permissionRepository;

	public User findByLoginName(String loginName) {
		return this.userRepository.findByLoginName(loginName);
	}

	/**
	 * 在保存用户时,发送用户修改通知消息, 由消息接收者异步进行较为耗时的通知邮件发送.
	 * 
	 * 如果企图修改超级用户,取出当前操作员用户,打印其信息然后抛出异常.
	 * 
	 * @return
	 * 
	 */
	@Override
	public User save(User user) {

		// if (isSupervisor(user)) {
		// logger.warn("操作员{}尝试修改超级管理员用户", getCurrentUserName());
		// throw new ServiceException("不能修改超级管理员用户");
		// }

		// 设定安全的密码，生成随机的salt并经过1024次 sha-1 hash
		if (StringUtils.isNotBlank(user.getPlainPassword())) {
			entryptPassword(user);
		}
		if (user.getCreateTime() == null)
			user.setCreateTime(new Date());
		return this.userRepository.save(user);
	}
	
	public boolean checkPassword(Long id, String password){
		User user = this.findOne(id);
		byte[] salt = Encodes.decodeHex(user.getSalt());
		byte[] hashPassword = Digests.sha1(password.getBytes(), salt, HASH_INTERATIONS);
		if(user.getPassword().equals(Encodes.encodeHex(hashPassword))){
			return true;
		}else{
			return false;
		}
	}

	/**
	 * 设定安全的密码，生成随机的salt并经过1024次 sha-1 hash
	 */
	private void entryptPassword(User user) {
		byte[] salt = Digests.generateSalt(SALT_SIZE);
		user.setSalt(Encodes.encodeHex(salt));

		byte[] hashPassword = Digests.sha1(user.getPlainPassword().getBytes(), salt, HASH_INTERATIONS);
		user.setPassword(Encodes.encodeHex(hashPassword));
	}

	public Collection<Menu> loadMenu(Long userId, String ctx) {
		User user = this.userRepository.findOne(userId);
		Iterator<Role> roles = user.getRoles().iterator();
		Map<Long, Menu> levelOne = new HashMap<Long, Menu>();
		Map<Long, Menu> levelTwo = new HashMap<Long, Menu>();
		Set<Menu> levelThree = new HashSet<Menu>();
		while (roles.hasNext()) {
			Role role = roles.next();
			Iterator<RoleAuth> auths = role.getRoleAuths().iterator();
			while (auths.hasNext()) {
				RoleAuth ra = auths.next();
				Resource resource = ra.getResource();
				if (resource.getLevel() == 1) {
					levelOne.put(resource.getId(), Menu.build(resource.getLevel(), resource, ctx));
				} else if (resource.getLevel() == 2) {
					levelTwo.put(resource.getId(), Menu.build(resource.getLevel(), resource, ctx));
				} else if (resource.getLevel() == 3) {
					levelThree.add(Menu.build(resource.getLevel(), resource, ctx));
				}
			}
		}

		Iterator<Menu> three = levelThree.iterator();
		while (three.hasNext()) {
			Menu menu = three.next();
			Menu parentMenu = levelTwo.get(menu.getParentId());
			if (parentMenu != null) {
				parentMenu.getItems().add(menu);
			}
		}

		MenuComparator comparator = new MenuComparator();// 给所有菜单排序

		Collection<Menu> menus = levelTwo.values();
		for (Menu menu : menus) {
			Collections.sort(menu.getItems(), comparator);// 排序二级菜单下的菜单
			Menu parentMenu = levelOne.get(menu.getParentId());
			if (parentMenu != null) {
				parentMenu.getMenu().add(menu);
			}
		}

		List<Menu> menuOne = new ArrayList<Menu>(levelOne.values());

		for (Menu menu : menuOne) {
			Collections.sort(menu.getMenu(), comparator);
		}

		Collections.sort(menuOne, comparator);// 排序二级菜单下的菜单
		return menuOne;
	}

	public List<String> findUserPermissions(Long id){
		User user = this.userRepository.findOne(id);
		List<Role> roles = user.getRoles();
		List<String> permissionsStr = new ArrayList<String>();
		for(Role role : roles){
			List<RoleAuth> ras = role.getRoleAuths();
			for(RoleAuth ra : ras){
				Resource resource = ra.getResource();
				String permissionsIds = ra.getPermissionIds();
				if(permissionsIds!=null && !"".equals(permissionsIds)){
					String[] idsStr = permissionsIds.split(",");
					List<Long> ids = new ArrayList<Long>(idsStr.length);
					for(String idStr : idsStr){
						ids.add(Long.parseLong(idStr));
					}
					List<Permission> permissions = this.permissionRepository.findAll(ids);
					for(Permission permission: permissions){
						permissionsStr.add(resource.getCode()+":"+permission.getPermission());
					}
				}

			}
		}
		return permissionsStr;
	}

	class MenuComparator implements Comparator<Menu> {

		@Override
		public int compare(Menu o1, Menu o2) {
			if (o1.getOrder() < o2.getOrder())
				return -1;
			if (o1.getOrder() > o2.getOrder())
				return 1;
			return 0;
		}

	}

	public static void main(String[] args) {
		ApplicationContext factory = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
		UserService userService = (UserService) factory.getBean("userService");
		// Map map = new TreeMap<String, String>();
		// map.put("EQ_name", "chenxi");
		// PageRequest pageRequest = new PageRequest(1,5);
		// Page<User> page = userService.searchUser(map, pageRequest);
		// System.out.println(page.getNumber());
		User user = userService.findOne((long) 1);
		user.setPlainPassword("chenxi");
		userService.save(user);
	}

	@Override
	public BaseJapRepository<User, Long> getRepository() {
		return this.userRepository;
	}
}
