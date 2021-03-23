package it.polimi.ingsw.model;

/*Last Edit: William Zeni*/

/*
 * This class represents the single shelf of my warehouse depots. This can have just a single type of resources and a limited size to store them;
 */
public class Shelf {

    private ResourceType restype;
    private final int MaxSize;
    private int Used;

    /*default constructor*/
    Shelf(ResourceType type, int maxSize) {
        this.Used=0;
        this.MaxSize=maxSize;
        this.restype=type;
    }

    Shelf(int maxSize){
        this.Used=0;
        this.MaxSize=maxSize;
    }


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
    public void SetUsed(int Used){
        this.Used=Used;
    }



    /*Additional Methods*/
    public void add_resources(int input) {
        if(this.Used+input<=this.MaxSize){
            this.Used=this.Used+input;
        }else{
            System.out.println("Operation not permitted");
        }
    }/** this method permits to add resources on the shelf only if the operation is permitted. There is no check about the res_type*/

    public void sub_resources(int required) {
        if(this.Used-required>=0){
            this.Used=this.Used-required;
        }else{
            System.out.println("Operation not permitted");
        }
    }
    /** this method permits to sub resources on the shelf only if the operation is permitted. There is no check about the res_type*/
}