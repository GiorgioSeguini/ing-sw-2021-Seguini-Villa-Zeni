package it.polimi.ingsw;

import it.polimi.ingsw.client.Client;

import java.io.IOException;

public class ClientApp
{
    public static void main(String[] args){
        int ui = 1;
        if(args.length<3 || args[2].equals("gui"))
            ui = 2;
        Client client = new Client(args[0], Integer.parseInt(args[1]), ui);
        try{
            client.run();
        }catch (IOException e){
            System.err.println(e.getMessage());
        }
    }
}
