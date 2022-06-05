/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package package1;

import java.util.Observable;
import java.util.Observer;

/**
 *
 * @author virtual
 */

public class Console implements Observer {

    //public Line line;
    public Dicc dicc;

    public Console() {        
        this.dicc = new Dicc();
    }
    
    
    static class Event {
        public int tecla, despl;
        public char letra;
        public boolean acc, insert;
        Event(int tecla)      {
            this.tecla=tecla;
        }
        Event(int tecla, int despl) {
            this.tecla=tecla;
            this.despl = despl;
        }
        Event(int tecla, boolean disc, char letra){
            this.tecla=tecla;
            this.insert=disc;
            this.letra=letra;
        }   
        Event(int tecla, boolean acc){
            this.tecla=tecla;
            this.acc=acc;
        }

    }

    @Override
    public void update(Observable o, Object arg) {
        Event event = (Event) arg;
        switch (event.tecla) {
            case Dicc.t_RIGHT:
                if (event.acc) {
                    System.out.print(Dicc.s_RIGHT);
                }
                break;
            case Dicc.t_LEFT:
                System.out.print(Dicc.s_LEFT);
                break;
            case Dicc.t_HOME:
                System.out.print(dicc.make(Dicc.t_HOME, event.despl));
                break;
            case Dicc.t_END:
                if(event.despl>0){
                    System.out.print(dicc.make(Dicc.t_END,event.despl));
                }
                break;
            case Dicc.t_DEL:
                System.out.print(Dicc.s_DEL);
                break;
            case Dicc.t_SUP:
                System.out.print(Dicc.s_SUP);
                break;
            case Dicc.t_ADD:                
                if (event.insert) {                    
                    System.out.print((char) event.letra);
                } else {
                    System.out.print("\033[@");
                    System.out.print((char) event.letra);
                }
                break;
        }
    }
    
}
