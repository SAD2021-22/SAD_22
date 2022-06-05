# SAD_22
En aquest repositori estan totes les practiques d'aquest quadrimestre.

P1_noMVC-master: 
--
https://github.com/SAD2021-22/SAD/tree/main/P1_noMVC-master  
Primera implementació de la pràctica 1 sense MVC.  
Completament funcional.  

P1_no_MVC_profe1-master: 
--
https://github.com/SAD2021-22/SAD/tree/main/P1_no_MVC_profe1-master  
Segona implementació de la pràctica 1 un cop feta la primera sessió de comentaris a classe.  
S'ha modificat l'algoritme de read a EditableBufferedReader per imitar la proposta de correcció, s'ha de tenir en compte que les Seqüències d'escape són diferents a la VB que a linux natiu. Comentaris i explicacions al codi.  
Complemtament funcional.  

P1_MVC-master: 
--
https://github.com/SAD2021-22/SAD/tree/main/P1_MVC-master  
Primera implmentació de la pràctica 1 amb MVC.    
Completament funcional.  

P1_MVC_corregit-master: 
--
https://github.com/SAD2021-22/SAD/tree/main/P1_MVC_corregit-master  
Segona implmentació de la pràctica 1 amb MVC.  
Complemtament funcional.  
Millores realitzades a partir de la sessió de comentaris de codi a classe:
* Corregim les variables globals que haurien haver estat de mètode
* Millorem la intruitivitat del codi: com a la classe Dicc no fèiem el codi escalable, hem d'identificar les seqüències d'esapce direcatament al *read* amb les definicions comentades sobre el mètode i anar a les traduccions a Dicc.
* Millor utilitzem números negatius perquè a unicode els caràcters arriben fins a num molt alts, ens prevenim de ambiguitats
* Implementem el *readAll* -> és millor que read perquè permet llegir la seqüència d'escape complerta i reduim el parse
  * Al parse fet a la primera implementació, millor no obviar els caràcetrs per dur a terme control d'errors innerent
* Afegim el control d'error davant les fletxes adat, abaix, repag i avpag

P2_ACABADA/xat_consola_java_v3: 
--
https://github.com/SAD2021-22/SAD/tree/main/P2_ACABADA/xat_consola_java_v3  
Primera implementació de la pràctica 2 -> xat en consola de java.  
Complemtament funcional.  

P3_v6_FUNCIONAL100: 
--
https://github.com/SAD2021-22/SAD/tree/main/P3_v6_FUNCIONAL100  
Primera implementació de la pràctica 3 -> xat java amb swing i arquitectura NIO.  
Complemtament funcional.  

P3_v7_ampli_REV: 
--
https://github.com/SAD2021-22/SAD/tree/main/P3_v7_ampli_REV  
Primera PRE-implmentació de l'ampliació de la pràctica 3:  
In process:
* Color al xat
* Simetria dels botons de emojis
* Codificació apta per a unicode
* Necessitem scroll als missatges?

xat_Python_FINAL-main: 
--
https://github.com/SAD2021-22/SAD/tree/main/xat_Python_FINAL-main  
Consisteix en 2 versiones de un chat client-servidor, una en terminal, chat senzill i l'altre en la consola amb el tkinter.
Complemtament funcional.  

chat_PythonTkinter_update: 
--
https://github.com/SAD2021-22/SAD/tree/main/chat_PythonTkinter_update                                                 
Es la versión de chat python amb les millores proposades pel professor. En aquesta versió hem fet servir el lock per tal de controlar i gestionar els els diferents threads, es a dir per controlar i gestionar l’acces del usuaris al servidor i que no hi hagi solapament entre els diferents threads de diferents clients i per això hem gestionat amb els mètodes de lock.acquire() i lock.release().  

Mentre que el servidor gestioni els missatges de un cliente, el mètode acquire, bloqueja la entrada d’altres clients.  
Quan s’ha acabat de gestionar els missatges d’un client, cridem al mètode release per tal de librerar el lock aixi permetent l’entrada d’altre client.  

In process:   
Pero al compilar donava errors que vam poder estudiar-los, però per falta de temps i per haver d’estudiar per l’examen d’altres materies no vam poder gestionar-los.  

També vam intentar refer el chat Client-Servidor utilitzant el GTK en comptes de tkinter però en els nostres portàtils vam tenir problemes amb la instal·lació de gtk i la seva versió.   
