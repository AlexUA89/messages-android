package com.alexua.messages.core.server.socketapi;

import javax.websocket.*;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;


public class WebsocketClientEndpoint {

    Session userSession = null;
    private List<SocketListener> socketListeners = new ArrayList<>();

    public WebsocketClientEndpoint(URI endpointURI) {
        try {
            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
            container.connectToServer(this, endpointURI);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Callback hook for Connection open events.
     *
     * @param userSession the userSession which is opened.
     */
    @OnOpen
    private void onOpen(Session userSession) {
        this.userSession = userSession;
        this.userSession.setMaxIdleTimeout(0);
        for(SocketListener handler : this.socketListeners) {
            handler.handleOnOpen();
        }
    }

    /**
     * Callback hook for Connection close events.
     *
     * @param userSession the userSession which is getting closed.
     * @param reason the reason for connection close
     */
    @OnClose
    private void onClose(Session userSession, CloseReason reason) {
        this.userSession = null;
        for(SocketListener handler : this.socketListeners) {
            handler.handleOnClose(reason.getReasonPhrase());
        }
    }

    /**
     * Callback hook for Message Events. This method will be invoked when a client send a message.
     *
     * @param message The text message
     */
    @OnMessage
    private void onMessage(String message) {
        if (this.socketListeners != null) {
            for(SocketListener handler : this.socketListeners) {
                handler.handleMessage(message);
            }
        }
    }

    /**
     * register message handler
     *
     * @param message
     */
    public void addSocketListener(SocketListener msgHandler) {
        this.socketListeners.add(msgHandler);
    }

    /**
     * Send a message.
     *
     * @param user
     * @param message
     */
    public void sendMessage(String message) {
        this.userSession.getAsyncRemote().sendText(message);
    }

    /**
     * Message handler.
     *
     * @author Jiji_Sasidharan
     */
    public interface SocketListener {
        void handleMessage(String message);
        void handleOnOpen();
        void handleOnClose(String message);
    }

}
