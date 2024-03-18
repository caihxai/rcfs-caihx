package com.caihx.rcfs.watch.client;

import com.caihx.rcfs.watch.queue.WatchConsumerQueue;
import com.rcfs.caihx.common.specific.RcfsWatchTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class ClientServer implements Runnable {

    public static final Logger log = LoggerFactory.getLogger(ClientServer.class);

    private Integer port;

    private String path;

    private String host = "http://127.0.0.1";

    private RcfsWatchTask rcfsWatchTask;

    public ClientServer(Integer port, String path, RcfsWatchTask rcfsWatchTask) {
        this.port = port;
        this.path = path;
        this.rcfsWatchTask = rcfsWatchTask;
    }

    @Override
    public void run() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            log.info("Rcfs Message - Service started, listening " + port + " port");
            log.info("Rcfs Message - Service client access address[" + host + ":" + port + path + "]");
            while (true) {
                try (Socket clientSocket = serverSocket.accept();
                     PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                     BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {
                    log.info("Rcfs Message - Client: Received a request to refresh the configuration notification");
                    String request = in.readLine();
                    rcfsWatchTask.execHttp();

                    // 构建HTTP响应
                    String responseHeaders = "HTTP/1.1 200 OK\r\n" +
                            "Content-Type: text/plain; charset=UTF-8\r\n" +
                            "Content-Length: 3\r\n" +
                            "\r\n";
                    // 发送响应头和响应体
                    out.println(responseHeaders);
                    out.println("Refresh successful");
                    out.flush();
                } catch (IOException e) {
                    log.error("Rcfs Message - An error occurred while processing client connections: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
