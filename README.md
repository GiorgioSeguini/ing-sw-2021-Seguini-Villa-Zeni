# ing-sw-2021-Seguini-Villa-Zeni

# Versione Italiana

## Installazione
Per avviare il gioco è necessario scaricare [JavaSE 15](https://www.oracle.com/it/java/technologies/javase-downloads.html) (o versioni superiori).
Il giocatore che creerà la partita, per poter hostare il server, può decidere di installare [ngrok] (https://ngrok.com/), sebbene siano disponibili altre modalità che la guida non coprirà.

Scaricare in locale il repository come segue
```bash
git clone https://github.com/GiorgioSeguini/ing-sw-2021-Seguini-Villa-Zeni.git
```
su linux. Per windows potrebbe essere necessario scaricare il repo in formato zip ed estrarlo. 

I file eseguibili per il gioco sono contenuti nella cartella `/shade`.

## Come far partire il server
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

## Come far partire il client
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
Di conseguenza, **4.tcp.ngrok.io** sarà l'ADDRESS mentre **11387** sarà la porta.

Di default il client aprirà la parte grafica, tuttavia è possibile giocare via linea di comando attraverso l'aggiunta del parametro `cli`
```bash
java -jar CLIENT_C39.jar ADDRESS PORT cli
```

##Come giocare
Le modalità di inizio partita possono essere tre: la prima consiste in una partita privata nella quale un utente può creare una stanza e tutti gli altri giocatori possono connettersi; la seconda consiste nel poter giocare online con qualsiasi utente connesso al server (questa modalità avrebbe senso se le coordinate del server fossero pubbliche), basterà difatto solo identificarsi con un nome e inserirsi in una partia da un massimo di 4 giocatori; la terza non necessità di un server, ma si può giocare solo da soli. 

Le regole del gioco 


## Risultati dei test
![Test result](https://github.com/GiorgioSeguini/ing-sw-2021-Seguini-Villa-Zeni/blob/master/img.png)
