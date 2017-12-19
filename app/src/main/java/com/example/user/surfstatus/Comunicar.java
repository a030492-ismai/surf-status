package com.example.user.surfstatus;

import android.util.Base64;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;


public class Comunicar {
    public static String contactar(String host, int port, String path) throws IOException {
        URL url = new URL("http", host, port, path);
        URLConnection conn = url.openConnection();
        // Isto faz um http GET, para fazer um POST usar: conn.setDoOutput(true);
        conn.setDoInput(true);
        conn.setAllowUserInteraction(true); // useless but harmless
        conn.connect();
        // Para fazer um http POST escrevia-se para: conn.getOutputStream());
        StringBuilder sb = new StringBuilder();
        BufferedReader in = new BufferedReader(
                new InputStreamReader(conn.getInputStream()));
        String line;
        while ((line = in.readLine()) != null) {
            sb.append(line);
        }
        in.close();
        return sb.toString();
    }
    public static String contactar(String host, int port, String path, String user, String password) throws IOException {
        String userpass = user + ":" + password;
        String basicAuth = "Basic " + new String(Base64.encode(userpass.getBytes(),Base64.NO_WRAP ));
        URL url = new URL("http", host, port, path);
        URLConnection conn = url.openConnection();
        conn.setRequestProperty("Authorization", basicAuth);
        // Isto faz um http GET, para fazer um POST usar: conn.setDoOutput(true);
        conn.setDoInput(true);
        conn.setAllowUserInteraction(true); // useless but harmless
        conn.connect();
        // Para fazer um http POST escrevia-se para: conn.getOutputStream());
        StringBuilder sb = new StringBuilder();
        BufferedReader in = new BufferedReader(
                new InputStreamReader(conn.getInputStream()));
        String line;
        while ((line = in.readLine()) != null) {
            sb.append(line);
        }
        in.close();
        return sb.toString();
    }
    public static String contactar2(String host, String path, int port) {
        String resultado = "<unknown>";
        try {
            resultado = contactar(host, port, path);
        } catch (IOException e) {
            resultado = "<failure>";
        }
        return resultado;
    }
    public static String contactar2(String host, String path, int port, String user, String password) {
        String resultado = "<unknown>";
        try {
            resultado = contactar(host, port, path, user, password);
        } catch (IOException e) {
            resultado = "<failure>";
        }
        return resultado;
    }
}
