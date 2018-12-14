package com.garbageview.garbageview;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;

@Component
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(new SocketHandler(), "/garbageview"); //http://localhost:8080/garbageview //anytime someone connects to thise endpoint, call the socketHandler
    }

    @Component
    class SocketHandler extends TextWebSocketHandler
    {

//    List<WebSocketSession> sessions = new CopyOnWriteArrayList<>();

        @Override
        public void handleTextMessage(WebSocketSession session, TextMessage message)
            throws InterruptedException, IOException
        {

//        for(WebSocketSession webSocketSession : GCInformation.sessions) {
////            Map value = new Gson().fromJson(message.getPayload(), Map.class); //Get the data from the JS side
//            webSocketSession.sendMessage(new TextMessage("Hello!")); //send message to JS side
//        }
        }

        @Override
        public void afterConnectionEstablished(WebSocketSession session) throws Exception {
            //the messages will be broadcasted to all users.
            System.out.println("");
            System.out.println("***  in Connection established");
            GCInformation.sessions.add(session);
            System.out.println("*** sessions size: " + GCInformation.sessions.size());
            System.out.println("*** passed add session");
//here, add to the list in <></>he GCInformation
            //when connection closed remove it
        }

        @Override
        public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
            //remove ws from set when connection is closed
            System.out.println("in Connection closed");
            GCInformation.sessions.remove(session);
            System.out.println("passed remove session");
        }
    }
}




