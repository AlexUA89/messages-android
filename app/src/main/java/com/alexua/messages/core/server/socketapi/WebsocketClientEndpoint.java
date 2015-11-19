package com.alexua.messages.core.server.socketapi;

import javax.websocket.*;
import java.io.IOException;
import java.net.URI;

/**
 * ChatServer Client
 *
 * @author Jiji_Sasidharan
 */
@ClientEndpoint
public class WebsocketClientEndpoint {

    Session userSession = null;
    private SocketListener socketListener;

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
    public void onOpen(Session userSession) {
        this.userSession = userSession;
        this.userSession.setMaxIdleTimeout(0);
        socketListener.handleOnOpen();
    }

    /**
     * Callback hook for Connection close events.
     *
     * @param userSession the userSession which is getting closed.
     * @param reason the reason for connection close
     */
    @OnClose
    public void onClose(Session userSession, CloseReason reason) {
        this.userSession = null;
        socketListener.handleOnClose(reason.getReasonPhrase());
    }

    /**
     * Callback hook for Message Events. This method will be invoked when a client send a message.
     *
     * @param message The text message
     */
    @OnMessage
    public void onMessage(String message) {
        if (this.socketListener != null) {
            this.socketListener.handleMessage(message);
        }
    }

    /**
     * register message handler
     *
     * @param message
     */
    public void addSocketListener(SocketListener msgHandler) {
        this.socketListener = msgHandler;
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
    public static interface SocketListener {
        public void handleMessage(String message);
        public void handleOnOpen();
        public void handleOnClose(String message);
    }

    public void close() {
        try {
            userSession.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
