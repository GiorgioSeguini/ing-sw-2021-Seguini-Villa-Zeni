package it.polimi.ingsw.model;

import java.util.*;

/*
 * This class represents the single shelf of my warehouse depots. This can have just a single type of resources and a limited size to store them;
 */
public class Shelf {

    private ResourceType restype;
    private int MaxSize;
    private int Used;

    Shelf() {
        this.Used=0;
        this.MaxSize=0;
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

    public void setMaxSize(int size){
        this.MaxSize=size;
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