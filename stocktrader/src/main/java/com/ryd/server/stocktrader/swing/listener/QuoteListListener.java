package com.ryd.server.stocktrader.swing.listener;

import com.ryd.basecommon.protocol.protobuf.DiyNettyMessage;
import com.ryd.basecommon.util.ApplicationConstants;
import com.ryd.server.stocktrader.swing.common.ClientConstants;
import com.ryd.server.stocktrader.swing.service.impl.MessageServiceImpl;
import com.ryd.server.stocktrader.utils.TestParamBuilderUtil;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;

/**
 * <p>标题:报价列表监听</p>
 * <p>描述:报价列表监听</p>
 * 包名：com.ryd.stockanalysis.util
 * 创建人：songby
 * 创建时间：2016/4/6 10:12
 */
public class QuoteListListener extends MouseAdapter implements ActionListener {

	public QuoteListListener() {

	}

	@Override
	public void actionPerformed(ActionEvent e) {

		DiyNettyMessage.NettyMessage.Builder builder = TestParamBuilderUtil.getAccount(ApplicationConstants.NETTYMESSAGE_ID_QUOTE,ClientConstants.stAccount.getId(),null,null);
		MessageServiceImpl.sendMessage(builder.build());
	}
}
