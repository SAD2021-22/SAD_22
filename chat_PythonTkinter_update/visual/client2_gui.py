from email import message
import random
import socket
import threading
import tkinter
from turtle import bgcolor  # Normal Tkinter.* widgets are not themed!
from tkinter import *
from tkinter import ttk
from tkinter import simpledialog
from tkinter.ttk import Sizegrip
from tokenize import String
from typing import List
import emoji


class Client():    
    def __init__(self):                          
        self.consola= Tk()                

        self.consola.withdraw() #esconder la consola
        self.username = simpledialog.askstring("Usuari", "Escriu el teu nom d'usuari:") + str(random.randrange(9)) + str(random.randrange(9)) + str(random.randrange(9))
        #self.username = simpledialog.askstring("Usuari", "Escriu el teu nom d'usuari:")
        
        self.consola.deiconify() #apareix la consola
        self.consola.title(" PyChat_UPC: ")
        self.consola.config(bg='#D4C6BD')
        self.consola.resizable(0, 0)
        
        #definició de la grid utilitzada
        self.consola.columnconfigure(0, weight=2)
        self.consola.columnconfigure(1, weight=2)
        self.consola.columnconfigure(2, weight=2)
        self.consola.columnconfigure(3, weight=2)
        self.consola.columnconfigure(4, weight=2)
        self.consola.columnconfigure(5, weight=1)
        self.consola.columnconfigure(6, weight=1)
        self.consola.rowconfigure(0, weight=1)
        self.consola.rowconfigure(1, weight=1)
        self.consola.rowconfigure(2, weight=1)
        

        #Espai de visualització de missatges
        welcom_msg = " El teu usuari sera: "+ self.username
        self.txtMessages = Text(self.consola, width=50)
        self.txtMessages.grid(row=0, column=0, columnspan=4, sticky=S)
        self.txtMessages.configure(bg='#F5F0ED')
        self.txtMessages.configure(fg='#3D5361')
        self.txtMessages.insert(END, "\n"+welcom_msg)

        #ScrollBar associada a l'espai de missatges                   
        scroll = Scrollbar(self.consola, orient=VERTICAL)
        scroll.grid(row=0, column=4, sticky=NS)        
        self.txtMessages.config(yscrollcommand=scroll.set)
        scroll.config(command=self.txtMessages.yview)
        self.txtMessages.config(state=DISABLED)

        #Llista de contactes units al xat
        self.view_info_label = Listbox(self.consola, width=15)
        self.view_info_label.grid(row=0, column=5) 
        self.view_info_label.configure(bg='#F5F0ED')
        self.view_info_label.configure(fg='#3D5361')        
        
        #Entry per indroduir el missatge
        self.txtYourMessage = Entry(self.consola, width=67) 
        self.txtYourMessage.grid(row=1, column=0, columnspan=4)
        self.txtYourMessage.bind('<Return>', self.enviar_missatgegui)       

        #Botó d'enviar
        self.boto = Button(self.consola, text="Envia", width=15, bg='#D6B7B5', fg='#3D5361', command=self.enviar_missatge)
        self.boto.grid(row=1, column=5)
        
        #MiniTeclat de emojis       
        b_em1 = Button(self.consola, padx=10, pady=5, bg='#D6B7B5', fg='#3D5361', font=(10), text="\U0001F923", command=self.enviar_em1)
        b_em1.grid(row=2, column=0, sticky=NSEW)
        b_em2 = Button(self.consola, padx=10, pady=5, bg='#D6B7B5', fg='#3D5361', font=(10), text="\U0001F970", command=self.enviar_em2)
        b_em2.grid(row=2, column=1, sticky=NSEW)
        b_em3 = Button(self.consola, padx=10, pady=5, bg='#D6B7B5', fg='#3D5361', font=(10), text="\U0001F636", command=self.enviar_em3)
        b_em3.grid(row=2, column=2, sticky=NSEW)
        b_em4 = Button(self.consola, padx=10, pady=5, bg='#D6B7B5', fg='#3D5361', font=(10), text="\U0001F641", command=self.enviar_em4)
        b_em4.grid(row=2, column=3, sticky=NSEW)
                        

        self.crear_thread()
        self.consola.mainloop()


            
    def rebre_missatge(self):
        threading.lock.acquire()
        while True:
            try:
                missatge = self.sc.recv(1024).decode('utf-8')                
                if missatge == 'CLOSE':
                    print(" closing")
                    self.sc.close()
                    break
                if missatge == 'USERNAME':
                    self.sc.send(self.username.encode('utf-8'))                                                    
                else:
                    missatge_help = missatge.split(':',1)                    
                    if(len(missatge_help)!=1): #parsing per control de la llista d'usuaris actius al xat
                        if(missatge_help[1]=="s'ha unit al chat!\n" or missatge_help[1]=="ja estava al xat!\n" ):
                            self.view_info_label.configure(state=NORMAL)
                            self.view_info_label.insert(END, missatge_help[0])
                            self.view_info_label.configure(state=DISABLED)
                            self.view_info_label.see(END)
                        if(missatge_help[1]=="ha marxat!\n"):                            
                            #print("detecto el mensaje de que algun usuario se ha ido")
                            self.view_info_label.configure(state=NORMAL)                            
                            idx = self.view_info_label.get(0, "end").index(str(missatge_help[0][1:])) #ni idea de pq hace falta este [1:] pero así sí que funciona                                                        
                            self.view_info_label.delete(idx)
                            self.view_info_label.configure(state=DISABLED)
                            self.view_info_label.see(END)
                    self.txtMessages.configure(state=NORMAL)                    
                    self.txtMessages.insert(END, "\n "+missatge)                    
                    self.txtMessages.configure(state=DISABLED)
                    self.txtMessages.see(END)         
            except Exception as e:
                print(" Hi ha hagut algun error")
                print(e)
                threading.lock.release()
                self.sc.close()
                break
            
            
    def enviar_missatge(self):
        threading.lock.acquire()
        self.txtMessages.configure(state=NORMAL)
        
        missatge = self.txtYourMessage.get()
        self.txtYourMessage.delete(0, END) #para q desaparezca el mensaje al enviar
           
        if(missatge ==""):
            print("Connected to server") 
        elif(missatge=="em1"):
            missatge = "\U0001F923"
            missatge_f = '{}: {}'.format(self.username, missatge)
            self.txtMessages.configure(fg='red')
            self.txtMessages.insert(END, "\n" + " You: " + missatge)
            self.sc.send(missatge_f.encode('utf-8'))
        elif(missatge=="lista"):
            self.sc.send('LIST'.encode('utf-8'))
        elif(missatge == "adeu" or missatge == "adios" or missatge == "exit"):
            print("\n")
            self.txtMessages.insert(END, "\n" + " You: sending close, ")
            self.sc.send('CLOSE'.encode('utf-8'))
            #self.sc.close()
            quit()
        else:
            missatge_f = '{}: {}'.format(self.username, missatge)
            self.txtMessages.insert(END, "\n" + " You: " + missatge)
            self.sc.send(missatge_f.encode('utf-8'))
        self.txtMessages.configure(state=DISABLED)
        threading.lock.release()

    def enviar_em1(self):        
        self.txtMessages.configure(state=NORMAL)            
        missatge = "\U0001F923"            
        missatge_f = '{}: {}'.format(self.username, missatge)
        self.txtMessages.insert(END, "\n" + " You: " + missatge)
        self.sc.send(missatge_f.encode('utf-8'))
        self.txtMessages.configure(state=DISABLED)
    
    def enviar_em2(self):
        self.txtMessages.configure(state=NORMAL)            
        missatge = "\U0001F970"            
        missatge_f = '{}: {}'.format(self.username, missatge)
        self.txtMessages.insert(END, "\n" + " You: " + missatge)
        self.sc.send(missatge_f.encode('utf-8'))
        self.txtMessages.configure(state=DISABLED)

    def enviar_em3(self):
        self.txtMessages.configure(state=NORMAL)                    
        missatge = "\U0001F636"             
        missatge_f = '{}: {}'.format(self.username, missatge)
        self.txtMessages.insert(END, "\n" + " You: " + missatge)
        self.sc.send(missatge_f.encode('utf-8'))
        self.txtMessages.configure(state=DISABLED)
    
              
    def enviar_em4(self):
        self.txtMessages.configure(state=NORMAL)            
        missatge = "\U0001F641"                
        missatge_f = '{}: {}'.format(self.username, missatge)
        self.txtMessages.insert(END, "\n" + " You: " + missatge)
        self.sc.send(missatge_f.encode('utf-8'))
        self.txtMessages.configure(state=DISABLED)
                
    def enviar_missatgegui(self, event):
        self.enviar_missatge()

    def crear_thread(self):
        self.sc = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
        # --->El server sempre serà el localHost
        self.sc.connect(('127.0.0.1', 8080))
        receive_thread = threading.Thread(target=self.rebre_missatge)
        receive_thread.daemon = True
        receive_thread.start()

        write_thread = threading.Thread(target=self.enviar_missatge)
        write_thread.daemon = True
        write_thread.start()    


client = Client() 
client.crear_thread()
    





