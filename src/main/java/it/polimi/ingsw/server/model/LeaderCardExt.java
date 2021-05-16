package it.polimi.ingsw.server.model;

import it.polimi.ingsw.constant.enumeration.LeaderStatus;
import it.polimi.ingsw.constant.model.LeaderCard;
import it.polimi.ingsw.constant.model.Requirements;
/*Last Edit: Fabio*/

public class LeaderCardExt extends LeaderCard {

    private final Ability ability;
    private final RequirementsExt requirements;

    public LeaderCardExt(RequirementsExt requirements,  Ability ability, int victoryPoints) {
        super(requirements, victoryPoints);
        this.ability = ability;
        this.requirements = requirements;
    }

    public Ability getAbility() {
        return ability;
    }


    public RequirementsExt getRequirements() {
        //return (RequirementsExt) super.getRequirements();
        return this.requirements;
    }

    /*Additional Methods*/
    /** This set the LeaderCard status on "played"
     * @return true if the card is really activated
     */
    public boolean setPlayed(PlayerExt owner) {
        if(this.getStatus()==LeaderStatus.onHand && ((RequirementsExt)this.getRequirements()).match(owner)){
                ability.RunAbility(owner);
                super.setStatus(LeaderStatus.Played);
                ((PersonalBoardExt)owner.getPersonalBoard()).change();
                return true;
        }
        return false;
    }



    /** This set the LeaderCard status on "Dead" and add one FaithPoint
     * @return true if the card is really discarded, false otherwise
     * */
    public boolean setDiscard(PlayerExt owner){
        if(getStatus().equals(LeaderStatus.onHand)){
            setStatus(LeaderStatus.Dead);
            owner.getFaithTrack().addPoint();
            owner.getPersonalBoard().change();
            return true;
        }
        return false;
    }


}
