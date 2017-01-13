package com.caihong.cms.manager.main;

import com.caihong.cms.entity.main.Channel;
import com.caihong.cms.entity.main.ChannelExt;

public interface ChannelExtMng {
	public ChannelExt save(ChannelExt ext, Channel channel);

	public ChannelExt update(ChannelExt ext);
}