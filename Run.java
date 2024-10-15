
package practica_ud1;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;


public class Run {
    
    // elimina todo lo que hay dentro del fichero base para que solo se encuentre el archivo de almacenamiento de jugadores
    public static void limpiarDirectorio(File directorio){
        File[] archivos = directorio.listFiles();
        if (archivos.length != 0) {
            for (File archivo : archivos) { // si encuentra archivos o direcotrios en el fichero base los elimina
                if (archivo.isDirectory()) {
                    limpiarDirectorio(archivo);
                }
                archivo.delete();
            }
           
        }
    }
    
    public static void mostrarSubmenu(){ // muestra el menu de configuracion del archivo
        
        System.out.println("Elige el tipo de archivo en el que deseas guardar los datos:");
        System.out.println("1. Fichero secuencial de texto (BufferedReader/ BufferedWriter)");
        System.out.println("2. Fichero secuencial Binario (DataInputStream/ DataOutputStream)");
        System.out.println("3. Fichero de objetos binarios (ObjectInputStream/ ObjectOutputStream)");
        System.out.println("4. Fichero de acceso aleatorio binario (RandomAccesFile)");
        System.out.println("5. Fichero de texto XML (DOM)");
        System.out.println("6. Salir de este menu");
    }
    
    public static void mostrarMenu(){ // muestra el menu principal
        
        System.out.println("Indica la accion que deseas realizar:");
        System.out.println("1. Dar de alta un jugador");
        System.out.println("2. Dar de baja un jugador");
        System.out.println("3. Modificar un jugador");
        System.out.println("4. Filtrar por ID");
        System.out.println("5. Listar todos los jugadores");
        System.out.println("6. Configuracion");
        System.out.println("7. Salir del programa");
    }
    
