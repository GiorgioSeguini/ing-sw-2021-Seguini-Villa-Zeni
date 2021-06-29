package it.polimi.ingsw.constant.model;

import it.polimi.ingsw.constant.enumeration.ResourceType;

/**
 * Shelf class.
 * This class represents the single shelf of the warehouse depots.
 * This can have just a single type of resources and a limited size to store them.
 */
public class Shelf {

    private ResourceType restype;
    private final int MaxSize;
    private int used;
    transient boolean is_extra_shelf;

    /**
     * Instantiates a new Shelf.
     *
     * @param type of type ResourceType: the type.
     * @param maxSize of type int: the max size.
     */
    /*default constructor*/
    public Shelf(ResourceType type, int maxSize) {
        this(maxSize);
        this.restype=type;
    }

    /**
     * Instantiates a new Shelf without a preset type.
     *
     * @param maxSize of type int: the max size.
     */
    public Shelf(int maxSize){
        if (maxSize<0)
            throw new IllegalArgumentException();
        this.used =0;
        this.MaxSize=maxSize;
        this.is_extra_shelf=false;
    }


    /**
     * Gets resource type.
     *
     * @return of type ResourceType: the type in this shelf.
     */
    /*Getter*/
    public ResourceType getResType() {
        return this.restype;
    }

    /**
     * Gets max size.
     *
     * @return of type int: the max size of this shelf.
     */
    public int getMaxSize() {
        return this.MaxSize;
    }

    /**
     * Gets the number of used space on the shelf.
     *
     * @return of type int: the number of used space on the shelf.
     */
    public int getUsed() {
        return this.used;
    }

    /**
     * Get if this shelf is an extra shelf.
     *
     * @return of type boolean: True if is extra(from ability). Otherwise False.
     */
    public boolean getIsExtra(){return this.is_extra_shelf;}


    /**
     * Sets res type.
     *
     * @param type of type ResourceType: the type.
     */
    /*Setter*/
    public void setResType(ResourceType type) {
        this.restype=type;
    }

    /**
     * Set the number of used space on the shelf.
     *
     * @param used of type int: the number of used space on the shelf.
     */
    public void setUsed(int used){
        if (used<0)
            throw new IllegalArgumentException();
        this.used =used;
    }

    /**
     * Set is extra.
     */
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