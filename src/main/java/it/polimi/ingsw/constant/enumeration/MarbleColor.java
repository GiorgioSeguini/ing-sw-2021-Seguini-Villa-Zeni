package it.polimi.ingsw.constant.enumeration;

/*Last Edit: Fabio*/
/*Color of Game's marble*/
public enum MarbleColor {

    YELLOW('Y'),
    BLUE('B'),
    GREY('G'),
    RED('R'),
    WHITE('W'),
    PURPLE('P');
    private final char color;

    MarbleColor(char color) {
        this.color = color;
    }

    public char getChar(){
        return color;
    }
}