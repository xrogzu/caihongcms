package com.caihong.cms.dao.main;

import com.caihong.cms.entity.main.ChannelTxt;
import com.caihong.common.hibernate4.Updater;

public interface ChannelTxtDao {
	public ChannelTxt findById(Integer id);

	public ChannelTxt save(ChannelTxt bean);

	public ChannelTxt updateByUpdater(Updater<ChannelTxt> updater);
}