/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package package1;

import java.util.ArrayList;

/**
 *
 * @author virtual
 */
public class Line {

    private int cursor;
    private int long_text;
    private ArrayList<Character> buffer_line; 
    private boolean insert;
    

    public Line() {
        cursor = 0;
        insert = false; //por convenio
        buffer_line = new ArrayList<Character>();
    }

    public String toString() { //REV: make sure
        String res = "";        
        for (int i = 0; i < this.buffer_line.size(); i++) {
            res+= this.buffer_line.get(i);            
        }
        return res;
    }
    
    public boolean moveto_right(){
        int aux=this.cursor;
        if(this.cursor < this.buffer_line.size()){//si no estoy al final de la línea
            this.cursor++;
            
        }
        if (aux==this.cursor){return false;}
        return true;

    }
    
    public void moveto_left(){
        if(this.cursor !=0){//si no estoy al principio de la línea
            this.cursor--;
            
        }
        
    }
    
    public int moveto_home(){
        int tmp = this.cursor;
        this.cursor=0;
        return tmp;
    }
    
    public int moveto_end(){ 
        //if(this.cursor==this.buffer_line.size()){return 0;}
        int tmp = this.cursor;       
        this.cursor = this.buffer_line.size();                
        return (this.cursor-tmp);
        
                
    }
    
    public void insert(){
        this.insert=!this.insert;
    }
    
    public void delete(){
        try{
        if(this.cursor >0){
            this.buffer_line.remove(this.cursor-1);
            this.cursor--;                        
        }                
        }catch(Exception e){
            e.printStackTrace();
            System.out.print("error en delete");            
        }        

    }
    
    public void suprimir(){
        try {
            if (this.cursor < this.buffer_line.size()) {
                
                //this.buffer_line.remove(this.cursor+1);
                this.buffer_line.remove(this.cursor);
                if (this.cursor != 0) {                    
                }                
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            System.out.print("error en suprimir");
            
        }
    }

    public boolean add(char letra){ 
        if(this.insert){ 
            //if(this.cursor < this.buffer_line.size()-1){el -1 hace que si sobreescribo (INSERTAR) "hola" con"adios" el resultado sea "adiosa"
            if(this.cursor < this.buffer_line.size()){//no estoy al final de la linea, tengo que cambiar la letra que hay
                this.buffer_line.set(this.cursor, letra);
            }else{
                this.buffer_line.add(this.cursor,letra);
            }
            this.cursor++;
            return true;
        }else{
            this.buffer_line.add(this.cursor, letra); //los valores se autodesplazan
            this.cursor++;            
            return false;
            //return true;
        }
        
    }
       
}
