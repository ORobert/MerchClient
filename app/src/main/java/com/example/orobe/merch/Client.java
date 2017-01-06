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
        server = new Socket(host,port);
        this.send = new ObjectOutputStream(server.getOutputStream());
        this.recive = new ObjectInputStream(server.getInputStream());
        this.responses=new LinkedBlockingQueue<Response>();
    }



    protected void readResponse(){
        Response response = null;
        try {
            response=responses.take();
            if (response instanceof GetAllResponse){
                System.out.print("Merge");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    protected void sendRequest(Request request){
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
        sendRequest(new GetAllRequest());
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
        }
    }
}
