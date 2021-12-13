package com.tf.backend.websocket.controller;

import com.tf.backend.websocket.dto.SendMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
public class WebSocketController {

    private SimpMessagingTemplate messagingTemplate;

    public WebSocketController(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @MessageMapping("/welcome")
    public String say(SendMessage message) {
        System.out.println(message.getName());
        return "Hello,World!";
    }

    /* 定时推送消息 */
    @Scheduled(fixedDelay = 1000)
    public void send() {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        messagingTemplate.convertAndSend("/topic/dashboard", "推送时间：" + df.format(new Date()));
    }
}