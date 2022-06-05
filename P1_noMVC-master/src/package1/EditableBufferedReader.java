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
    public Dicc dicc;
    public EditableBufferedReader(Reader r){
        super(r);
        this.error_end=false;
        this.esp=false;
        this.line=new Line();
        this.dicc = new Dicc();

    }
    
    public void setRaw(){
        try{
            String[] com ={"/bin/sh", "-c", "stty -echo raw </dev/tty"};
            Runtime.getRuntime().exec(com).waitFor(); //REV: añadir un waitfor?
        }catch(Exception e){
            System.out.println("error setting raw mode");
        }
    }
       
    
    public void unsetRaw(){
        try{
            String[] com ={"/bin/sh", "-c", "stty echo cooked </dev/tty"};
            Runtime.getRuntime().exec(com).waitFor(); //REV: uan necesario es añadir un waitfor
        }catch(Exception e){
            System.out.println("error setting cooked mode");
        }
    }
    
    public int read() throws IOException{
        int tecla;
        tecla=super.read();        
        if(tecla==Dicc.ESC){ 
            super.read();
            tecla=super.read();              
            switch(tecla){
                    case Dicc.RIGHT:                        
                        return Dicc.t_RIGHT;
                    case Dicc.LEFT:
                        return Dicc.t_LEFT;
                    case Dicc.HOME:
                        return Dicc.t_HOME;
                    case Dicc.END:                        
                        return Dicc.t_END; 
                    case Dicc.SUP:
                        super.read(); //hay algo más en medio
                        return Dicc.t_SUP;
                    case Dicc.INSERT:
                        super.read(); //hay algo más en medio
                        return Dicc.t_INSERT;
            }
                    
        }
        return tecla;//letra cualquiera                
    }
    
    @Override
    public String readLine() throws IOException{
        this.setRaw();

        int aux, tmp1, tmp2;
        
        while((aux=this.read())!=Dicc.t_ESC){
            switch(aux){
                case Dicc.t_RIGHT:   
                        if(line.moveto_right()){
                            System.out.print(Dicc.s_RIGHT);
                        }
                    break;
                case Dicc.t_LEFT: 
                        line.moveto_left();
                        System.out.print(Dicc.s_LEFT);
                    
                    break;
                case Dicc.t_HOME:
                    tmp1= line.moveto_home();
                    System.out.print(dicc.make(Dicc.t_HOME, tmp1));
                    break;
                case Dicc.t_END:
                    tmp2 = line.moveto_end();
                    if(tmp2>0){
                        System.out.print(dicc.make(Dicc.t_END, tmp2));                    
                    }                    
                case Dicc.t_INSERT:
                    line.insert();                    
                    break;
                case Dicc.t_DEL:  
                        line.delete();
                        System.out.print(Dicc.s_DEL);                                                                     
                    break;
                case Dicc.t_SUP: 
                        line.suprimir();                        
                        System.out.print(Dicc.s_SUP);                                           
                    break;                
                default:                   
                    if(line.add((char) aux)){
                        System.out.print((char)aux);
                    }else{
                        System.out.print("\033[@");
                        System.out.print((char)aux);
                    }
                    break;
            }
        
        }
        this.unsetRaw();
        return line.toString();
        
        
    }
    
}

