package com.caihong.cms.action.member;

import static com.caihong.cms.Constants.TPLDIR_MEMBER;
import static com.caihong.common.page.SimplePage.cpn;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.caihong.cms.action.front.UploadifyAct;
import com.caihong.cms.entity.assist.CmsWebservice;
import com.caihong.cms.entity.main.Order;
import com.caihong.cms.entity.main.Patient;
import com.caihong.cms.entity.main.Reserve;
import com.caihong.cms.entity.main.ReserveAttachment;
import com.caihong.cms.entity.main.UserSchedule;
import com.caihong.cms.manager.assist.CmsWebserviceMng;
import com.caihong.cms.manager.main.OrderMng;
import com.caihong.cms.manager.main.PatientMng;
import com.caihong.cms.manager.main.ReserveMng;
import com.caihong.cms.manager.main.UserScheduleMng;
import com.caihong.common.page.Pagination;
import com.caihong.common.web.CookieUtils;
import com.caihong.common.web.ReserveStatus;
import com.caihong.common.web.ResponseUtils;
import com.caihong.core.entity.CmsSite;
import com.caihong.core.entity.CmsUser;
import com.caihong.core.entity.CmsUserExt;
import com.caihong.core.entity.MemberConfig;
import com.caihong.core.manager.CmsUserAccountMng;
import com.caihong.core.manager.CmsUserExtMng;
import com.caihong.core.manager.CmsUserMng;
import com.caihong.core.web.WebErrors;
import com.caihong.core.web.util.CmsUtils;
import com.caihong.core.web.util.FrontUtils;

/**
 * 会员中心Action
 */
@Controller
public class MemberAct {
	private static final Logger log = LoggerFactory.getLogger(MemberAct.class);

	public static final String MEMBER_CENTER = "tpl.memberCenter";
	public static final String MEMBER_ORDER = "tpl.memberOrder";
	public static final String MEMBER_PROFILE = "tpl.memberProfile";
	public static final String MEMBER_PORTRAIT = "tpl.memberPortrait";
	public static final String MEMBER_PASSWORD = "tpl.memberPassword";
	public static final String MEMBER_ACCOUNT = "tpl.memberAccount";
	
	public static final String MEMBER_RESERVE = "tpl.memberReserve";
	public static final String MEMBER_RESERVE_DOCTOR = "tpl.memberReserveDoctor";
	public static final String MEMBER_RESERVE_RECORD ="tpl.memberReserveRecord";
	public static final String MEMBER_RESERVE_VIEW="tpl.memberReserveView";	
	public static final String MEMBER_DOCTOR_WORK="tpl.memberDoctorWork";
	public static final String MEMBER_DOCTOR_VIEW="tpl.memberDoctorView";
	
	public static final String HOME="home";
	public static final String ORDER="order";
	public static final String RESERVE="reserve";
	public static final String RECORD="record";
	public static final String DOCTOR="doctor";
	
