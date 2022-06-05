import socket
import threading


class Server:
    #host = '127.0.0.1'   #---> LocalHost
    #port =  8080

    def __init__(self):
        self.ss = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
        self.ss.bind(('127.0.0.1', 8080))
        self.ss.listen()
        self.dicc = dict()
        print("\n\033[1;35m"+"Welcome to Server"+"\033[0m")
        

    def broadcast(self, message, c):
        for valor in self.dicc.values():
            if (valor != c):
                valor.send(message)

    #quan un usuari s'hi afegeix al xat, s'indica els usuaris que ja hi estàven presents
    def initial_broadcast(self, c, username): 
        for clave in self.dicc:
            if(clave != username):
                c.send("{}:ja estava al xat!\n".format(clave).encode('utf-8'))

    def handle(self, username):
        c = self.dicc.get(username)
        while True:
            try:
                #c = self.dicc.get(username)
                message = c.recv(1024)
                if(message.decode('utf-8') == 'CLOSE'):
                    print(username+"\033[2;33m"+":s'ha desconnectat"+"\033[0m")
                    #self.broadcast('{} ha marxat!'.format(username).encode('utf-8'), c)
                    c.send('CLOSE'.encode('utf-8'))
                    c.close()
                elif(message.decode('utf-8') == 'LIST'):
                    c.send(str(self.dicc.keys()))                    
                else:
                    self.broadcast(message, c)
            except:
                c.close()
                self.broadcast('\n{}:ha marxat!\n'.format(
                    username).encode('utf-8'), c)
                self.dicc.pop(username)
                break

    def rebre_missatge(self):
        while True:
            c, a = self.ss.accept()
            print("\033[1;35m"+"Connexio nova amb {}".format(str(a))+"\033[0m")
            c.send('USERNAME'.encode('utf-8'))
            username = c.recv(1024).decode('utf-8')
            self.dicc[username] = c

            print("\033[1;33m"+"Usuari:  {}".format(username))
            self.broadcast("{}:s'ha unit al chat!\n".format(username).encode('utf-8'), c)

            self.initial_broadcast(c, username)
            #c.send('Connected to server!\n'.encode('utf-8'))
            
            thread = threading.Thread(target=self.handle, args=(username, ))
            thread.start()
                
serv = Server()
serv.rebre_missatge()

