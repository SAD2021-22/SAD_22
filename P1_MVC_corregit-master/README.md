# P1_MVC_corregit
Amb les correcions de classe fetes



CORRECIONS FETES A CLASSE: (respecte a la versió P1_MVC de SAD2021-22)

*Hemos definido algunas variables globales que deberían de ser de método

*Con la clase Dicc no hacemos el código escalable, hay que identificar las secuencias de escape directamente en read con las definiciones arriba e ir a las traducciones a Dicc, pporque Dicc no es muy intuitivo

*En unicode los carñacteres parecen llegar hasta ocupar muchisimos numeros: mejor usar números negativos que seguro no se solpalan

*Usar un readAll en lugar de read<char> para leer la secuencia de escape completa y no tener que parsear tanto

*No obviar los [ ni virguillas por si acaso, mejor identificarlas (en el caso de no usar readAll, con readAll me ahorro todo este parsing)

  *Controloar las secuencias de escape random como las flechas arriba/abajo y repag y avpag
