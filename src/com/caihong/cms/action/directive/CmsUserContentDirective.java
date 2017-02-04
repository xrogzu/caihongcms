package com.caihong.cms.action.directive;

import static com.caihong.common.web.freemarker.DirectiveUtils.OUT_BEAN;
import static com.caihong.common.web.freemarker.DirectiveUtils.OUT_LIST;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.caihong.cms.ws.Topic;
import com.caihong.cms.ws.TopicHttpSender;
import com.caihong.common.web.freemarker.DefaultObjectWrapperBuilderFactory;
import com.caihong.common.web.freemarker.DirectiveUtils;
import com.caihong.core.entity.CmsUser;
import com.caihong.core.manager.CmsUserMng;
import com.caihong.core.web.util.FrontUtils;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

/**
 * 内容对象标签
 */
public class CmsUserContentDirective implements TemplateDirectiveModel {
	/**
	 * 输入参数，栏目ID。
	 */
	public static final String PARAM_ID = "id";	

	@SuppressWarnings("unchecked")
	public void execute(Environment env, Map params, TemplateModel[] loopVars,
			TemplateDirectiveBody body) throws TemplateException, IOException {
		String username=DirectiveUtils.getString(PARAM_ID, params);
		CmsUser user=new CmsUser();
		if (username != null) {
			user = cmsUserMng.findByUsername(username);
		} 
		
		List<Topic> list=TopicHttpSender.getUserTopic(username,null, null);
		Map<String, TemplateModel> paramWrap = new HashMap<String, TemplateModel>(params);
		paramWrap.put(OUT_BEAN, DefaultObjectWrapperBuilderFactory.getDefaultObjectWrapper().wrap(user));
		paramWrap.put(OUT_LIST, DefaultObjectWrapperBuilderFactory.getDefaultObjectWrapper().wrap(list));
		Map<String, TemplateModel> origMap = DirectiveUtils
				.addParamsToVariable(env, paramWrap);
		body.render(env.getOut());
		DirectiveUtils.removeParamsFromVariable(env, paramWrap, origMap);
	}

	@Autowired
	private CmsUserMng cmsUserMng;
}
