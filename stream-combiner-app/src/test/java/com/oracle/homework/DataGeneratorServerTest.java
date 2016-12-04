package com.oracle.homework;


import org.junit.Test;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class DataGeneratorServerTest {
    @Test
    public void name() throws Exception {

        ServerSocket listener = new ServerSocket(2222);
        Socket socket = listener.accept();

        OutputStream out = socket.getOutputStream();
        InputStream in = socket.getInputStream();
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out));
        while (socket.isConnected()) {
            writer.write(".");
        }
    }
}
