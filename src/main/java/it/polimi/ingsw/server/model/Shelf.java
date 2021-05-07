package it.polimi.ingsw.server.model;

/*Last Edit: William Zeni*/

import it.polimi.ingsw.constant.enumeration.ResourceType;

/*
 * This class represents the single shelf of my warehouse depots. This can have just a single type of resources and a limited size to store them;
 */
public class Shelf {

    private ResourceType restype;
    private final int MaxSize;
    private int used;
    transient boolean is_extra_shelf;

    /*default constructor*/
    public Shelf(ResourceType type, int maxSize) {
        this(maxSize);
        this.restype=type;
    }

    public Shelf(int maxSize){
        if (maxSize<0)
            throw new IllegalArgumentException();
        this.used =0;
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
        return this.used;
    }

    public boolean getIsExtra(){return this.is_extra_shelf;}

    /*Setter*/
    public void setResType(ResourceType type) {
        this.restype=type;
    }

    public void setUsed(int used){
        if (used<0)
            throw new IllegalArgumentException();
        this.used =used;
    }

    public void setIsExtra(){
        this.is_extra_shelf=true;
    }

    @Override
    public String toString(){
        String res ="Shelf-->\t";
        res+="Size: "+ getMaxSize()+"\t";
        res+="Objects: "+getUsed()+" , "+getResType()+"\t";
        res+="Is extra? : "+getIsExtra()+"\n";

        return res;
    }


}