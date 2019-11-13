package io.springboot.log.web.socket.channel;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.websocket.CloseReason;
import javax.websocket.EndpointConfig;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


@Component
@ServerEndpoint(value = "/channel/log")
public class LogChannel {

	private static final Logger LOGGER = LoggerFactory.getLogger(LogChannel.class);
	
	public static final Map<String, Session> CHANNELS = new ConcurrentHashMap<>();

	private Session session;

	@OnMessage(maxMessageSize = 10)
	public void onMessage(byte[] data){
		// 忽略客户端发来的消息
	}

	@OnOpen
	public void onOpen(Session session, EndpointConfig endpointConfig){
		LOGGER.info("新的连接,id={}",session.getId());
		session.setMaxIdleTimeout(0);
		this.session = session;
		CHANNELS.put(this.session.getId(), session);
	}

	@OnClose
	public void onClose(CloseReason closeReason){
		LOGGER.info("连接断开,id={} reason={}", this.session.getId(),closeReason);
		CHANNELS.remove(this.session.getId());
	}

	@OnError
	public void onError(Throwable throwable) throws IOException {
		LOGGER.info("连接异常,id={},throwable={}", this.session.getId(),throwable);
		throwable.printStackTrace();
		this.session.close();
	}
}
