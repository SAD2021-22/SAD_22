# xat_Python_FINAL
En aquesta versió hem duut a terme una proposta de millora, que es fer servir els locks, per si en cas de tenir molts connexions, no hi hagi colapse entre els threads, per aixo hem fet servir el metode lock dels threads.
Amb lock.acquire(), es el metode que hem utilitzat per fer el bloqueig, i el lock.release() per desbloquejar. 
Aquestes condicions els hem posat als metodes de enviar y rebre missatge. Per tal de que quan un usuari rep un missatge, rep nomes d'un usuari en concret, i el rebre missatge sera bloquejant fins que l'altre usuari no hagi acabat d'enviar.

