/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package package1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

/**
 *
 * @author virtual
 */
public class EditableBufferedReader extends BufferedReader {

    //InputStreamReader in;
    public boolean error_end;
    public boolean esp;
    public Line line;
    public Console console;
    public Dicc dicc;

    public EditableBufferedReader(Reader r) {
        super(r);
        this.error_end = false;
        this.esp = false;
        this.line = new Line();
        this.console = new Console();
        this.dicc = new Dicc();
    }

    public void setRaw() {
        try {
            String[] com = {"/bin/sh", "-c", "stty -echo raw </dev/tty"};
            Runtime.getRuntime().exec(com).waitFor(); 
        } catch (Exception e) {
            System.out.println("error setting raw mode");
        }
    }

    public void unsetRaw() {
        try {
            String[] com = {"/bin/sh", "-c", "stty echo cooked </dev/tty"};
            Runtime.getRuntime().exec(com).waitFor(); 
        } catch (Exception e) {
            System.out.println("error setting cooked mode");
        }
    }

    public int read() throws IOException {
        int tecla;
        tecla = super.read();
        if (tecla == Dicc.ESC) {
            super.read();
            tecla = super.read();
            switch (tecla) {
                case Dicc.RIGHT:
                    return Dicc.t_RIGHT;
                case Dicc.LEFT:
                    return Dicc.t_LEFT;
                case Dicc.HOME:
                    return Dicc.t_HOME;
                case Dicc.END:
                    return Dicc.t_END;
                case Dicc.SUP:
                    super.read(); 
                    return Dicc.t_SUP;
                case Dicc.INSERT:
                    super.read(); 
                    return Dicc.t_INSERT;
            }

        }
        return tecla;//letra cualquiera                
    }

    @Override
    public String readLine() throws IOException {
        this.setRaw();
        int aux;
        this.line.addObserver(console);
        while ((aux = this.read()) != Dicc.t_ESC) {            
            switch (aux) {
                case Dicc.t_RIGHT:
                     line.moveto_right();
                    break;
                case Dicc.t_LEFT:
                    line.moveto_left();
                    System.out.print(Dicc.s_LEFT);
                    break;
                case Dicc.t_HOME:
                    line.moveto_home();                    
                    break;
                case Dicc.t_END:
                    line.moveto_end();

                case Dicc.t_INSERT:
                    line.insert();
                    break;
                case Dicc.t_DEL:
                    line.delete();                                                                                        
                    break;
                case Dicc.t_SUP:
                    line.suprimir();                                                             
                    break;
                default:
                    line.add((char) aux);
                    break;
            }
            
        }
        this.unsetRaw();
        return line.toString();

    }

}
