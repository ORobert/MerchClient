package Models;

import java.io.Serializable;

/**
 * Created by Sergiu on 19-Jan-17.
 */
public enum OrderState implements Serializable{
	Confirmed,ToBeDelivered,Delivered;
}
