package it.polimi.ingsw.model;

import java.util.*;

/**
 * Classe che descrive il potere di produzione
 */
public class ProductionPower {

    /**
     * Default constructor
     */
    public ProductionPower() {
    }

    /**
     * Punti fede acquisiti dalla produzione
     */
    private int PointsFaithOut;

    /**
     *
     */
    private NumberOfResources outputRes;

    /**
     * 
     */
    private NumberOfResources inputRes;

    /**
     * @return
     */
    public int getFaithPointsOut() {
        return this.PointsFaithOut;
    }

    /**
     * @return
     */
    public NumberOfResources getInputRes() {
        return this.inputRes;
    }

    /**
     * @return
     */
    public NumberOfResources getOutputRes() {
        return this.outputRes;
    }

}