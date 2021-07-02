# ing-sw-2021-Seguini-Villa-Zeni
![latest commit](https://img.shields.io/github/last-commit/GiorgioSeguini/ing-sw-2021-Seguini-Villa-Zeni?color=red)
![latest release](https://img.shields.io/github/v/release/GiorgioSeguini/ing-sw-2021-Seguini-Villa-Zeni?color=green)
 * [Italian Version](#ITA)
 * [English Version](#ENG)
 
# Versione Italiana <a name="ITA"></a>
 * [Il gioco](#gameITA)
 * [Funzionalità implementate](#functionalitiesITA)
 * [Installazione](#installationITA)
 * [Server](#serverITA)
 * [Client](#clientITA)
 * [Come giocare](#howtoplayITA)
 * [Tools](#toolsITA)
 * [Test e Documentazione](#testITA)
 
 <img src="https://github.com/GiorgioSeguini/ing-sw-2021-Seguini-Villa-Zeni/blob/master/Masters-of-Renaissance_box3D_front_ombra.png" width=300px height=300px align="right" />

## Il gioco <a name="gameITA"></a>
Questo repo contiene una versione **Java** del gioco [*Maestri del rinascimento*](http://www.craniocreations.it/prodotto/masters-of-renaissance/). Il progetto è stato sviluppato durante l'anno 2020/21 per il corso di **Ingegneria del Software** al Politecnico di Milano, come parte finale di valutazione per il conseguimento della laurea triennale in Ingegneria Informatica. Gli sviluppatori sono stati:
  * [William Zeni](https://github.com/williamzeni)
  * [Giorgio Seguini](https://github.com/GiorgioSeguini)
  * [Fabio Villa](https://github.com/fabiovillaa)
 
## Funzionalità Implementate<a name="functionalitiesITA"></a>
| Funzionalità | Stato |
|:-----------------------|:------------------------------------:|
| Regole Semplificate | [![GREEN](http://placehold.it/15/44bb44/44bb44)]() |
| Regole Complete | [![GREEN](http://placehold.it/15/44bb44/44bb44)]() |
| Socket |[![GREEN](http://placehold.it/15/44bb44/44bb44)]() |
| CLI | [![GREEN](http://placehold.it/15/44bb44/44bb44)]() |
| GUI |[![GREEN](http://placehold.it/15/44bb44/44bb44)]() |
| Partite Multiple | [![GREEN](http://placehold.it/15/44bb44/44bb44)]() |
| Resilienza | [![GREEN](http://placehold.it/15/44bb44/44bb44)]() |
| Partita Locale | [![GREEN](http://placehold.it/15/44bb44/44bb44)]() |
| Persistenza | [![RED](http://placehold.it/15/f03c15/f03c15)]() |
| Editor dei parametri | [![RED](http://placehold.it/15/f03c15/f03c15)]() |

#### Legenda
[![RED](http://placehold.it/15/f03c15/f03c15)]() Non Implementato &nbsp;&nbsp;&nbsp;&nbsp;[![YELLOW](http://placehold.it/15/ffdd00/ffdd00)]() Implementazione in corso&nbsp;&nbsp;&nbsp;&nbsp;[![GREEN](http://placehold.it/15/44bb44/44bb44)]() Implementato
  
## Installazione <a name="installationITA"></a>
Per avviare il gioco è necessario scaricare [JavaSE 15](https://www.oracle.com/it/java/technologies/javase-downloads.html) (o versioni superiori).
Il giocatore che creerà la partita, per poter hostare il server, può decidere di installare [ngrok](https://ngrok.com/), sebbene siano disponibili altre modalità che la guida non coprirà.

Scaricare in locale il repository come segue
```bash
git clone https://github.com/GiorgioSeguini/ing-sw-2021-Seguini-Villa-Zeni.git
```
su linux. Per windows potrebbe essere necessario scaricare il repo in formato zip ed estrarlo. 

I file eseguibili per il gioco sono contenuti nella cartella `/shade`.

## Come far partire il server <a name="serverITA"></a>
Entrare nella cartella con l'eseguibile di **ngrok** e aprire una connessione TCP alla porta `12345`. Sia dalla bash Linux, sia dalla PowerShell di Windows, è possibile farlo attraverso il comando (la bash deve essere aperta nella stessa dir di **ngrok**)

```bash
./ngrok tcp 12345
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


## Test e Documentazione <a name="testITA"></a>
La documentazione è disponibile [qui](https://github.com/GiorgioSeguini/ing-sw-2021-Seguini-Villa-Zeni/tree/master/Documentation).

I test hanno raggiunta la seguente copertura.


![Test result](https://github.com/GiorgioSeguini/ing-sw-2021-Seguini-Villa-Zeni/blob/master/Documentation/total.png)
![Test result](https://github.com/GiorgioSeguini/ing-sw-2021-Seguini-Villa-Zeni/blob/master/Documentation/server.png)
![Test result](https://github.com/GiorgioSeguini/ing-sw-2021-Seguini-Villa-Zeni/blob/master/Documentation/costant.png)
![Test result](https://github.com/GiorgioSeguini/ing-sw-2021-Seguini-Villa-Zeni/blob/master/Documentation/client.png)

# English Version<a name="ENG"></a>
 * [The Game](#gameENG)
 * [Implemented Functionalities](#functionalitiesENG)
 * [Installation](#installationENG)
 * [Server](#serverENG)
 * [Client](#clientENG)
 * [How to play](#howtoplayENG)
 * [Tools](#toolsENG)
 * [Test and Docs](#testENG)
 
 <img src="https://github.com/GiorgioSeguini/ing-sw-2021-Seguini-Villa-Zeni/blob/master/Masters-of-Renaissance_box3D_front_ombra.png" width=300px height=300px align="right" />


## The Game <a name="gameENG"></a>
This repo contains a **Java** version of the game [*Masters of renaissance*](http://www.craniocreations.it/prodotto/masters-of-renaissance/). The project has been developed for the course **Software Engineering** at Politecnico di Milano as a final part of examination for the Bachelor in Computer Engineering. The contributors were:
  * [William Zeni](https://github.com/williamzeni)
  * [Giorgio Seguini](https://github.com/GiorgioSeguini)
  * [Fabio Villa](https://github.com/fabiovillaa)

## Implemented Funcionalities <a name="functionalitiesENG"></a>
| Functionalities | Status |
|:-----------------------|:------------------------------------:|
| Basic Rules | [![GREEN](http://placehold.it/15/44bb44/44bb44)]() |
| Complete Rules | [![GREEN](http://placehold.it/15/44bb44/44bb44)]() |
| Socket |[![GREEN](http://placehold.it/15/44bb44/44bb44)]() |
| CLI | [![GREEN](http://placehold.it/15/44bb44/44bb44)]() |
| GUI |[![GREEN](http://placehold.it/15/44bb44/44bb44)]() |
| Multiple Games | [![GREEN](http://placehold.it/15/44bb44/44bb44)]() |
| Resilience | [![GREEN](http://placehold.it/15/44bb44/44bb44)]() |
| Local Game | [![GREEN](http://placehold.it/15/44bb44/44bb44)]() |
| Persistence | [![RED](http://placehold.it/15/f03c15/f03c15)]() |
| Parameter Editor | [![RED](http://placehold.it/15/f03c15/f03c15)]() |

#### Legend
[![RED](http://placehold.it/15/f03c15/f03c15)]() Not Implemented &nbsp;&nbsp;&nbsp;&nbsp;[![YELLOW](http://placehold.it/15/ffdd00/ffdd00)]() Implementing&nbsp;&nbsp;&nbsp;&nbsp;[![GREEN](http://placehold.it/15/44bb44/44bb44)]() Implemented
  
## Installation <a name="installationENG"></a>
To start the game you need to download [JavaSE 15](https://www.oracle.com/it/java/technologies/javase-downloads.html) (or updated versions).
The player that will start the match can install [ngrok](https://ngrok.com/) to host the game. Other options are available but will not be covered in this guide.

Download the repo as it follow
```bash
git clone https://github.com/GiorgioSeguini/ing-sw-2021-Seguini-Villa-Zeni.git
```
on linux. For Windows should be necessary download the repo in zip format and then unzip it. 
The executable files are stored in the `/shade` directory.

## How to start the server <a name="serverENG"></a>
Open the **ngrok** directory with the terminal (linux or windows) and open a TCP connection at port `12345`, as it follow 
```bash
./ngrok tcp 12345
```
The port number has to be `12345`. If it should be necessary to modify the port, open the project class `Server` and edit the attribute, then rebuild the *.jar* file. 

Open the terminal at dir `\shade` and copy
```bash
java -jar SERVERG_C39.jar
```
Now the server has started and can be leaved powered on even a match ends. 

## How to start the client <a name="clientENG"></a>
To connect the client will be necessary open the terminal at the `\shade` dir and copy
```bash
java -jar CLIENT_C39.jar ADDRESS PORT
```
where `ADDRESS` and `PORT` are the server address and port. In our case, the **ngrok** app will print a text as it follow
```bash
Session Status: online
Account: YOURACCOUNT (Plan: Free)
Version: 2.3.40
Region: United States (us)
Web Interface: http://127.0.0.1:4040
Forwarding:  tcp://4.tcp.ngrok.io:11387 -> localhost:12345                                              
```
 **4.tcp.ngrok.io** will be the address and **11387** the port.

On default the client will open the GUI, but if you add the parameter `cli` at the end of the command it will be possible start the game from command-line. 
```bash
java -jar CLIENT_C39.jar ADDRESS PORT cli
```

## How to play<a name="howtoplayENG"></a>
A game can be started in three different ways: I can create a room where my friends can join me (a room can be joined through its name); I can join an online public multiplayer filling the infos with my personal nickname and with how many players I want play with; I can play alone offline.

You can find the english rules [here](https://github.com/GiorgioSeguini/ing-sw-2021-Seguini-Villa-Zeni/blob/master/Documentation/Masters-of-Renaissance_small.pdf)

Ps. It is alway possible rejoin an alredy started match if I have my `nickname` and the `room` name.

## Tools <a name="toolsENG"></a>
In this project were used the following tools:
 * [StarUML](http://staruml.io) - UML Diagram
 * [Maven](https://maven.apache.org/) - Dependency Management
 * [IntelliJ](https://www.jetbrains.com/idea/) - IDE
 * [JavaFX](https://openjfx.io) - Graphical Framework


## Test and Documentation <a name="testENG"></a>
You can find the documentation [here](https://github.com/GiorgioSeguini/ing-sw-2021-Seguini-Villa-Zeni/tree/master/Documentation).

The tests have reached the following coverage.


![Test result](https://github.com/GiorgioSeguini/ing-sw-2021-Seguini-Villa-Zeni/blob/master/Documentation/total.png)
![Test result](https://github.com/GiorgioSeguini/ing-sw-2021-Seguini-Villa-Zeni/blob/master/Documentation/server.png)
![Test result](https://github.com/GiorgioSeguini/ing-sw-2021-Seguini-Villa-Zeni/blob/master/Documentation/costant.png)
![Test result](https://github.com/GiorgioSeguini/ing-sw-2021-Seguini-Villa-Zeni/blob/master/Documentation/client.png)
