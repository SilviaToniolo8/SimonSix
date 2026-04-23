# Simon Six

Una versione del classico gioco di memoria **Simon** con **6 colori** invece di 4.  
Sviluppato in Kotlin con **Jetpack Compose**.

---

## Descrizione
Simon Six è un'applicazione con due schermate: Game e Chronology. Supporta sia l'orientamento **verticale** che **orizzontale** con un'interfaccia adattiva.

### Game Screen
Composta da una **griglia 2x3** di pulsanti colorati che una volta premuti aggiungono alla sequenza il simbolo secondo la tabella a seguire. Il testo viene visualizzato in base al colore premuto. 

| Simbolo | Colore  | Hex       |
|---------|---------|-----------|
| `R`     | Rosso   | `#E24B4A` |
| `Y`     | Giallo  | `#FFDE59` |
| `G`     | Verde   | `#7ED957` |
| `C`     | Ciano   | `#5CE1E6` |
| `B`     | Blu     | `#378ADD` |
| `M`     | Magenta | `#E978C4` |


Nella parte inferiore dello schermo sono presenti due pulsanti per cancellare la sequenza e per finire la partita.

### Chronology Screen 
Composta da una lista delle partite precedenti: ogni elemento della lista mostra quanti pulsanti sono stati premuti e la sequenza. Le partite sono mostrate in **ordine cronologico**, la più recente per prima e vengono colorate mantenendo l'ordine di colori rosso-giallo-verde-ciano-blu-magenta.

Se la sequenza non può essere scrtta interamente nello schermo viene troncata con **...**


Per tornare alla schermata principale del gioco bisogna usare il tasto *back* del sistema

---

## Dispositivi testati

### Smartphone

|    Marca    |      Modello      |   Versione   |
|-------------|-------------------|--------------|
| Samsung     | Galaxy A55 5G     | 16           |
| Redmi       | 12                | 15           |
| Redmi       | Note 11 Pro+ 5G   | 13           |
| Samsung     | Galaxy J6         | 10           |


### Tablet

|    Marca    |      Modello      |   Versione   |
|-------------|-------------------|--------------|
| Lenovo      | Idea Tab          | 16           |
| Samsung     | Tab S6 Lite       | 14           |
