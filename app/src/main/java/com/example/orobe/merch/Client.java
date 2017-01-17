package com.example.orobe.merch;

import Protocol.GetAllRequest;
import Protocol.GetAllResponse;
import Protocol.Request;
import Protocol.Response;
import android.os.AsyncTask;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by orobe on 31/12/2016.
 */
public class Client extends AsyncTask<Void,Void,Void> {
    protected String host;
    protected int port;
    protected Socket server;
    protected ObjectOutputStream send;
    protected ObjectInputStream recive;
    protected BlockingQueue<Response> responses;

    protected Client(String host, int port) throws IOException {
        this.host=host;
        this.port=port;
    }



    protected void readResponse(){
        Response response = null;
        try {
            response=responses.take();
            if (response instanceof GetAllResponse){
                System.out.print("Merge");
                System.out.println(((GetAllResponse) response).x);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    protected void sendRequest(Object request){
        try {
            send.writeObject(request);
            send.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onPostExecute(Void result){

    }

    @Override
    protected void onPreExecute(){

    }

    @Override
    protected Void doInBackground(Void... arg0) {
        System.out.println("oooooo");

        try {
            System.out.println("before socket");
            server = new Socket(host,port);
            System.out.println("after socket");
            this.recive = new ObjectInputStream(server.getInputStream());
            System.out.println("after out stream");
            this.send = new ObjectOutputStream(server.getOutputStream());
            System.out.println("after in stream");
            this.responses=new LinkedBlockingQueue<Response>();
            System.out.println("before request");
            sendRequest(new GetAllRequest());
            //sendRequest("ceva freureg4495y");
        } catch (IOException e) {
            e.printStackTrace();
        }

        while (true){
            try {
                Object response = recive.readObject();
                responses.put((Response)response);
                readResponse();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }catch (InterruptedException e) {
                e.printStackTrace();
            }
        return null;
        }
    }
}
