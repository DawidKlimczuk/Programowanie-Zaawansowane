package org.example;

import java.io.*;
import java.net.*;
import java.util.Random;

public class Client {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost", 12345);
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

        String response = in.readLine();
        if ("OK".equals(response)) {
            int clientId = Integer.parseInt(in.readLine());
            requestAndPrintObjects(out, in, "get_Kot", clientId);
            requestAndPrintObjects(out, in, "get_Pies", clientId);
            requestAndPrintObjects(out, in, "get_Krolik", clientId);
        } else {
            System.out.println("Connection refused");
        }

        socket.close();
    }

    private static void requestAndPrintObjects(PrintWriter out, BufferedReader in, String request, int clientId) throws IOException {
        out.println(request);
        String response = in.readLine();
        if (response.equals("ERROR")) {
            System.out.println("Client " + clientId + " received an error.");
            return;
        }
        String[] objects = response.split(";");
        System.out.println("Client " + clientId + " received " + request.split("_")[1] + ":");
        for (String obj : objects) {
            System.out.println(obj);
        }
    }
}
