package com.casd.serviceimpl.tree;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.casd.controller.web.Ref;
import com.casd.dao.tree.MenuDao;
import com.casd.dao.uc.UserDao;
import com.casd.entity.tree.Menu;
import com.casd.entity.uc.User;
import com.casd.service.tree.MenuService;
import com.casd.service.uc.UserService;

@Service
public class MenuServiceImpl implements MenuService {

	@Autowired
	private MenuDao menuDao;

	@Override
	public List<Menu> queryMenuList(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return menuDao.queryMenuList(map);
	}

	@Override
	public String findMenu(Map<String, Object> map) {
		return menuDao.findMenu(map);
	}

	@Override
	public List<Map<String, Object>> menuList(int page, int pageSize,
			Ref<Integer> record, String where, String searchId,
			String searchName) throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();

		param.put("where", where);

		if (record != null) {
			Integer count = menuDao.getmenuCount(param);
			record.setValue(count);
		}
		// 分页
		if (pageSize <= 0)
			throw new Exception("pageSize 必须 > 0");

		param.put("limit", String.format("limit %1$s,%2$s", page < 0 ? 0 : page
				* pageSize, pageSize));

		return menuDao.menuList(param);
	}

	@Override
	@Transactional
	public void addMenu(Menu menu) {
	
		menuDao.addMenu(menu);
	}

	@Override
	public void deleMenu(Map<String, Object> map) {
		// TODO Auto-generated method stub
		menuDao.deleMenu(map);
	}

	@Override
	public Menu findById(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return menuDao.findById(map);
	}

	@Override
	public void updateMenu(Menu menu) {
		// TODO Auto-generated method stub
		menuDao.updateMenu(menu);
	}

	/**
	 * 获取菜单权限
	 * 
	 * */
	@Override
	public List<Map<String, Object>> queryListForRang(Map<String, Object> map) {

		return menuDao.queryListForRang(map);
	}

}
