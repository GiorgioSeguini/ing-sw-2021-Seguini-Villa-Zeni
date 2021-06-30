# ing-sw-2021-Seguini-Villa-Zeni

# Versione Italiana
 * [Il gioco](#gameITA)
 * [Installazione](#installationITA)
 * [Server](#serverITA)
 * [Client](#clientITA)
 * [Come giocare](#howtoplayITA)
 * [Tools](#toolsITA)
 * [Risultati dei test](#testITA)

## Il gioco <a name="gameITA"></a>
Questo repo contiene una versione **Java** del gioco [*Maestri del rinascimento*](http://www.craniocreations.it/prodotto/masters-of-renaissance/). Il progetto è stato sviluppato durante l'anno 2020/21 per il corso di **Ingegneria del Software** al Politecnico di Milano, come parte finale di valutazione per il conseguimento della laurea triennale in Ingegneria Informatica. Gli sviluppatori sono stati:
  * [William Zeni](https://github.com/williamzeni)
  * [Giorgio Seguini](https://github.com/GiorgioSeguini)
  * [Fabio Villa](https://github.com/fabiovillaa)
  
## Installazione <a name="installationITA"></a>
Per avviare il gioco è necessario scaricare [JavaSE 15](https://www.oracle.com/it/java/technologies/javase-downloads.html) (o versioni superiori).
Il giocatore che creerà la partita, per poter hostare il server, può decidere di installare [ngrok] (https://ngrok.com/), sebbene siano disponibili altre modalità che la guida non coprirà.

Scaricare in locale il repository come segue
```bash
git clone https://github.com/GiorgioSeguini/ing-sw-2021-Seguini-Villa-Zeni.git
```
su linux. Per windows potrebbe essere necessario scaricare il repo in formato zip ed estrarlo. 

I file eseguibili per il gioco sono contenuti nella cartella `/shade`.

## Come far partire il server <a name="serverITA"></a>
Entrare nella cartella con l'eseguibile di **ngrok** e aprire una connessione TCP alla porta `12345`. Sia dalla bash Linux, sia dalla PowerShell di Windows, è possibile farlo attraverso il comando (la bash deve essere aperta nella stessa dir di **ngrok**)

```bash
ngrok tcp 12345
```
Il numero della porta deve essere necessariamente quella indicata. Se si volesse modificare la porta, sarà necessario modificare l'attributo nella classe `Server` e ricompilare i file *.jar* .

Successivamente aprire la PowerShelf Windows o la bash linux all'interno della cartella `\shade` e digitare
```bash
java -jar SERVERG_C39.jar
```

Ora il server è attivo e può essere lasciato acceso fino a quando non si vuole smettere di giocare, anche se si vogliono disputare più partite. 

## Come far partire il client <a name="clientITA"></a>
Per poter collegare il client al server sarà necessario aprire la PowerShelf Windows o la bash linux all'interno della cartella `\shade` e digitare
```bash
java -jar CLIENT_C39.jar ADDRESS PORT
```
dove `ADDRESS` e `PORT` sono rispettivamente l'indirizzo e la porta del server. Nel caso di **ngrok** il messaggio potrebbe essere
```bash
Session Status: online
Account: YOURACCOUNT (Plan: Free)
Version: 2.3.40
Region: United States (us)
Web Interface: http://127.0.0.1:4040
Forwarding:  tcp://4.tcp.ngrok.io:11387 -> localhost:12345                                              
```
Di conseguenza, **4.tcp.ngrok.io** sarà l'indirizzo mentre **11387** sarà la porta.

Di default il client aprirà la parte grafica, tuttavia è possibile giocare via linea di comando attraverso l'aggiunta del parametro `cli`
```bash
java -jar CLIENT_C39.jar ADDRESS PORT cli
```

## Come giocare<a name="howtoplayITA"></a>
Le modalità di inizio partita possono essere tre: la prima consiste in una partita privata nella quale un utente può creare una stanza e tutti gli altri giocatori possono connettersi (si noti che il creatore della stanza deve comunicare il nome della stanza agli altri giocatori per poter accerdere) ; la seconda consiste nel poter giocare online con qualsiasi utente connesso al server (questa modalità avrebbe senso se le coordinate del server fossero pubbliche), basterà difatto solo identificarsi con un nome e inserirsi in una partia da un massimo di 4 giocatori; la terza non necessità di un server, ma si può giocare solo da soli. 

Le regole del gioco italiane si trovano all'interno del repository [qui](https://github.com/GiorgioSeguini/ing-sw-2021-Seguini-Villa-Zeni/blob/master/Lorenzo_Cardgame_Rules_ITA_small-3.pdf)

Nota bene: è sempre possibile riconettersi ad una partita in corso se si memorizzano il proprio `nickname` e il nome della `room`.

## Tools <a name="toolsITA"></a>
Per la realizzazione del progetto sono stati utilizzati :
 * [StarUML](http://staruml.io) - UML Diagram
 * [Maven](https://maven.apache.org/) - Dependency Management
 * [IntelliJ](https://www.jetbrains.com/idea/) - IDE
 * [JavaFX](https://openjfx.io) - Graphical Framework


## Risultati dei test <a name="testITA"></a>
![Test result](https://github.com/GiorgioSeguini/ing-sw-2021-Seguini-Villa-Zeni/blob/master/img.png)
