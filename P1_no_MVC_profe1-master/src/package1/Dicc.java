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


    //valors traduits a aprtir del 3000  
    public static final int t_RIGHT = 3001;
    public static final int t_LEFT = 3002;
    public static final int t_HOME = 3003;
    public static final int t_INSERT = 3004;
    public static final int t_SUP = 3005;
    public static final int t_END = 3006;
    public static final int t_INTRO = 3007;
    public static final int t_ESC = 13;
    public static final int t_ERROR = 3008;
    
    public static final int t_DEL = 127;
    

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
