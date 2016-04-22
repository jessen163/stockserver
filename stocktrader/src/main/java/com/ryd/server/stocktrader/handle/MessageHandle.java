package com.ryd.server.stocktrader.handle;

import com.ryd.basecommon.protocol.protobuf.DiyNettyMessage;
import com.ryd.basecommon.util.ApplicationConstants;
import io.netty.channel.ChannelHandlerContext;

/**
 * 客户端消息处理
 *
 * Created by Administrator on 2016/4/22.
 */
public class MessageHandle {
    public static ChannelHandlerContext ctx;

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