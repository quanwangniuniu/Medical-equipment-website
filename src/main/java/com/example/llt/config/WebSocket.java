package com.example.llt.config;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.CopyOnWriteArraySet;

//注册成组件
@Component
//定义websocket服务器端，它的功能主要是将目前的类定义成一个websocket服务器端。注解的值将被用于监听用户连接的终端访问URL地址
@ServerEndpoint("/websocket/{username}")
//如果不想每次都写private  final Logger logger = LoggerFactory.getLogger(当前类名.class); 可以用注解@Slf4j;可以直接调用log.info
@Slf4j
public class WebSocket {

    //实例一个session，这个session是websocket的session
    private Session session;

    //存放当前用户名
    private String userName;

    //存放需要接受消息的用户名
    private String toUserName;

    //存放在线的用户数量
    private static Integer userNumber = 0;

    //存放websocket的集合
    private static CopyOnWriteArraySet<WebSocket> webSocketSet = new CopyOnWriteArraySet<>();

    //前端请求时一个websocket时
    @OnOpen
    public void onOpen(Session session,@PathParam("username") String username) throws IOException {
        this.session = session;
        // 将当前对象放入webSocketSet
        webSocketSet.add(this);
        // 增加在线人数
        userNumber++;
        // 保存当前用户名
        this.userName = username;
        //获得所有的用户
        Set<String> userLists = new TreeSet<>();
        for (WebSocket webSocket : webSocketSet) {
            userLists.add(webSocket.userName);
        }

        // 将所有信息包装好传到客户端(给所有用户)
        Map<String, Object> map1 = new HashMap();
        //  把所有用户列表
        map1.put("onlineUsers", userLists);
        //messageType 1代表上线 2代表下线 3代表在线名单 4代表普通消息
        map1.put("messageType", 1);
        //  返回用户名
        map1.put("username", username);
        //  返回在线人数
        map1.put("number", this.userNumber);
        //  发送给所有用户谁上线了，并让他们更新自己的用户菜单
        sendMessageAll(JSON.toJSONString(map1),this.userName);
        log.info("【websocket消息】有新的连接, 总数:{}", this.userNumber);

        // 更新在线人数(给所有人)
        Map<String, Object> map2 = new HashMap();
        //  messageType 1代表上线 2代表下线 3代表在线名单 4代表普通消息
        map2.put("messageType", 3);
        //  把所有用户放入map2
        map2.put("onlineUsers", userLists);
        //  返回在线人数
        map2.put("number", this.userNumber);
        // 消息发送指定人（所有的在线用户信息）
        sendMessageAll(JSON.toJSONString(map2),this.userName);
    }

    //  前端关闭时一个websocket时
    @OnClose
    public void onClose() throws IOException {
        //  从集合中移除当前对象
        webSocketSet.remove(this);
        //  在线用户数减少
        userNumber--;
        Map<String, Object> map1 = new HashMap();
        //  messageType 1代表上线 2代表下线 3代表在线名单 4代表普通消息
        map1.put("messageType", 2);
        //  所有在线用户
        map1.put("onlineUsers", this.webSocketSet);
        //  下线用户的用户名
        map1.put("username", this.userName);
        //  返回在线人数
        map1.put("number", userNumber);
        //   发送信息，所有人，通知谁下线了
        sendMessageAll(JSON.toJSONString(map1),this.userName);
        log.info("【websocket消息】连接断开, 总数:{}", webSocketSet.size());
    }

    //  前端向后端发送消息
    @OnMessage
    public void onMessage(String message) throws IOException {
        log.info("【websocket消息】收到客户端发来的消息:{}", message);
        //  将前端传来的数据进行转型
        com.alibaba.fastjson.JSONObject jsonObject = JSON.parseObject(message);
        //  获取所有数据
        String textMessage = jsonObject.getString("message");
        String username = jsonObject.getString("username");
        String type = jsonObject.getString("type");
        String tousername = jsonObject.getString("tousername");
        // 群发
        if(type.equals("群发")){
            Map<String, Object> map3 = new HashMap();
            map3.put("messageType", 4);
            // 所有在线用户
            map3.put("onlineUsers", this.webSocketSet);
            // 发送消息的用户名
            map3.put("username", username);
            // 返回在线人数
            map3.put("number", userNumber);
            // 发送的消息
            map3.put("textMessage", textMessage);
            // 发送信息，所有人，通知谁下线了
            sendMessageAll(JSON.toJSONString(map3),this.userName);
        }
        // 私发
        else{
            // 发送给对应的私聊用户
            Map<String, Object> map3 = new HashMap();
            map3.put("messageType", 4);
            // 所有在线用户
            map3.put("onlineUsers", this.webSocketSet);
            // 发送消息的用户名
            map3.put("username", username);
            // 返回在线人数
            map3.put("number", userNumber);
            // 发送的消息
            map3.put("textMessage", textMessage);
            // 发送信息，所有人，通知谁下线了
            sendMessageTo(JSON.toJSONString(map3),tousername);

            // 发送给自己
            Map<String, Object> map4 = new HashMap();
            map4.put("messageType", 4);
            // 所有在线用户
            map4.put("onlineUsers", this.webSocketSet);
            // 发送消息的用户名
            map4.put("username", username);
            // 返回在线人数
            map4.put("number", userNumber);
            // 发送的消息
            map4.put("textMessage", textMessage);
            // 发送信息，所有人，通知谁下线了
            sendMessageTo(JSON.toJSONString(map3),username);
        }
    }

    /**
     *  消息发送所有人
     */
    public void sendMessageAll(String message, String FromUserName) throws IOException {
        for (WebSocket webSocket: webSocketSet) {
            // 消息发送所有人（同步）getAsyncRemote
            webSocket.session.getBasicRemote().sendText(message);
        }
    }

    /**
     *  消息发送指定人
     */
    public void sendMessageTo(String message, String ToUserName) throws IOException {
        //遍历所有用户
        for (WebSocket webSocket : webSocketSet) {
            if (webSocket.userName.equals(ToUserName)) {
                //消息发送指定人
                webSocket.session.getBasicRemote().sendText(message);
                log.info("【发送消息】:", this.userName+"向"+ToUserName+"发送消息："+message);
                break;
            }
        }
    }

}
