package com.example.orobe.merch;

import Models.Order;
import Models.Product;
import Models.User;
import Protocol.*;
import android.os.AsyncTask;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.*;

/**
 * Created by orobe on 31/12/2016.
 */
public class Client{
    protected static String host;
    protected static int port;
    protected static Executor exec;
    protected static Socket server;
    protected static ObjectOutputStream send;
    protected static ObjectInputStream receive;
    protected static BlockingQueue<Response> responses;
	protected static BlockingQueue<Request> requests;

    public static void setupConnection(String host1, int port1) throws IOException {
        host = host1;
        port = port1;
        exec=Executors.newFixedThreadPool(10);
		(new ConnectionCreator()).executeOnExecutor(exec);
    }

    public static List<Order> getAllConfirmedOrders()throws ProtocolException{
		GetAllConfirmedOrdersRequest orderRequest = new GetAllConfirmedOrdersRequest();
		sendRequest(orderRequest);
		try {
			Response resp = responses.take();
			if(resp instanceof GetAllConfirmedOrdersResponse)
				return ((GetAllConfirmedOrdersResponse) resp).getOrders();
			else
				throw new ProtocolException("Invalid response received from the server!");
		}catch(InterruptedException e){
			e.printStackTrace();
			throw new ProtocolException("IntreruptedExcetion!");
		}
	}

    public static User login(String password, String username) throws ProtocolException{
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername(username);
        loginRequest.setPassword(password);
        sendRequest(loginRequest);
        try {
	            Response resp = responses.take();
            if(resp instanceof LoginResponse){
                User user=((LoginResponse) resp).getUser();
                if(user==null)
                    throw new ProtocolException("Incorrect username or password!");
                else
                    return user;
            }else
                throw new ProtocolException("Invalid response received from the server!");
        }catch(InterruptedException e){
            e.printStackTrace();
            throw new ProtocolException("IntreruptedExcetion!	");
        }
    }

    /*protected void readResponse(){
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
    }*/


	private static class ConnectionCreator extends AsyncTask<Void,Void,Void> {
		protected Void doInBackground(Void... args) {
			try {
				server = new Socket(host,port);
				send = new ObjectOutputStream(server.getOutputStream());
				receive = new ObjectInputStream(server.getInputStream());
				responses=new LinkedBlockingQueue<>();
				requests=new LinkedBlockingQueue<>();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void aVoid) {
			(new ThreadReader()).executeOnExecutor(exec);
			(new WriterThread()).executeOnExecutor(exec);
		}
	}

    private static class ThreadReader extends AsyncTask<Void,Void,Void>{
		@Override
		protected Void doInBackground(Void... params) {
			while(true){
				try {
					Response response=(Response) receive.readObject();
					System.out.println("response received "+response);
						try {
							responses.put(response);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
				} catch (IOException e) {
					e.printStackTrace();
					return null;
				}catch(ClassNotFoundException er) {
					er.printStackTrace();
					return null;
				}
			}
		}
	}

    public static void sendRequest(Request request){
		try {
			requests.put(request);
		}catch (InterruptedException e){
			e.printStackTrace();
		}
    }

	private static class WriterThread extends AsyncTask<Void,Void,Void> {
		protected Void doInBackground(Void... args) {
			while(true) {
				try {
					Request request = requests.take();
					send.writeObject(request);
					send.flush();
				} catch (IOException e) {
					e.printStackTrace();
					return null;
				} catch (InterruptedException er) {
					er.printStackTrace();
					return null;
				}
			}
		}
	}

    /*@Override
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
    }*/
}
