package Models;


import java.io.Serializable;

/**
 * Created by orobe on 27/12/2016.
 */
public abstract class Products implements Serializable {

    private int id;
    private String name;

    public Products(){

    }

    public Products(final String name){
        this.name=name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String toString(){
        return "Name "+name;
    }
}
