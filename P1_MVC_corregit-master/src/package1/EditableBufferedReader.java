

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
    
    public EditableBufferedReader(Reader r) {
        super(r);

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

    public String readAll() throws IOException {
        StringBuilder lecture_pack = new StringBuilder();
        do {
            lecture_pack.append((char) super.read());
        } while (super.ready());
        return lecture_pack.toString();
    }

    /**
     * Scape sequences: RIGHT: Esc [ C LEFT: Esc [ D HOME: Esc [ H END: Esc [ F
     * INSERT: Esc [ 2 ~ SUP: Esc [ 3 ~
     */

    /*public int read() throws IOException {
        int tecla;
        tecla = super.read();
        if (tecla == Dicc.ESC) {
            tecla = super.read();
            if (tecla == '[') {
                tecla = super.read();
                switch (tecla) {
                    case 'C':
                        return Dicc.t_RIGHT;
                    case 'D':
                        return Dicc.t_LEFT;
                    case 'H':
                        return Dicc.t_HOME;
                    case 'F':
                        return Dicc.t_END;
                    case '3':
                        tecla=super.read();
                        if(tecla=='~')
                            return Dicc.t_SUP;
                        break;
                    case '2':
                        tecla=super.read();
                        if(tecla=='~')
                            return Dicc.t_INSERT;
                        break;
                    case 'A': return Dicc.t_ERROR;
                    case 'B': return Dicc.t_ERROR;
                    case '5':tecla=super.read();
                        if(tecla=='~')
                            return Dicc.t_ERROR;
                        break;
                    case '6':
                        tecla=super.read();
                        if(tecla=='~')
                            return Dicc.t_ERROR;
                        break;
                        
                }
            }

        }
        return tecla;
    }*/
    public int read() throws IOException {
        String tecla;
        tecla = this.readAll();

        switch (tecla) {
            case "\033[C":
                return Dicc.t_RIGHT;
            case "\033[D":
                return Dicc.t_LEFT;
            case "\033[H":
                return Dicc.t_HOME;
            case "\033[F":
                return Dicc.t_END;
            case "\033[2~":
                return Dicc.t_INSERT;
            case "\033[3~":
                return Dicc.t_SUP;
            case "\033[A":
                return Dicc.t_ERROR;
            case "\033[B":
                return Dicc.t_ERROR;
            case "\033[5~":
                return Dicc.t_ERROR;
            case "\033[6~":
                return Dicc.t_ERROR;

        }
        char letra = this.StringToChar(tecla);
        return (int)letra;
    }

    @Override
    public String readLine() throws IOException {
        this.setRaw();
        Line line = new Line();
        Console console = new Console();
        Dicc dicc = new Dicc();
        int aux;
        line.addObserver(console);
        try {
            while ((aux = this.read()) != 13) { //diferent d'ESC
                switch (aux) {
                    case Dicc.t_RIGHT:
                        line.moveto_right();
                        break;
                    case Dicc.t_LEFT:
                        line.moveto_left();
                        break;
                    case Dicc.t_HOME:
                        line.moveto_home();
                        break;
                    case Dicc.t_END:
                        line.moveto_end();
                    case Dicc.t_INSERT:
                        line.insert();
                        break;
                    case Dicc.DEL: //el botó DEL no és una seqüencia d'escape
                        line.delete();
                        break;
                    case Dicc.t_SUP:
                        line.suprimir();
                        break;
                    case Dicc.t_ERROR: //davat la detecció d'aluna tecla no codificada, anulem l'acció
                        break;
                    default:
                        line.add((char) aux);
                        break;
                }

            }
        } catch (Exception e) {
            this.unsetRaw();
        }
        this.unsetRaw();
        return line.toString();

    }

    public char StringToChar(String str) {
        String str1 = str;
        char c[] = str.toCharArray();
        return c[0];
    }

}
