package com.example.orobe.merch;

import Models.Order;
import Models.Product;
import Models.User;
import Protocol.*;
import android.annotation.TargetApi;
import android.os.AsyncTask;
import android.os.Build;
import android.os.SystemClock;
import android.support.v4.app.Fragment;

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
	protected static User user;
    protected static String host;
    protected static int port;
    public static Executor exec;
    protected static Socket server;
    protected static ObjectOutputStream send;
    protected static ObjectInputStream receive;
    protected static BlockingQueue<Response> responses;
	protected static BlockingQueue<Request> requests;

	public static void setUser(User user) {
		Client.user = user;
	}

	public static void setupConnection(String host1, int port1) throws IOException {
        host = host1;
        port = port1;
        exec=Executors.newFixedThreadPool(10);
		(new ConnectionCreator()).executeOnExecutor(exec);
	}

	public static List<Product> getAllProducts()throws ProtocolException{
    	GetAllRequest getAllRequest = new GetAllRequest();
    	sendRequest(getAllRequest);
    	try{
    		Response resp = responses.take();
    		if (resp instanceof GetAllResponse)
    			return ((GetAllResponse) resp).getProductsList();
    		else
    			throw new ProtocolException("Invalid response from server in getAllProducts");
		}catch (InterruptedException e){
    		e.printStackTrace();
    		throw new ProtocolException("IntreruptedExcetion");
		}
	}

	public static AsyncTask<Object,Double,Void> startUpdaterThread(OrderDetailsFragment frag, Order order){
		AsyncTask<Object,Double,Void> obj=new MapUpdaterThread();
		obj.executeOnExecutor(exec,frag,order);
		return obj;
	}

	public static List<Order> getOrdersByDriver(User driver)throws ProtocolException{
		GetOrdersByDriverRequest orderRequest = new GetOrdersByDriverRequest(driver);
		sendRequest(orderRequest);
		try {
			Response resp = responses.take();
			if(resp instanceof GetOrdersResponse)
				return ((GetOrdersResponse) resp).getOrders();
			else
				throw new ProtocolException("Invalid response received from the server!");
		}catch(InterruptedException e){
			e.printStackTrace();
			throw new ProtocolException("IntreruptedExcetion!");
		}
	}

    public static List<Order> getConfirmedOrders()throws ProtocolException{
		GetConfirmedOrdersRequest orderRequest = new GetConfirmedOrdersRequest();
		sendRequest(orderRequest);
		try {
			Response resp = responses.take();
			if(resp instanceof GetOrdersResponse)
				return ((GetOrdersResponse) resp).getOrders();
			else
				throw new ProtocolException("Invalid response received from the server!");
		}catch(InterruptedException e){
			e.printStackTrace();
			throw new ProtocolException("IntreruptedExcetion!");
		}
	}

	public static List<Product> getProductsByOrder(Order order) throws ProtocolException{
		GetProductsByOrderRequest getRequest = new GetProductsByOrderRequest(order);
		sendRequest(getRequest);
		try {
			Response resp = responses.take();
			if(resp instanceof GetAllResponse)
				return ((GetAllResponse) resp).getProductsList();
			else{
				throw new ProtocolException("Valoare incorecta de la server!");
			}
		}catch(InterruptedException e){
			e.printStackTrace();
			throw new ProtocolException("IntreruptedExcetion!");
		}
	}

	public static void takeOrders(List<Order> orders)throws ProtocolException{
		TakeOrdersRequest takeRequest = new TakeOrdersRequest(orders);
		sendRequest(takeRequest);
		try {
			Response resp = responses.take();
			if(resp instanceof ErrorResponse)
				throw new ProtocolException("Error while taking orders!");
		}catch(InterruptedException e){
			e.printStackTrace();
			throw new ProtocolException("IntreruptedExcetion!");
		}
	}

	public static void takeUsersOrders(List<Product> products) throws ProtocolException {
    	OrderProductsRequest orderProductsRequest = new OrderProductsRequest(user.getId(),products);
    	sendRequest(orderProductsRequest);
    	try {
    		Response resp = responses.take();
    		if (resp instanceof ErrorResponse)
				throw new ProtocolException("Error while taking orders from user!");
		}catch (InterruptedException e){
			e.printStackTrace();
			throw new ProtocolException("IntreruptedExcetion!");
		}
	}

	public static void updateOrderLocation(List<Order> orders,double longitude, double latitude){
		UpdateLocationRequest updateRequest = new UpdateLocationRequest();
		updateRequest.setLatitude(latitude);
		updateRequest.setLongitude(longitude);
		updateRequest.setOrders(orders);
		sendRequest(updateRequest);
	}

	public static void deliverOrder(Order order)throws ProtocolException{
		DeliverOrderRequest takeRequest = new DeliverOrderRequest(order);
		sendRequest(takeRequest);
		try {
			Response resp = responses.take();
			if(resp instanceof ErrorResponse)
				throw new ProtocolException("Error while taking orders!");
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

	private static class MapUpdaterThread extends AsyncTask<Object,Double,Void>{
		private OrderDetailsFragment frag;
		@Override
		protected Void doInBackground(Object... params) {
			frag= (OrderDetailsFragment) params[0];
			Order order=(Order) params[1];
			while(true){
				SystemClock.sleep(5000);
				try {
					requests.put(new GetLocationRequest(order));
					LocationResponse response = (LocationResponse) responses.take();
					//frag.updateCoord(response.getLatitude(), response.getLongitude());
					publishProgress(new Double[]{response.getLatitude(),response.getLongitude()});
				}catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}

		@Override
		protected void onProgressUpdate(Double... values) {
			super.onProgressUpdate(values);
			frag.updateMapPosition(values[0],values[1]);
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
