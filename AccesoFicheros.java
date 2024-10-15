
package practica_ud1;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import org.w3c.dom.*;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;
import java.io.*;


public class AccesoFicheros {

    
 
    private ArrayList<Jugador> jugadores; // lista de jugadores que voy a escribir o leer en un fichero
    
    public AccesoFicheros(){
        
        this.jugadores = new ArrayList<Jugador>();
    }
    
    public ArrayList<Jugador> getJugadores() {
        return jugadores;
    }
    
    public void leerFicheroTexto(String rutaArchivo){
        
        try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))){
            
            String linea;
            String[] datosJugador; // para guardar cada datos por separado de cada jugador
            Jugador player;
            while((linea = br.readLine()) != null){// mientras que haya datos en el fichero que leer
                
                player = new Jugador();
                datosJugador = linea.split(";"); // guarda los datos de cada jugador separando cada dato por el caracter ";"
                player.setId(Integer.parseInt(datosJugador[0]));
                player.setNick_name(datosJugador[1]);
                player.setExperience(Integer.parseInt(datosJugador[2]));
                player.setLife_level(Integer.parseInt(datosJugador[3]));
                player.setCoins(Integer.parseInt(datosJugador[4]));
                this.getJugadores().add(player);
            }
            br.close();
           
        }catch(FileNotFoundException fn){
            System.out.println("Fichero no encontrado");
        }catch(Exception e){
            System.out.println("Error");
        }
    }
    
    public void escribirFicheroTexto(ArrayList<Jugador> jugadores, String rutaArchivo){
        
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(rutaArchivo))){
            
            for (int i = 0; i < jugadores.size(); i++) { // escribira tantas lineas como jugadores haya en la lista
                
                bw.write(jugadores.get(i).toString());
                bw.newLine();
            }
            
            bw.close();
            
        }catch(FileNotFoundException fn){
            System.out.println("Fichero no encontrado");
        }catch(IOException e){
            System.out.println("Error");
        }
    }
    
    public void escribirFicheroBinario(ArrayList<Jugador> jugadores, String rutaArchivo){
        
        
        try{
            File fichero = new File(rutaArchivo);
            FileOutputStream fos = new FileOutputStream(fichero);
            DataOutputStream dos = new DataOutputStream(fos);
            for (int i = 0; i < jugadores.size(); i++){// escribira los datos de todos los jugadores que se encuentren en la lista
            
                dos.writeInt(jugadores.get(i).getId());
                dos.writeUTF(jugadores.get(i).getNick_name());
                dos.writeInt(jugadores.get(i).getExperience());
                dos.writeInt(jugadores.get(i).getLife_level());
                dos.writeInt(jugadores.get(i).getCoins());
            }
            
            dos.close();
            
        }catch(FileNotFoundException fn){
            System.out.println("Fichero no encontrado");
        }catch(IOException e){
            System.out.println("Error");
        }
    }
    
    public void leerFicheroBinario(String rutaArchivo) throws FileNotFoundException, IOException{
        
        DataInputStream dis = new DataInputStream(new FileInputStream(new File(rutaArchivo)));
        Jugador player;
        try{
            
            
            
            while(dis.available() > 0){ // leera mientras el fichero siga teniendo datos que leer
                
                player = new Jugador();
                player.setId(dis.readInt());
                player.setNick_name(dis.readUTF());
                player.setExperience(dis.readInt());
                player.setLife_level(dis.readInt());
                player.setCoins(dis.readInt());
                getJugadores().add(player);
            }
        }catch(FileNotFoundException fn){
            System.out.println("Fichero no encontrado");
        }catch(IOException e){
            System.out.println("Error");
        }
        dis.close();
    }
    
    public void escribirFicheroObjBinario(ArrayList<Jugador> jugadores, String rutaArchivo){
        
        try{
            ObjectOutputStream dataOS = new ObjectOutputStream(new FileOutputStream(new File(rutaArchivo)));
            
            for (int i = 0; i < jugadores.size(); i++) { // escrbira tantos objetos en el fichero como jugadores haya en la lista
                
                dataOS.writeObject(jugadores.get(i));
            }
            
            dataOS.close();
            
        }catch(FileNotFoundException fn){
            System.out.println("Fichero no encontrado");
        }catch(IOException e){
            System.out.println("Error");
        }
    }
    
    public void leerFicheroObjBinario(String rutaArchivo) throws FileNotFoundException, IOException{
        
        ObjectInputStream dataIS = new ObjectInputStream(new FileInputStream(new File(rutaArchivo)));
        Jugador player;
            
        try{
            while(true){ // leera mientras haya objetos que leer en el fichero
                
                player = new Jugador();
                player = (Jugador) dataIS.readObject();
                getJugadores().add(player);
            }
        }catch(FileNotFoundException fn){
            System.out.println("Fichero no encontrado");
        }catch(ClassNotFoundException cn){
            System.out.println("Clase no encontrada");
        }catch(IOException e){
            System.out.println("Error");
        }
        dataIS.close();
    }
    
    public void leerFicheroRandom(String rutaArchivo){
        
        try{
            
            RandomAccessFile random = new RandomAccessFile(new File(rutaArchivo), "r");
            int posicion = 0;
            char[] nick_name = new char[15];
            char aux;
            Jugador player;
            
            for(;;){
                
               player = new Jugador();
               random.seek(posicion);
               player.setId(random.readInt());
               
                for (int i = 0; i < nick_name.length; i++) {
                    
                    aux = random.readChar();
                    nick_name[i] = aux;
                }
               
                String nick_nameString = new String(nick_name);
                player.setNick_name(nick_nameString);
                player.setExperience(random.readInt());
                player.setLife_level(random.readInt());
                player.setCoins(random.readInt());
                this.getJugadores().add(player);
                
                posicion = posicion + 46;
                
                if(random.getFilePointer() == random.length())
                    break;
            }
            
            posicion = 0;
            random.close();
            
        }catch(FileNotFoundException fn){
            System.out.println("Fichero no encontrado");
        }catch(IOException e){
            System.out.println("Fichero no encontrado");
        }
    }
    
    public void escribirFicheroRandom(ArrayList<Jugador> jugadores, String rutaArchivo) throws FileNotFoundException, IOException{
        
        RandomAccessFile random = new RandomAccessFile(new File(rutaArchivo), "rw");
        
        StringBuffer buffer = null; // para guardar el nick_name del jugador y asignarle una longitud luego
        
        for (int i = 0; i < jugadores.size(); i++) {
            
            random.writeInt(jugadores.get(i).getId());
            buffer = new StringBuffer(jugadores.get(i).getNick_name());
            buffer.setLength(15);
            random.writeChars(buffer.toString());
            
            random.writeInt(jugadores.get(i).getExperience());
            random.writeInt(jugadores.get(i).getLife_level());
            random.writeInt(jugadores.get(i).getCoins());
        }
        
        random.close();
    }
    
    public void leerFicheroXML(String rutaArchivo){
    
        try{
        
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            
            Document document = builder.parse(new File(rutaArchivo)); // parsea el archivo xml y lo obtiene el DOM
            document.getDocumentElement().normalize(); // normaliza la estructura del xml
            
            NodeList jugadoresNodo = document.getElementsByTagName("jugador"); // crea una lista de los elementos "jugador" del xml
            
            Jugador player;
            
            for (int i = 0; i < jugadoresNodo.getLength(); i++) { // recorro la lista de jugadores del xml
                
                player = new Jugador();
                Node jugador = jugadoresNodo.item(i); // obtengo el elemento i de la lista de jugadores del xml
                Element jugadorObjeto = (Element) jugador; // lo transformo en un objeto para poder trabajar con el en el programa
                
                player.setId(Integer.parseInt(jugadorObjeto.getElementsByTagName("id").item(0).getTextContent()));
                player.setNick_name(jugadorObjeto.getElementsByTagName("nick_name").item(0).getTextContent());
                player.setExperience(Integer.parseInt(jugadorObjeto.getElementsByTagName("experience").item(0).getTextContent()));
                player.setLife_level(Integer.parseInt(jugadorObjeto.getElementsByTagName("life_level").item(0).getTextContent()));
                player.setCoins(Integer.parseInt(jugadorObjeto.getElementsByTagName("coins").item(0).getTextContent()));
                this.getJugadores().add(player);
            }
            
        }catch(Exception e){
            System.out.println("Error");
        }
    }
    
    public void escribirFicheroXML(ArrayList<Jugador> jugadores, String rutaArchivo){
    
        try{
        
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.newDocument();
            
            Element raiz = document.createElement("jugadores");
            document.appendChild(raiz); // añade el elemento raiz al archivo
            
            Element jugador, id, nick_name, experience, life_level, coins;
            
            for (int i = 0; i < jugadores.size(); i++) {
                
                jugador = document.createElement("jugador");
                raiz.appendChild(jugador); // cuelga jugador de jugadores
                
                // para cada jugador, creo nuevamente los nodos y les asigno los datos correspondientes
                id = document.createElement("id");
                id.appendChild(document.createTextNode(jugadores.get(i).getId() + ""));
                nick_name = document.createElement("nick_name");
                nick_name.appendChild(document.createTextNode(jugadores.get(i).getNick_name()));
                experience = document.createElement("experience");
                experience.appendChild(document.createTextNode(jugadores.get(i).getExperience() + ""));
                life_level = document.createElement("life_level");
                life_level.appendChild(document.createTextNode(jugadores.get(i).getLife_level() + ""));
                coins = document.createElement("coins");
                coins.appendChild(document.createTextNode(jugadores.get(i).getCoins() + ""));
                
                // hago que los datos cuelguen de jugador
                jugador.appendChild(id);
                jugador.appendChild(nick_name);
                jugador.appendChild(experience);
                jugador.appendChild(life_level);
                jugador.appendChild(coins);
            }
            
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer = tf.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes"); // para que el archivo xml tenga las tabulaciones adecuadas
            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(new File(rutaArchivo));
            transformer.transform(source, result);
            
        }catch(Exception e){
            System.out.println("Error");
        }
    }
    // faltan metodos de xml !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
}
