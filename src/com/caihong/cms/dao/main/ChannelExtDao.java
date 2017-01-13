package com.caihong.cms.dao.main;

import com.caihong.cms.entity.main.ChannelExt;
import com.caihong.common.hibernate4.Updater;

public interface ChannelExtDao {
	public ChannelExt save(ChannelExt bean);

	public ChannelExt updateByUpdater(Updater<ChannelExt> updater);
}