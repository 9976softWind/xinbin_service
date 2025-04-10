package com.wims.iot.common.websocket;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.util.concurrent.CopyOnWriteArraySet;

@Component
@ServerEndpoint("/webSocket")
@CrossOrigin //跨域
@Slf4j
public class WebSocket {

        //与某个客户端的连接会话，需要通过它来给客户端发送数据
        private Session session;
        //concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。
        private static CopyOnWriteArraySet<WebSocket> webSocketSet = new CopyOnWriteArraySet<WebSocket>();

        /**
         * 连接建立成功调用的方法
         */
        @OnOpen
        public void onOpen(Session session){
            this.session=session;
            webSocketSet.add(this); //加入set中
            log.info("[WebSocket] new connections, total:{}",webSocketSet.size());
        }

        /**
         * 连接关闭调用的方法
         */
        @OnClose
        public void onClose(){
            webSocketSet.remove(this);//从set中删除
            log.info("[WebSocket] disconnected, total:{}",webSocketSet.size());
        }

        /**
         * 收到客户端消息后调用的方法
         * @param message 客户端发送过来的消息
         */
        @OnMessage
        public void onMessage(String message){
            log.info("[WebSocket] receive from client:{}",message);
        }

    /**
     * 主动发送消息给客户端
     * @param message
     */
    public void sendMessage(String message){
            for (WebSocket webSocket:webSocketSet) {
                log.info("[webSocket] send message:{}",message);
                try {
                    webSocket.session.getBasicRemote ().sendText(message);
                } catch (Exception e) {
                    e.printStackTrace ();
                }
            }
        }

}
