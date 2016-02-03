package com.alexua.messages.core.server.socketapi;

import java.io.IOException;
import java.net.URI;

import javax.websocket.ClientEndpoint;
import javax.websocket.CloseReason;
import javax.websocket.ContainerProvider;
import javax.websocket.DeploymentException;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;


@ClientEndpoint
final class WebsocketClientEndpoint {

    Session userSession = null;
    SocketListener socketListener;

    public WebsocketClientEndpoint(URI endpointURI, SocketListener socketListener) throws IOException, DeploymentException {
        this.socketListener = socketListener;
        WebSocketContainer container = ContainerProvider.getWebSocketContainer();
        container.connectToServer(this, endpointURI);

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
     * @param reason      the reason for connection close
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
        socketListener.handleMessage(message);
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
    public static abstract class SocketListener {
        public void handleMessage(String message) {
        }

        public void handleOnOpen() {
        }

        public void handleOnClose(String message) {
        }
    }

    public void close() {
        try {
            userSession.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