	/**
	 * 会员中心页
	 * 
	 * 如果没有登录则跳转到登陆页
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/member/index.jspx", method = RequestMethod.GET)
	public String index(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		CmsSite site = CmsUtils.getSite(request);
		CmsUser user = CmsUtils.getUser(request);
		FrontUtils.frontData(request, model, site);
		MemberConfig mcfg = site.getConfig().getMemberConfig();
		// 没有开启会员功能
		if (!mcfg.isMemberOn()) {
			return FrontUtils.showMessage(request, model, "member.memberClose");
		}
		if (user == null) {
			return FrontUtils.showLogin(request, model, site);
		}
		model.addAttribute("_index", HOME);
		return FrontUtils.getTplPath(request, site.getSolutionPath(),
				TPLDIR_MEMBER, MEMBER_CENTER);
	}
	/**
	 * 用户订单 
	 * @param request
	 * @param pageNo
	 * @param type
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/member/orders.jspx")
	public String orders(HttpServletRequest request,Integer pageNo,Integer type,
			HttpServletResponse response, ModelMap model) {
		CmsSite site = CmsUtils.getSite(request);
		CmsUser user = CmsUtils.getUser(request);
		FrontUtils.frontData(request, model, site);
		
		if (user == null) {
			return FrontUtils.showLogin(request, model, site);
		}
		Pagination pagination=orderMng.getPageByUser(user.getId(), type, cpn(pageNo), CookieUtils.getPageSize(request));
		model.addAttribute("pagination",pagination);
		model.addAttribute("_index", ORDER);
		return FrontUtils.getTplPath(request, site.getSolutionPath(),
				TPLDIR_MEMBER, MEMBER_ORDER);
	}
	@RequestMapping(value = "/member/ordersJson.jspx")
	public void ordersJson(HttpServletRequest request,Integer pageNo,Integer pageSize,Integer type,
			HttpServletResponse response, ModelMap model) {
		CmsUser user = CmsUtils.getUser(request);
		JSONArray array=new JSONArray();
		if(pageSize==null){
			pageSize=5;
		}
		if(pageNo==null){
			pageNo=1;
		}
		
		Pagination pagination=orderMng.getPageByUser(user.getId(), type,pageNo, pageSize);
		if(pagination!=null &&pagination.getTotalCount()>0){
			List<Order> list=(List<Order>)pagination.getList();
			SimpleDateFormat format =new SimpleDateFormat("yyyy-MM-dd HH:mm");
			try {
				for(Order order:list){
					JSONObject object = new JSONObject();
					object.put("amount", order.getAmount());
					object.put("note", order.getNote());
					object.put("id", order.getId());
					object.put("orderNum", order.getOrderNum());
					object.put("time", format.format(order.getTime()));
					object.put("typeName", order.getTypeName());
					object.put("statusName", order.getStatusName());
					object.put("status", order.getStatus());
					object.put("totalPage", pagination.getTotalPage());
					array.put(object);
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		ResponseUtils.renderJson(response, array.toString());
	
	}
	
	@RequestMapping(value = "/member/reserveRecord.jspx")
	public String reserveRecord(HttpServletRequest request,Integer pageNo,Integer doctorid,Date startDate,Date endDate,Boolean payStatus,Integer status,String doctorname,
			HttpServletResponse response, ModelMap model) {
		CmsSite site = CmsUtils.getSite(request);
		CmsUser user = CmsUtils.getUser(request);
		FrontUtils.frontData(request, model, site);
		
		if (user == null) {
			return FrontUtils.showLogin(request, model, site);
		}
		if(startDate!=null){
			model.addAttribute("startDate",startDate);
		}
		if(endDate!=null){
			model.addAttribute("endDate",endDate);
		}
		if(payStatus!=null){
			model.addAttribute("payStatus",payStatus);
		}
		if(status!=null){
			model.addAttribute("status",status);
		}
		if(doctorname!=null){
			model.addAttribute("doctorname",doctorname);
		}
		model.addAttribute("_index", RECORD);
		Pagination pagination=reserveMng.search(user.getId(), doctorid, startDate, endDate, payStatus, status, cpn(pageNo), CookieUtils.getPageSize(request),null,doctorname);
		model.addAttribute("pagination",pagination);
		
		return FrontUtils.getTplPath(request, site.getSolutionPath(),
				TPLDIR_MEMBER, MEMBER_RESERVE_RECORD);
	}
	@RequestMapping(value = "/member/reserveRecordJson.jspx")
	public void reserveRecordJson(HttpServletRequest request,Integer pageNo,Integer pageSize,Integer doctorid,Date startDate,Date endDate,Boolean payStatus,Integer status,String doctorname,
			HttpServletResponse response, ModelMap model) {
		CmsUser user = CmsUtils.getUser(request);
		if(pageSize==null){
			pageSize=5;
		}
		if(pageNo==null){
			pageNo=1;
		}		
		JSONArray array=new JSONArray();	
		Pagination pagination=reserveMng.search(user.getId(), doctorid, startDate, endDate, payStatus, status, cpn(pageNo), CookieUtils.getPageSize(request),null,doctorname);
		if(pagination!=null &&pagination.getTotalCount()>0){
			List<Reserve> list=(List<Reserve>)pagination.getList();
			SimpleDateFormat format =new SimpleDateFormat("yyyy-MM-dd HH:mm");
			try {
				for(Reserve order:list){
					JSONObject object = new JSONObject();
					object.put("price", order.getPrice());
					object.put("id", order.getId());
					object.put("time",format.format(order.getTime()));
					object.put("status", order.getStatus());
					object.put("payStatus", order.getPayStatus());
					object.put("statusName", order.getStatusName());
					object.put("doctorUserName", order.getDoctorUser().getUsername());
					object.put("note", order.getDoctorUser().getJobLevel().getName()+order.getDoctorUser().getJobTitle().getName());
					object.put("doctorImg", order.getDoctorUser().getUserImg());
					object.put("patientName", order.getPatient().getName());
					object.put("doctorRealName", order.getDoctorUser().getRealname());
					object.put("totalPage", pagination.getTotalPage());
					array.put(object);
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		ResponseUtils.renderJson(response, array.toString());
	}
	
	@RequestMapping(value = "/member/doctorWork.jspx")
	public String doctorWork(HttpServletRequest request,Integer pageNo,Date startDate,Date endDate,Boolean payStatus,Integer status,String patientName,
			HttpServletResponse response, ModelMap model) {
		CmsSite site = CmsUtils.getSite(request);
		CmsUser user = CmsUtils.getUser(request);
		FrontUtils.frontData(request, model, site);
		
		if (user == null) {
			return FrontUtils.showLogin(request, model, site);
		}
		
		boolean mo=true;
		if(startDate!=null){
			model.addAttribute("startDate",startDate);
			mo=false;
		}
		if(endDate!=null){
			model.addAttribute("endDate",endDate);
			mo=false;
		}
		if(payStatus!=null){
			model.addAttribute("payStatus",payStatus);
			mo=false;
		}
		if(status!=null){
			model.addAttribute("status",status);
			mo=false;
		}
		if(patientName!=null){
			model.addAttribute("patientName",patientName);
			mo=false;
		}
		if(mo){
			if(payStatus==null){
				payStatus=true;
			}
			if(status==null){
				status=ReserveStatus.ARRANGED.getValue();
			}
		}
		model.addAttribute("_index", DOCTOR);
		List<ReserveStatus> statusList=Arrays.asList(ReserveStatus.values());
		Pagination pagination=reserveMng.search(null, user.getId(), startDate, endDate, payStatus, status, cpn(pageNo), CookieUtils.getPageSize(request),patientName,null);
		model.addAttribute("pagination",pagination);
		model.addAttribute("statusList",statusList);
		return FrontUtils.getTplPath(request, site.getSolutionPath(),
				TPLDIR_MEMBER, MEMBER_DOCTOR_WORK);
	}
	
	@RequestMapping(value = "/member/doctorSave.jspx")
	public void doctorSave(HttpServletRequest request,Reserve reserve,
			HttpServletResponse response, ModelMap model) {
		CmsSite site = CmsUtils.getSite(request);
		CmsUser user = CmsUtils.getUser(request);
		FrontUtils.frontData(request, model, site);
		
		if (user == null) {
			ResponseUtils.renderJson(response, "0");
		}else{
			
				if(reserve.getDiagnosis()==null){
					ResponseUtils.renderJson(response, "0");
				}else{
					reserve.setStatus(ReserveStatus.CONSULTATION.getValue());
					reserve.setConsultTime(new Date());
					reserveMng.update(reserve);
					
					ResponseUtils.renderJson(response, "1");
				}
			
		}
		
	}
	
	@RequestMapping(value = "/member/reserve.jspx")
	public String reserve(HttpServletRequest request,Integer pageNo,Integer userid,Date startDate,Date endDate, Integer nationId,Integer majorId, Integer jobTitleId,Integer jobLevelId,
			HttpServletResponse response, ModelMap model) {
		CmsSite site = CmsUtils.getSite(request);
		CmsUser user = CmsUtils.getUser(request);
		FrontUtils.frontData(request, model, site);
		
		if (user == null) {
			return FrontUtils.showLogin(request, model, site);
		}
		Pagination pagination=userScheduleMng.getPage(userid, startDate, endDate, nationId, majorId, jobTitleId, jobLevelId,  cpn(pageNo), CookieUtils.getPageSize(request));
		model.addAttribute("pagination",pagination);
		model.addAttribute("_index", RESERVE);
		return FrontUtils.getTplPath(request, site.getSolutionPath(),
				TPLDIR_MEMBER,MEMBER_RESERVE_DOCTOR );
	}
	@RequestMapping(value = "/member/reserveView.jspx")
	public String reserveView(HttpServletRequest request,Integer pageNo,Integer id,
			HttpServletResponse response, ModelMap model) {
		CmsSite site = CmsUtils.getSite(request);
		CmsUser user = CmsUtils.getUser(request);
		FrontUtils.frontData(request, model, site);
		
		if (user == null) {
			return FrontUtils.showLogin(request, model, site);
		}
		if(id!=null){
			Reserve reserve=reserveMng.findById(id);
			if(reserve!=null){
				model.addAttribute("reserve",reserve);
			}
		}
		model.addAttribute("_index", RESERVE);
		return FrontUtils.getTplPath(request, site.getSolutionPath(),
				TPLDIR_MEMBER,MEMBER_RESERVE_VIEW );
	}
	@RequestMapping(value = "/member/doctorView.jspx")
	public String doctorView(HttpServletRequest request,Integer pageNo,Integer id,
			HttpServletResponse response, ModelMap model) {
		CmsSite site = CmsUtils.getSite(request);
		CmsUser user = CmsUtils.getUser(request);
		FrontUtils.frontData(request, model, site);
		
		if (user == null) {
			return FrontUtils.showLogin(request, model, site);
		}
		if(id!=null){
			Reserve reserve=reserveMng.findById(id);
			if(reserve!=null){
				model.addAttribute("reserve",reserve);
			}
		}
		model.addAttribute("_index", DOCTOR);
		return FrontUtils.getTplPath(request, site.getSolutionPath(),
				TPLDIR_MEMBER,MEMBER_DOCTOR_VIEW );
	}
	@RequestMapping(value = "/member/imageUnload.jspx", method = RequestMethod.POST)
	public void imageUnload(HttpServletRequest request,Integer id,String byteString,
			HttpServletResponse response, ModelMap model) {
		
		String out="0";
		
		if(id!=null){
			CmsUser user = cmsUserMng.findById(id);
			if(user!=null){
				String path=UploadifyAct.saveByteImg(request, byteString);
				System.out.println(path);
				if(path!=null&&!path.equals("")){
					user.getUserExt().setUserImg(path);
					cmsUserMng.updateUser(user);
					out="1";
				}
				
			}
		}
		ResponseUtils.renderJson(response, out);
	}
	
	@RequestMapping(value = "/member/reserveCancel.jspx", method = RequestMethod.POST)
	public void reserveCancel(HttpServletRequest request,Integer pageNo,Integer id,String reason,
			HttpServletResponse response, ModelMap model) {
		
		String out="0";
		if(id!=null){
			Reserve reserve=reserveMng.findById(id);
			if(reserve!=null){
				if(reserve.getStatus()==ReserveStatus.RESERVE.getValue()){
					reserve.setStatus(ReserveStatus.CANCEL.getValue());
					reserve.setCancelReason(reason);
					reserve.setCancelTime(new Date());
					reserveMng.save(reserve);
					out="1";
				}
			}
		}
		ResponseUtils.renderJson(response, out);
	}
	@RequestMapping(value = "/member/reserveApply.jspx")
	public String reserveApply(HttpServletRequest request,Integer pageNo,Integer userid,
			HttpServletResponse response, ModelMap model) {
		CmsSite site = CmsUtils.getSite(request);
		CmsUser user = CmsUtils.getUser(request);
		FrontUtils.frontData(request, model, site);
		
		if (user == null) {
			return FrontUtils.showLogin(request, model, site);
		}
		model.addAttribute("sessionId", request.getSession().getId());
		if(userid!=null){
			UserSchedule doctor=userScheduleMng.findById(userid);
			if(doctor!=null){
				model.addAttribute("doctor",doctor);
			}
		}
		model.addAttribute("_index", RESERVE);
		return FrontUtils.getTplPath(request, site.getSolutionPath(),
				TPLDIR_MEMBER,MEMBER_RESERVE );
	}
	@RequestMapping(value = "/member/reserveSave.jspx")
	public void reserveSave(HttpServletRequest request,Integer pageNo,Integer userid,Patient patient,Reserve reserve,String fpaths,String fnames,
			HttpServletResponse response, ModelMap model) {
		CmsSite site = CmsUtils.getSite(request);
		CmsUser user = CmsUtils.getUser(request);
		FrontUtils.frontData(request, model, site);
		if(patient.getIdNo()!=null){
			Patient patient1 =patientMng.findByIdNo(patient.getIdNo());
			if(patient1!=null){
				patient.setId(patient1.getId());
			}
		}
		patient.setUser(user);
		if(patient.getId()!=null){
			patient=patientMng.update(patient);
		}else{
			patient.setTime(new Date());
			patient=patientMng.save(patient);
		}
		CmsUser doctor=cmsUserMng.findById(userid);
		reserve.setPatient(patient);
		reserve.setDoctorUser(doctor);
		reserve.setReserveUser(user);
		reserve.setPayStatus(false);
		reserve.setStatus(ReserveStatus.RESERVE.getValue());
		reserve.setTime(new Date());
		List<ReserveAttachment> list=new ArrayList<ReserveAttachment>();
		String paths[]=fpaths.split(",");
		String names[]=fnames.split(",");
		for(int i=0;i<paths.length;i++){
			if(paths[i]==null||paths[i].equals("")){
				continue;
			}
			ReserveAttachment ra=new ReserveAttachment(paths[i],names[i]);
			list.add(ra);
		}
		reserve.setAttachments(list);
		reserve=reserveMng.save(reserve);
		ResponseUtils.renderJson(response, reserve.getId()+"");  
	}
	

	/**
	 * 个人资料输入页
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/member/profile.jspx", method = RequestMethod.GET)
	public String profileInput(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		CmsSite site = CmsUtils.getSite(request);
		CmsUser user = CmsUtils.getUser(request);
		FrontUtils.frontData(request, model, site);
		MemberConfig mcfg = site.getConfig().getMemberConfig();
		// 没有开启会员功能
		if (!mcfg.isMemberOn()) {
			return FrontUtils.showMessage(request, model, "member.memberClose");
		}
		if (user == null) {
			return FrontUtils.showLogin(request, model, site);
		}
		model.addAttribute("_index", HOME);
		return FrontUtils.getTplPath(request, site.getSolutionPath(),
				TPLDIR_MEMBER, MEMBER_PROFILE);
	}
	/**
	 * 更换头像
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/member/portrait.jspx", method = RequestMethod.GET)
	public String portrait(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		CmsSite site = CmsUtils.getSite(request);
		CmsUser user = CmsUtils.getUser(request);
		FrontUtils.frontData(request, model, site);
		MemberConfig mcfg = site.getConfig().getMemberConfig();
		// 没有开启会员功能
		if (!mcfg.isMemberOn()) {
			return FrontUtils.showMessage(request, model, "member.memberClose");
		}
		if (user == null) {
			return FrontUtils.showLogin(request, model, site);
		}
		model.addAttribute("_index", HOME);
		return FrontUtils.getTplPath(request, site.getSolutionPath(),
				TPLDIR_MEMBER, MEMBER_PORTRAIT);
	}

	/**
	 * 个人资料提交页
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/member/profile.jspx", method = RequestMethod.POST)
	public String profileSubmit(CmsUserExt ext, String nextUrl,String telphone,String idNo,
			HttpServletRequest request, HttpServletResponse response,
			ModelMap model) throws IOException {
		CmsSite site = CmsUtils.getSite(request);
		CmsUser user = CmsUtils.getUser(request);
		if(StringUtils.isNotBlank(telphone)){
			user.setTelphone(telphone);
		}
		if(StringUtils.isNotBlank(idNo)){
			user.setIdNo(idNo);
		}
		FrontUtils.frontData(request, model, site);
		MemberConfig mcfg = site.getConfig().getMemberConfig();
		// 没有开启会员功能
		if (!mcfg.isMemberOn()) {
			return FrontUtils.showMessage(request, model, "member.memberClose");
		}
		if (user == null) {
			return FrontUtils.showLogin(request, model, site);
		}
		ext.setId(user.getId());
		ext=cmsUserExtMng.update(ext, user);
		cmsWebserviceMng.callWebService("false",user.getUsername(), null, 
				user.getEmail(),user.getTelphone(),null,null, ext,CmsWebservice.SERVICE_TYPE_UPDATE_USER);
		log.info("update CmsUserExt success. id={}", user.getId());
		model.addAttribute("_index", HOME);
		return FrontUtils.showSuccess(request, model, nextUrl);
	}

	/**
	 * 密码修改输入页
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/member/pwd.jspx", method = RequestMethod.GET)
	public String passwordInput(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		CmsSite site = CmsUtils.getSite(request);
		CmsUser user = CmsUtils.getUser(request);
		FrontUtils.frontData(request, model, site);
		MemberConfig mcfg = site.getConfig().getMemberConfig();
		// 没有开启会员功能
		if (!mcfg.isMemberOn()) {
			return FrontUtils.showMessage(request, model, "member.memberClose");
		}
		if (user == null) {
			return FrontUtils.showLogin(request, model, site);
		}
		model.addAttribute("_index", HOME);
		return FrontUtils.getTplPath(request, site.getSolutionPath(),
				TPLDIR_MEMBER, MEMBER_PASSWORD);
	}

	/**
	 * 密码修改提交页
	 * 
	 * @param origPwd
	 *            原始密码
	 * @param newPwd
	 *            新密码
	 * @param email
	 *            邮箱
	 * @param nextUrl
	 *            下一个页面地址
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/member/pwd.jspx", method = RequestMethod.POST)
	public String passwordSubmit(String origPwd, String newPwd, String email,String telphone,
			String nextUrl, HttpServletRequest request,
			HttpServletResponse response, ModelMap model) throws IOException {
		CmsSite site = CmsUtils.getSite(request);
		CmsUser user = CmsUtils.getUser(request);
		FrontUtils.frontData(request, model, site);
		MemberConfig mcfg = site.getConfig().getMemberConfig();
		// 没有开启会员功能
		if (!mcfg.isMemberOn()) {
			return FrontUtils.showMessage(request, model, "member.memberClose");
		}
		if (user == null) {
			return FrontUtils.showLogin(request, model, site);
		}
		WebErrors errors = validatePasswordSubmit(user.getId(), origPwd,
				newPwd, email, request);
		/*
		 * 演示站专用
		if(user.getUsername().equals("test")){
			errors.addErrorString("您不能修改密码哦!");
		}
		*/
		if (errors.hasErrors()) {
			return FrontUtils.showError(request, response, model, errors);
		}
		model.addAttribute("_index", HOME);
		cmsUserMng.updatePwdEmail(user.getId(), newPwd, email,telphone);
		cmsWebserviceMng.callWebService("false",user.getUsername(), newPwd, 
				email,telphone, null,null,null,CmsWebservice.SERVICE_TYPE_UPDATE_USER);
		return FrontUtils.showSuccess(request, model, nextUrl);
	}
	
	/**
	 * 完善账户资料
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/member/account.jspx", method = RequestMethod.GET)
	public String accountInput(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		CmsSite site = CmsUtils.getSite(request);
		CmsUser user = CmsUtils.getUser(request);
		FrontUtils.frontData(request, model, site);
		MemberConfig mcfg = site.getConfig().getMemberConfig();
		// 没有开启会员功能
		if (!mcfg.isMemberOn()) {
			return FrontUtils.showMessage(request, model, "member.memberClose");
		}
		if (user == null) {
			return FrontUtils.showLogin(request, model, site);
		}
		model.addAttribute("_index", HOME);
		return FrontUtils.getTplPath(request, site.getSolutionPath(),
				TPLDIR_MEMBER, MEMBER_ACCOUNT);
	}
	
	//完善用户账户资料
	@RequestMapping(value = "/member/account.jspx", method = RequestMethod.POST)
	public String accountSubmit(String accountWeiXin,String  accountAlipy,
			Short drawAccount,String nextUrl,HttpServletRequest request, 
			HttpServletResponse response,ModelMap model) throws IOException {
		CmsSite site = CmsUtils.getSite(request);
		CmsUser user = CmsUtils.getUser(request);
		FrontUtils.frontData(request, model, site);
		MemberConfig mcfg = site.getConfig().getMemberConfig();
		// 没有开启会员功能
		if (!mcfg.isMemberOn()) {
			return FrontUtils.showMessage(request, model, "member.memberClose");
		}
		if (user == null) {
			return FrontUtils.showLogin(request, model, site);
		}

		WebErrors errors=WebErrors.create(request);
		if(drawAccount==null)
		{
			errors.addErrorCode("error.needParams");
		}else{
			if(!(drawAccount==0&&StringUtils.isNotBlank(accountWeiXin)
					||drawAccount==1&&StringUtils.isNotBlank(accountAlipy))){
				errors.addErrorCode("error.needParams");
			}
		}
		if(errors.hasErrors()){
			return FrontUtils.showError(request, response, model, errors);
		}
		model.addAttribute("_index", HOME);
		cmsUserAccountMng.updateAccountInfo(accountWeiXin, accountAlipy, drawAccount,user);
		log.info("update CmsUserExt success. id={}", user.getId());
		return FrontUtils.showSuccess(request, model, nextUrl);
	}
	
	

	/**
	 * 验证密码是否正确
	 * 
	 * @param origPwd
	 *            原密码
	 * @param request
	 * @param response
	 */
	@RequestMapping("/member/checkPwd.jspx")
	public void checkPwd(String origPwd, HttpServletRequest request,
			HttpServletResponse response) {
		CmsUser user = CmsUtils.getUser(request);
		boolean pass = cmsUserMng.isPasswordValid(user.getId(), origPwd);
		ResponseUtils.renderJson(response, pass ? "true" : "false");
	}

	private WebErrors validatePasswordSubmit(Integer id, String origPwd,
			String newPwd, String email, HttpServletRequest request) {
		WebErrors errors = WebErrors.create(request);
		if (errors.ifBlank(origPwd, "origPwd", 100)) {
			return errors;
		}
		if (errors.ifMaxLength(newPwd, "newPwd", 100)) {
			return errors;
		}
		if (errors.ifNotEmail(email, "email", 100)) {
			return errors;
		}
		if (!cmsUserMng.isPasswordValid(id, origPwd)) {
			errors.addErrorCode("member.origPwdInvalid");
			return errors;
		}
		return errors;
	}
	@Autowired
	private PatientMng patientMng;
	@Autowired
	private ReserveMng reserveMng;
	@Autowired
	private CmsUserMng cmsUserMng;
	@Autowired
	private UserScheduleMng userScheduleMng;
	@Autowired
	private OrderMng orderMng;
	@Autowired
	private CmsUserExtMng cmsUserExtMng;
	@Autowired
	private CmsUserAccountMng cmsUserAccountMng;
	@Autowired
	private CmsWebserviceMng cmsWebserviceMng;
}
