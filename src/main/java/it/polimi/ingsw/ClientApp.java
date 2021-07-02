package it.polimi.ingsw;

import it.polimi.ingsw.client.Client;

import java.io.IOException;

public class ClientApp
{
    public static void main(String[] args){
        int ui = 1;
        int port;
        String ip;
        if(args.length>3 || args.length <2){
            System.out.println("Invalid argument , please specify ip address and port number");
            return;
        }
        if(args.length==3 && !args[2].equals("gui") && !args[2].equals("cli")){
            System.out.println("Invalid argument , please \"cli\" or \"gui\" for user interface. Default is gui");
            return;
        }
        ip = args[0];
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
