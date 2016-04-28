package com.ryd.server.stocktrader.handle;

import com.ryd.basecommon.protocol.protobuf.DiyNettyMessage;
import com.ryd.basecommon.util.ApplicationConstants;
import com.ryd.business.model.StAccount;
import com.ryd.business.service.StAccountService;
import com.ryd.business.service.impl.StAccountServiceImpl;
import io.netty.channel.ChannelHandlerContext;

/**
 * 客户端消息处理
 *
 * Created by Administrator on 2016/4/22.
 */
public class MessageHandle {
    public static ChannelHandlerContext ctx;

    private static StAccountService stAccountService = new StAccountServiceImpl();
    /**
     * 处理客户端消息
     * @param request
     * @return
     */
    public static DiyNettyMessage.NettyMessage handleClientRequestInfo(DiyNettyMessage.NettyMessage request) {
        DiyNettyMessage.NettyMessage.Builder builder = DiyNettyMessage.NettyMessage.newBuilder();
        switch (request.getId()){
            case ApplicationConstants.NETTYMESSAGE_ID_HEARTBEAT:
                builder.setId(request.getId());
                builder.setStatus(ApplicationConstants.NETTYMESSAGE_STATUS_RESPONSE_SUCCESS);
                break;
            case ApplicationConstants.NETTYMESSAGE_ID_STOCKINFO:
                break;
            case ApplicationConstants.NETTYMESSAGE_ID_STOCKPRICEDETAIL:
                break;
            case ApplicationConstants.NETTYMESSAGE_ID_LOGIN:
                DiyNettyMessage.AccountInfo acc = request.getAccountInfoList().get(0);
                StAccount account = stAccountService.findStAccount(acc.getAccountNum(), acc.getPassword());

                builder.setId(request.getId());
                builder.addAccountInfo(DiyNettyMessage.AccountInfo.newBuilder().setAccountId(account.getId()).setAccountNum(account.getAccountNum()));
                break;
            case ApplicationConstants.NETTYMESSAGE_ID_QUOTE:
                break;
            case ApplicationConstants.NETTYMESSAGE_ID_WITHDRAWORDER:
                break;
            case ApplicationConstants.NETTYMESSAGE_ID_MYCAPITAL:
                break;
            case ApplicationConstants.NETTYMESSAGE_ID_MYPOSITION:
                break;
            case ApplicationConstants.NETTYMESSAGE_ID_MYQUOTELIST:
                break;
            case ApplicationConstants.NETTYMESSAGE_ID_MYTRADERECORD:
                break;
            case ApplicationConstants.NETTYMESSAGE_ID_MYMONEYJOURNAL:
                break;
            default:
                // 默认状态
                break;
        }

        return builder.build();
    }

    /**
     * 发送消息给客户端
     * @param message
     */
    public static void sendMessage(DiyNettyMessage.NettyMessage message) {
        ctx.writeAndFlush(message);
    }
}