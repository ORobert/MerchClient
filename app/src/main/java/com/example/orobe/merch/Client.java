package com.example.orobe.merch;

import Models.Product;
import Protocol.*;
import android.app.Activity;
import android.os.AsyncTask;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by orobe on 31/12/2016.
 */
public class Client extends AsyncTask<Void,Void,Void> implements Serializable{
    protected String username;
    protected String password;
    protected String host;
    protected int port;
    protected Socket server;
    protected ObjectOutputStream send;
    protected ObjectInputStream recive;
    protected BlockingQueue<Response> responses;
    protected Activity activity;

    protected Client(String host, int port, String username, String password) throws IOException {
        this.host=host;
        this.port=port;
        this.password=password;
        this.username=username;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    protected void readResponse(){
        Response response = null;
        try {
            response=responses.take();
            if (response instanceof GetAllResponse){
//                List<Product> productsList = ((GetAllResponse) response).getProductsList();
//                activity.handleGetAllRequest(productsList);
            }
            if (response instanceof LoginResponse){
                ((LoginActivity) activity).swhitchToActivity();
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
        try {
            server = new Socket(host,port);
            this.recive = new ObjectInputStream(server.getInputStream());
            this.send = new ObjectOutputStream(server.getOutputStream());
            this.responses=new LinkedBlockingQueue<>();
            LoginRequest loginRequest = new LoginRequest();
            loginRequest.setUsername(username);
            loginRequest.setPassword(password);
            sendRequest(loginRequest);
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
