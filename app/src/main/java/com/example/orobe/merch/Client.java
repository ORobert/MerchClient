package com.example.orobe.merch;

import Models.Product;
import Protocol.GetAllRequest;
import Protocol.GetAllResponse;
import Protocol.Response;
import android.os.AsyncTask;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;
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
    protected MainMenuActivity main;

    protected Client(String host, int port, MainMenuActivity main) throws IOException {
        this.host=host;
        this.port=port;
        this.main=main;
    }

    protected void readResponse(){
        Response response = null;
        try {
            response=responses.take();
            if (response instanceof GetAllResponse){
                List<Product> productsList = ((GetAllResponse) response).getProductsList();
                main.handleGetAllRequest(productsList);
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
            server = new Socket(host,port);
            this.recive = new ObjectInputStream(server.getInputStream());
            this.send = new ObjectOutputStream(server.getOutputStream());
            this.responses=new LinkedBlockingQueue<>();
            sendRequest(new GetAllRequest());
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
