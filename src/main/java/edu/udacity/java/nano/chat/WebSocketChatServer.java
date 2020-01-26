package edu.udacity.java.nano.chat;

import com.alibaba.fastjson.JSON;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * WebSocket Server
 *
 * @see ServerEndpoint WebSocket Client
 * @see Session   WebSocket Session
 */

@Component
@ServerEndpoint("/chat")
public class WebSocketChatServer {

    /**
     * All chat sessions.
     */
    private static Map<String, Session> onlineSessions = new ConcurrentHashMap<>();

    private static void sendMessageToAll(String msg) {

        for(Session session : onlineSessions.values()) {
            try {
                System.out.println("Send Message to All :: " + msg);
                session.getBasicRemote().sendText(msg);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Open connection, 1) add session, 2) add user.
     */
    @OnOpen
    public void onOpen(Session session) {
        System.out.println("onOpen :: sessionID :: " + session.getId());
        System.out.println("onOpen :: qry :: " + session.getQueryString());
        onlineSessions.putIfAbsent(session.getId(), session);
        Message message = new Message("SystemUser", "New user connected!!!");
        message.setType("OPEN");
        message.setOnlineCount(onlineSessions.size());
        sendMessageToAll(JSON.toJSONString(message));

    }

    /**
     * Send message, 1) get username and session, 2) send message to all.
     */
    @OnMessage
    public void onMessage(Session session, String jsonStr) {

        System.out.println("on Message");
        System.out.println("onMessage :: String ::" + jsonStr);
        Message message = JSON.parseObject(jsonStr, Message.class);
        message.setType("SPEAK");
        message.setOnlineCount(onlineSessions.size());

        sendMessageToAll(JSON.toJSONString(message));
    }

    /**
     * Close connection, 1) remove session, 2) update user.
     */
    @OnClose
    public void onClose(Session session) {

        System.out.println("onClose :: sessionID :: " + session.getId());
        System.out.println("onClose :: qry :: " + session.getQueryString());
        onlineSessions.remove(session.getId());
        Message message = new Message("SystemUser", "User disconnected!!!");
        message.setType("CLOSE");
        message.setOnlineCount(onlineSessions.size());
        sendMessageToAll(JSON.toJSONString(message));
    }

    /**
     * Print exception.
     */
    @OnError
    public void onError(Session session, Throwable error) {
        error.printStackTrace();
    }

}
