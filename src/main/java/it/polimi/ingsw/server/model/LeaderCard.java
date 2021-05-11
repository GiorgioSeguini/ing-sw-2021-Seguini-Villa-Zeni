package it.polimi.ingsw.server.model;

import it.polimi.ingsw.constant.enumeration.LeaderStatus;

import java.util.concurrent.atomic.AtomicInteger;
/*Last Edit: Fabio*/

public class LeaderCard extends Card {

    private transient LeaderStatus status; // TODO: 5/6/21 defualt value on hand json
    private final Requirements requirements;
    private final Ability ability;
    //static AtomicInteger nextId= new AtomicInteger();

    /*Default constructor*/
    public LeaderCard(Requirements requirements, Ability ability, int victoryPoints) {
        super(victoryPoints);
        this.status=LeaderStatus.onHand;
        this.requirements=requirements;
        this.ability=ability;
    }


    /*getter*/
    public LeaderStatus getStatus() {
        return status;
    }

    public Requirements getRequirements() {
        return requirements;
    }

    public Ability getAbility() {
        return ability;
    }

    /*setter*/
    public void setStatus(LeaderStatus status) {
        this.status = status;
    }

    /*Additional Methods*/
    /** This set the LeaderCard status on "played"
     * @return true if the card is really activated
     */
    public boolean setPlayed(Player owner) {
        if(this.getStatus()==LeaderStatus.onHand && this.getRequirements().match(owner)){
                getAbility().RunAbility(owner);
                setStatus(LeaderStatus.Played);
                owner.getPersonalBoard().change();
                return true;
        }
        return false;
    }



    /** This set the LeaderCard status on "Dead" and add one FaithPoint
     * @return true if the card is really discarded, false otherwise
     * */
    public boolean setDiscard(Player owner){
        if(getStatus().equals(LeaderStatus.onHand)){
            setStatus(LeaderStatus.Dead);
            owner.getFaithTrack().addPoint();
            owner.getPersonalBoard().change();
            return true;
        }
        return false;
    }

    @Override
    public boolean equals(Object o){
        if(o==this)
            return true;

        if(!(o instanceof LeaderCard))
            return false;

        LeaderCard other = (LeaderCard) o;

        if(this.getVictoryPoints() != other.getVictoryPoints())
            return false;

        if(!requirements.equals(other.requirements))
            return false;

        return ability.equals(other.ability);
    }

    @Override
    public String toString(){
        String LC ="";
        LC += "Status: "+getStatus()+"\n";
        LC += "Requirements: \n";
        LC += "\t"+getRequirements();
        LC += "Victory Points: "+getVictoryPoints()+"\n";
        LC += "Ability: \n";
        LC += "\t "+getAbility().getName()+"\tResources: "+getAbility().getTypeOfRes()+"\n";
        return LC;
    }
}
