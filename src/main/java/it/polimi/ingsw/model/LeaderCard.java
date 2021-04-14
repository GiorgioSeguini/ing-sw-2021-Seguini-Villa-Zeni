package it.polimi.ingsw.model;

import it.polimi.ingsw.model.enumeration.LeaderStatus;
/*Last Edit: Fabio*/

public class LeaderCard extends Card {

    private LeaderStatus status;
    private final Requirements requirements;
    private final Ability ability;

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

        if(!requirements.equals(other.requirements))
            return false;

        return ability.equals(other.ability);
    }

}