    public static void main(String[] args) throws IOException {
        
        JugadoresMemoria jugadoresEnMemoria = new JugadoresMemoria(); // jugadores en memoria
        AccesoFicheros af = new AccesoFicheros(); // objeto que contiene los metodos para trabajar con diferentes ficheros
        Scanner sc = new Scanner(System.in);
        int eleccion; // referencia la opcion del menu que el usuario ha escogido
        String ruta = "C:\\Users\\yeray\\Documents\\NetBeansProjects\\ACCESO_DATOS\\src\\ficherobase";
        
        File directorio = new File(ruta);
        String[] archivos = directorio.list();
        
        // si es la primera vez que se utiliza el programa, el directorio base estara vacio y creara por defecto un txt.
        if(archivos.length == 0){ 
            File fichero = new File(directorio, "jugadores.txt");
            fichero.createNewFile();
        }else{ // en caso de que ya se haya usado y haya un fichero
            
            if(archivos[0].equals("jugadores.txt")){
                af.leerFicheroTexto(ruta + "\\jugadores.txt");
                jugadoresEnMemoria.setJugadores(af.getJugadores());
            }else if(archivos[0].equals("jugadores.dat")){
                af.leerFicheroBinario(ruta + "\\jugadores.dat");
                jugadoresEnMemoria.setJugadores(af.getJugadores());
            }else if(archivos[0].equals("jugadoresObj.dat")){
                af.leerFicheroObjBinario(ruta + "\\jugadoresObj.dat");
                jugadoresEnMemoria.setJugadores(af.getJugadores());
            }else if(archivos[0].equals("jugadoresRandom.dat")){
                af.leerFicheroRandom(ruta + "\\jugadoresRandom.dat");
                jugadoresEnMemoria.setJugadores(af.getJugadores());
            }else if(archivos[0].equals("jugadores.xml")){
                af.leerFicheroXML(ruta + "\\jugadores.xml");
                jugadoresEnMemoria.setJugadores(af.getJugadores());
            }else{ // si hay cualquier fichero/directorio que no corresponda con los nombres, se eliminara todo y se creara un txt
                limpiarDirectorio(directorio); 
                File fichero = new File(directorio, "jugadores.txt");
            }
        }
        
        do{ // mostrara el menu siempre y cuando la eleccion sea distinta de 7
            
            mostrarMenu();
            Jugador jugador = new Jugador();
            eleccion = sc.nextInt(); 
            
            switch(eleccion){
            
                case 1: 
                    System.out.println("Indica el nombre del jugador:");
                    sc.nextLine();
                    jugador.setNick_name(sc.nextLine());
                    System.out.println("Indica la experiencia del jugador:");
                    jugador.setExperience(sc.nextInt());
                    System.out.println("Indica el nivel de vida del jugador:");
                    jugador.setLife_level(sc.nextInt());
                    System.out.println("Indica las monedas que posee el jugador");
                    jugador.setCoins(sc.nextInt());
                    if(jugadoresEnMemoria.anadirJugador(jugador))
                        System.out.println("Jugador añadido correctamente");
                    else
                        System.out.println("No se ha podido añadir al jugador");
                    break;

                case 2: 
                    int idEliminar;
                    System.out.println("Indica el ID del jugador que deseas eliminar:");
                    idEliminar = sc.nextInt();
                    if(jugadoresEnMemoria.borrarJugador(idEliminar))
                        System.out.println("Jugador eliminado");
                    else
                        System.out.println("El jugador no se ha encontrado");
                    break;

                case 3:
                    int idModificar;
                    System.out.println("Indica el ID del jugador que deseal modificar:");
                    idModificar = sc.nextInt();
                    if(jugadoresEnMemoria.modificarJugador(idModificar))
                        System.out.println("Jugador modificado");
                    else
                        System.out.println("Jugador no encontrado");
                    break;

                case 4:
                    int idBusqueda;
                    System.out.println("ID del jugador: ");
                    idBusqueda = sc.nextInt();
                    System.out.println(jugadoresEnMemoria.filtrarId(idBusqueda));
                    break;

                case 5:
                    System.out.println(jugadoresEnMemoria.listarJugadores());
                    break;

                case 6:
                    mostrarSubmenu();
                    eleccion = sc.nextInt();
                    File archivoNuevo;
                    switch(eleccion){
                        
                        case 1:
                            limpiarDirectorio(directorio); // elimina el/los archivo existente
                            archivoNuevo = new File(directorio, "jugadores.txt");
                            archivoNuevo.createNewFile(); // creo el nuevo en base a la eleccion
                            System.out.println("Los datos se guardaran en un archivo .txt");
                            break;
                            
                        case 2:
                            limpiarDirectorio(directorio);
                            archivoNuevo = new File(directorio, "jugadores.dat");
                            archivoNuevo.createNewFile();
                            System.out.println("Los datos se guardaran en un archivo binario .dat");
                            break;
                            
                        case 3:
                            limpiarDirectorio(directorio);
                            archivoNuevo = new File(directorio, "jugadoresObj.dat");
                            archivoNuevo.createNewFile();
                            System.out.println("Los datos se guardaran en un archivo de objetos binario .dat");
                            break;
                            
                        case 4:
                            limpiarDirectorio(directorio);
                            archivoNuevo = new File(directorio, "jugadoresRandom.dat");
                            archivoNuevo.createNewFile();
                            System.out.println("Los datos se guardaran en un archivo binario de acceso aleatorio .dat");
                            break;
                            
                        case 5:
                            limpiarDirectorio(directorio);
                            archivoNuevo = new File(directorio, "jugadores.xml");
                            archivoNuevo.createNewFile();
                            System.out.println("Los datos se guardaran en un archivo .xml");
                            break;
                            
                        case 6:
                            break;
                            
                        default:
                            System.out.println("La eleccion no existe");
                            break;
                    }
                    break;

                case 7:
                    archivos = directorio.list();
                    // segun el nombre del fichero, guardara los datos en memoria en un tipo de fichero u otro
                    if(archivos[0].equals("jugadores.txt")) 
                        af.escribirFicheroTexto(jugadoresEnMemoria.getJugadores(), ruta + "\\jugadores.txt");
                    else if(archivos[0].equals("jugadores.dat"))
                        af.escribirFicheroBinario(jugadoresEnMemoria.getJugadores(), ruta + "\\jugadores.dat");
                    else if(archivos[0].equals("jugadoresObj.dat"))
                        af.escribirFicheroObjBinario(jugadoresEnMemoria.getJugadores(), ruta + "\\jugadoresObj.dat");
                    else if(archivos[0].equals("jugadoresRandom.dat"))
                        af.escribirFicheroRandom(jugadoresEnMemoria.getJugadores(), ruta + "\\jugadoresRandom.dat");
                    else if(archivos[0].equals("jugadores.xml"))
                        af.escribirFicheroXML(jugadoresEnMemoria.getJugadores(), ruta + "\\jugadores.xml");
                    else 
                        af.escribirFicheroTexto(jugadoresEnMemoria.getJugadores(), ruta + "\\jugadores.txt");
                    System.exit(0);
                    break;
            }
        }while(eleccion != 7);
    }
}
