package it.polimi.ingsw;

import it.polimi.ingsw.client.Client;

import java.io.IOException;

public class ClientApp
{
    public static void main(String[] args){
        int ui = 1;
        int port = 12345;           //default values
        String ip = "127.0.0.1";
        if(args.length>1)
            ip = args[0];
        if(args.length>2)
            port = Integer.parseInt(args[1]);
        if(args.length<3 || args[2].equals("gui"))
            ui = 2;
        Client client = new Client(ip, port, ui);
        try{
            client.run();
        }catch (IOException e){
            System.err.println(e.getMessage());
        }
    }
}
