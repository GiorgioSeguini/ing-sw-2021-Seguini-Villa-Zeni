package it.polimi.ingsw.model;

/*Last Edit: William Zeni*/

import it.polimi.ingsw.model.enumeration.ResourceType;

/*
 * This class represents the single shelf of my warehouse depots. This can have just a single type of resources and a limited size to store them;
 */
public class Shelf {

    private ResourceType restype;
    private final int MaxSize;
    private int Used;
    boolean is_extra_shelf;

    /*default constructor*/
    Shelf(ResourceType type, int maxSize) {
        this.Used=0;
        this.MaxSize=maxSize;
        this.restype=type;
        this.is_extra_shelf=false;
    }

    Shelf(int maxSize){
        this.Used=0;
        this.MaxSize=maxSize;
        this.is_extra_shelf=false;
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

    public boolean getIsExtra(){return this.is_extra_shelf;}

    /*Setter*/
    public void setResType(ResourceType type) {
        this.restype=type;
    }

    public void setUsed(int Used){
        this.Used=Used;
    }

    public void setIsExtra(){
        this.is_extra_shelf=true;
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