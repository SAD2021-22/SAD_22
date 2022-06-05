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
    /* Scape sequences:
    RIGHT:  Esc [ C
    LEFT:   Esc [ D
    HOME:   Esc [ O D || Esc [ 1 ~
    END:    Esc [ O C || Esc [ 4 ~
    INSERT: Esc [ 2 ~ 
    SUP:    Esc [ 3 ~         
    */
    public int read() throws IOException{
        int tecla, tecla1;
        tecla=super.read();        
        if(tecla!='\033'){             
            return tecla;
        }
        tecla=super.read();
        //switch(tecla){
            /*case 'O':                 PROPOSTA DE COMANA, NO FUNCIONA A VB
                tecla=super.read();
                switch(tecla){
                    case 'H': return Dicc.t_HOME;                                                            
                    case 'F': return Dicc.t_END;
                } */               
            //case '[':
                tecla=super.read();
                switch(tecla){
                    case 'C': return Dicc.t_RIGHT;
                    case 'D': return Dicc.t_LEFT;
                    case 'H': return Dicc.t_HOME;
                    case 'F': return Dicc.t_END;
                    case '2': tecla= super.read(); return Dicc.t_INSERT;
                    case '3': tecla= super.read(); return Dicc.t_SUP; 
                    //control de les tecles: repag, avpag, upArrow, downArrow -> davant no tenir indicacions, anulem les tecles.
                    case 'A': return Dicc.t_ERROR;
                    case 'B': return Dicc.t_ERROR;
                    case '5': tecla= super.read(); return Dicc.t_ERROR;
                    case '6': tecla= super.read();return Dicc.t_ERROR;                        
                            
                    /*case '1':                 PROPOSTA ÚTIL SI LA VB RECONEGUÉS HOME I END AMB ESC[num~
                    case '2':
                    case '3':
                    case '4':
                        tecla1=super.read();
                        if(tecla1!='~')
                            return tecla;
                        return Dicc.t_HOME + tecla - '1';*/
                    //default: return tecla; 
                }
            //default: return tecla;   
        return tecla;
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
                case Dicc.t_ERROR: 
                    break; //davant la detecció d'aquetes tecles, anulem les tecles
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

