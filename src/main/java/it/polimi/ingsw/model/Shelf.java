package it.polimi.ingsw.model;

/*Last Edit: William Zeni*/

/*
 * This class represents the single shelf of my warehouse depots. This can have just a single type of resources and a limited size to store them;
 */
public class Shelf {

    private ResourceType restype;
    private final int MaxSize;
    private int Used;

    Shelf(int maxSize) {
        this.Used=0;
        this.MaxSize=maxSize;
    }
    /*default constructor*/


    /*Getter*/
    public ResourceType getResType() {
        return this.restype;
    }

    public int getMaxSize() {
        return this.MaxSize;
    }

    public int getUsed() {
        return this.Used;
    }

    /*Setter*/
    public void setResType(ResourceType type) {
        this.restype=type;
    }



    /*Additional Methods*/
    public void add(int input) {
        if(this.Used+input<=this.MaxSize){
            this.Used=this.Used+input;
        }else{
            System.out.println("Operation not permitted");
        }
    }/** this method permits to add resources on the shelf only if the operation is permitted. There is no check about the res_type*/

    public void sub(int required) {
        if(this.Used-required>=0){
            this.Used=this.Used-required;
        }else{
            System.out.println("Operation not permitted");
        }
    }
    /** this method permits to sub resources on the shelf only if the operation is permitted. There is no check about the res_type*/
}