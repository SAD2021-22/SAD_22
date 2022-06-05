/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package package1;

/**
 *
 * @author virtual
 */
public class Dicc {
////"cuando la aplicación recibe el carácter ASCII, no está claro si ese carácter es resultado
    //de que el usuario prsione la tecla ESC o asi es el carácter inicial de una secuencia de escape."
    //
    //Para resolverlo nosotros vamos a leer el ASCII y transformarlo en otro valor, nos ahorramos la incongruencia
    //con esta traducción

    //The action of a CSI( control sequence introducer) is determinated by its final character
    //PC-Style functino Keys (web enunciat)
    public static final int RIGHT = 67; //ESC[[C -> 67=ascii{c}
    public static final int LEFT = 68;
    public static final int HOME = 72;
    public static final int END = 70; //"Page down"
    public static final int INTRO = 13; //"retorno de carro"
    //public static final int DEL = 127; //backspace (borrar la tecla de la izq) --> //DEL no es una seq d'escape, es ctrl+H
    
    public static final int SUP = 51; //"delete"
    public static final int ESC = 27; //\033 stands for ESC (ANSI value 27)
    public static final int INSERT = 50; //CSI 2 virguilla

    //valors traduits a aprtir del 3000  
    public static final int t_RIGHT = 3001;
    public static final int t_LEFT = 3002;
    public static final int t_HOME = 3003;
    public static final int t_END = 3004;
    public static final int t_INTRO = 3005;
    public static final int t_ESC = 13;
    public static final int t_SUP = 3007;
    public static final int t_DEL = 127;
    public static final int t_INSERT = 3009;

    //codes
        
    public static final String s_RIGHT = "\033[C";
    public static final String s_LEFT = "\033[D";
    public static final String s_HOME = "\033";
    public static final String s_END = "\033"; 
    public static final String s_INSERT = "\033[4h";
    public static final String s_SUP = "\033[P";
    public static final String s_DEL = "\033[D\033[P";    
    
    
    
    
    public String make(int name, int pos){
        String tmp;
        if(name==t_HOME){
            tmp = "\033[" + pos + "D";
            return tmp;
        }
        else if(name== t_END){
            tmp= "\033[" + pos + "C";
            return tmp;
        }
        else
            return "";
    }
    
    public Dicc(){//constructor;
        
    }
    
}
/*
RIGHT -> mueve columnas a la derecha
LEFT -> mueve columnas a la izquierda
HOME -> va al principio del texto (columna 1 fila 1)

*/
