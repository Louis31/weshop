package com.i5le.redplus.web;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.io.IOUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.i5le.framwork.core.entity.AjaxResult;
import com.i5le.framwork.core.service.BaseService;
import com.i5le.framwork.core.web.BaseCRUDController;
import com.i5le.framwork.sys.entity.User;
import com.i5le.redplus.entity.AreaCode;
import com.i5le.redplus.entity.GiftInfo;
import com.i5le.redplus.entity.UserImg;
import com.i5le.redplus.entity.UserInfo;
import com.i5le.redplus.service.AreaCodeService;
import com.i5le.redplus.service.GiftInfoService;
import com.i5le.redplus.service.UserImgService;
import com.i5le.redplus.service.UserInfoService;
import com.redplus.view.utils.GsonUtil;

@Controller
@RequestMapping(value = "/userImg")
public class UserImgController extends BaseCRUDController<UserImg, Long> {

	private static AjaxResult<UserImg> ImgAjaxResult = new AjaxResult<UserImg>();
	private static Properties pro = new Properties();
	@Autowired
	private UserInfoService userInfoService;

	private static final String URL_PATH;
	static {
		try {
			pro.load(UserImgController.class
					.getResourceAsStream("/imp.properties"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		URL_PATH = pro.getProperty("img_url");

	}
	private DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");

	@Autowired
	private UserImgService userImgService;

	@RequestMapping(value = "")
	@ResponseBody
	public String index() {
		return PARAMS_ERROR;
	}

	@RequestMapping(value="list")
	@ResponseBody
	public String list(ServletRequest request){
		 return super.listIter(request);
	}

	@RequestMapping(value = "getUserImg")
	@ResponseBody
	public String getImageForUserId(@RequestParam("uid") Long uid) {

		AjaxResult<UserImg> resultImage = new AjaxResult<UserImg>();

		resultImage.setSuccess(true);

		List<UserImg> userImgs = this.userImgService.findByUserId(uid);

		resultImage.setList(userImgs);

		return GsonUtil.transferByEntity(resultImage);

	}

	@RequestMapping(value = "getUserImg5")
	@ResponseBody
	public String getImageForUserId5(@RequestParam("uid") Long uid) {

		PageRequest pageRequest = new PageRequest(0, 5);
		Map<String, Object> searchParams   = new HashMap<String, Object>();
		searchParams.put("EQ_userId", uid+"");
		
		
		AjaxResult<UserImg> resultImage = new AjaxResult<UserImg>();

		resultImage.setSuccess(true);
		
		

		List<UserImg> userImgs = this.userImgService.searchByPage(searchParams, pageRequest).getContent();

		resultImage.setList(userImgs);

		return GsonUtil.transferByEntity(resultImage);

	}
	@RequestMapping(value = "uploadSig", method = RequestMethod.POST)
	@ResponseBody
	public String uploadSig(@RequestParam("file") CommonsMultipartFile files,
			HttpServletRequest request) {

		AjaxResult ajaxResult = new AjaxResult();
		String dateT = df.format(new Date());

		if (!files.isEmpty()) {

			String basePath = URL_PATH;
			String src = "/" + "/" + dateT + "/";
			basePath += src;
			File f = new File(basePath);

			System.out.println(basePath);

			if (!f.exists()) {
				System.out.println(f.mkdirs());
			}
			try {
				IOUtils.copy(files.getInputStream(), new FileOutputStream(
						basePath + files.getOriginalFilename()));

				ajaxResult.setSuccess(true);
				ajaxResult.setMsg(src + files.getOriginalFilename());

				// ajaxResult.setMsg(msg);
			} catch (Exception e) {
				ajaxResult.setSuccess(false);
				ajaxResult.setMsg("上传失败！");

			}
		}

		return GsonUtil.transferByEntity(ajaxResult);
	}

	@RequestMapping(value = "upload", method = RequestMethod.POST)
	@ResponseBody
	public String upload(@RequestParam("uid") Long uid,
			@RequestParam("file") CommonsMultipartFile[] files,
			HttpServletRequest request) {

		String dateT = df.format(new Date());

		List<UserImg> list = new ArrayList<UserImg>();
		for (int i = 0; i < files.length; i++) {

			UserImg userImg = new UserImg();
			userImg.setUserId(uid);

			if (!files[i].isEmpty()) {

				String basePath = URL_PATH;
				String src = "/" + uid + "/" + dateT + "/";
				basePath += src;
				File f = new File(basePath);

				System.out.println(basePath);

				if (!f.exists()) {
					System.out.println(f.mkdirs());
				}
				try {
					IOUtils.copy(
							files[i].getInputStream(),
							new FileOutputStream(basePath
									+ files[i].getOriginalFilename()));
					userImg.setImgSrc(src + files[i].getOriginalFilename());
					list.add(userImg);

				} catch (IOException e) {
					e.printStackTrace();
					ImgAjaxResult.setSuccess(false);
					return GsonUtil.transferByEntity(ImgAjaxResult);
					// TODO Auto-generated catch block

				}
			}
		}

		try {

			this.userImgService.save(list);
			ImgAjaxResult.setSuccess(true);
			ImgAjaxResult.setList(list);
		} catch (Exception e) {
			e.printStackTrace();
			ImgAjaxResult.setSuccess(false);
			// TODO: handle exception
		}

		return GsonUtil.transferByEntity(ImgAjaxResult);
	}

	@ModelAttribute
	public void getEntity(
			@RequestParam(value = "id", defaultValue = "-1") Long id,
			Model model) {
		if (id != -1) {
			model.addAttribute("entity", this.userImgService.findOne(id));
		}
	}

	@Override
	protected BaseService<UserImg, Long> getServcie() {
		return this.userImgService;
	}

}
