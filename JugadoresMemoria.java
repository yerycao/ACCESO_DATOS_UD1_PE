
package practica_ud1;

import java.util.ArrayList;
import java.util.Scanner;

public class JugadoresMemoria {
    
    private ArrayList<Jugador> jugadores; // jugadores almacenados en memoria
    
    public JugadoresMemoria(){
        
        jugadores = new ArrayList<Jugador>();
    }
    
    public ArrayList<Jugador> getJugadores() {
        return jugadores;
    }
    
    public void setJugadores(ArrayList<Jugador> jugadores) {
        this.jugadores = jugadores;
    }
    
    public boolean anadirJugador(Jugador jugador){
        if(this.getJugadores().size() == 0)// si la lista de jugadores esta vacia, se le asigna el id 1 al jugador
            jugador.setId(1);
        else // si no esta vacia, obtiene el id del ultimo jugador de la lista y se le asigna al nuevo ese id + 1 para que sea unico
            jugador.setId(this.getJugadores().get(this.getJugadores().size() - 1).getId() + 1);
        return getJugadores().add(jugador);
    }
    
    public boolean borrarJugador(int id){
        
        for(int i = 0; i < getJugadores().size(); i++){ // bucle hasta que encuentre el jugador con el id pasado por parametro
            if(getJugadores().get(i).getId() == id) // si el id pasado por parametro coincide con el de algun jugador, lo elimina
                return getJugadores().remove(getJugadores().get(i));
        }
        return false;
    }
    
    public boolean modificarJugador(int id){
        
        Scanner sc = new Scanner(System.in);
        Jugador jugador = null;
        for(int i = 0; i < getJugadores().size(); i++){ // localiza el jugador que tiene el id pasado
            if(getJugadores().get(i).getId() == id)
                jugador = getJugadores().get(i);
        }
        
        if (jugador == null) // si el id pasado no coincide con ningun jugador
            return false;
        else{
            System.out.println("Indica el nuevo nombre del jugador:");
            jugador.setNick_name(sc.nextLine());
            System.out.println("Indica el nievl de experiencia:");
            jugador.setExperience(sc.nextInt());
            System.out.println("Indica el nievl de vida:");
            jugador.setLife_level(sc.nextInt());
            System.out.println("Indica cuantas monedas tiene el jugador:");
            jugador.setCoins(sc.nextInt());
            return true;
        }
    }
    
    public String filtrarId(int id){
        
        for (int i = 0; i < getJugadores().size(); i++) {
            if(getJugadores().get(i).getId() == id)
                return getJugadores().get(i).toString(); // si encuentra al jugador con el id pasado por paramtro, lo muestra
        }
        return "No se ha encontrado ningun jugador";
    }
    
    public String listarJugadores(){
        StringBuilder sb = new StringBuilder("");
        for (int i = 0; i < getJugadores().size(); i++) { // recorro la lista de jugadores para mostrarlos en pantalla uno por uno
            
            sb.append(getJugadores().get(i).toString() + "\n");
        }
        return sb.toString();
    }
    
}
