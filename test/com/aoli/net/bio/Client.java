package com.aoli.net.bio;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class Client {
    public static void main(String[] args) throws Exception{
        Socket s = new Socket("localhost", 8888);
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));
        bw.write("test");
        bw.newLine();
        bw.flush();

        BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream()));
        String str = br.readLine();//阻塞步骤
        System.out.println(str);
        bw.close();
    }
}
