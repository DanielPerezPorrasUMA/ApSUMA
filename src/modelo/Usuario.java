package modelo;

import gui.VistaNuevaPrincipal;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public abstract class Usuario {

    private String correo;
    private String nombreUsuario;
    private String contra;
    private ArrayList<Evento> eventosInscritos;

    public Usuario(){}

    // Constructor para crear un nuevo usuario
    public Usuario(String cor, String nombreUs, String contr){
        BD bd = new BD();
        bd.Insert("INSERT INTO Usuario VALUES ('" + cor + "', '" + nombreUs + "', '" + contr + "');");
        correo = cor;
        nombreUsuario = nombreUs;
        contra = contr;
        eventosInscritos = null;
    }

    // Constructor para recuperar los datos de un usuario ya existente
    public Usuario(String cor) {

        BD bd = new BD();
        List<Object[]> userList = bd.Select("SELECT * FROM Usuario WHERE correo = '" + cor + "';");

        if (userList.size() > 0) {
            Object[] user = userList.get(0);
            correo = cor;
            nombreUsuario = (String)user[1];
            contra = (String)user[2];
            eventosInscritos = null;
        } else {
            throw new ErrorBD("No se ha encontrado un usuario con correo " + cor);
        }

    }

    public void modificarInformacion(String cor, String nombr, String corCorp){
        // Hacer sentencia SQL "UPDATE..."
        BD bd = new BD();
        bd.Update("UPDATE Usuario SET correo = '" + cor + "', nombreUsuario = '" + nombr + "' WHERE correo = '" + getCorreo() + "' ;");
        correo = cor;
        nombreUsuario = nombr;
    }

    public void eliminarCuenta(){
        // Hacer sentencia SQL "DELETE..."
        BD bd = new BD();
        bd.Delete("DELETE FROM Usuario WHERE correo = '" + correo + "';");
        correo = null;
        nombreUsuario = null;
        contra = null;
    }

    public void darseAltaEvento(Evento evento){
        eventosInscritos.add(evento);
        BD bd = new BD();
        bd.Insert("INSERT INTO UsuarioEvento VALUES('" + this.getCorreo() + "', '" + evento.getNombre() + "');");
    }

    public void darseBajaEvento(Evento evento){
        eventosInscritos.remove(evento);
        BD bd = new BD();
        bd.Delete("DELETE FROM UsuarioEvento WHERE correo = '" + correo + "';");
    }

    public String getCorreo() {
        return correo;
    }

    public String getNombreUsuario(){
        return nombreUsuario;
    }

    public List<Evento> getEventosInscritos() {
        if (eventosInscritos == null) {
            eventosInscritos = new ArrayList<>();
            BD bd = new BD();
            List<Object[]> resultados = bd.Select("SELECT nombre FROM UsuarioEvento WHERE correo='" + getCorreo() + "'");
            for (Object[] tupla : resultados) {
                eventosInscritos.add(Evento.buscarEvento((String)tupla[0]));
            }
        }
        return eventosInscritos;
    }

    // Devuelve una lista vacía si se llama en un estudiante.
    public List<Evento> getEventosCreados() {
        List<Evento> eventosCreados = new ArrayList<>();
        BD bd = new BD();
        List<Object[]> resultados = bd.Select("SELECT nombre FROM Evento WHERE correo='" + getCorreo() + "'");
        for (Object[] tupla : resultados) {
            eventosCreados.add(Evento.buscarEvento((String)tupla[0]));
        }
        return eventosCreados;
    }

    public static Usuario buscarUsuario(String correo) {
        Usuario usuario = null;
        try {
            usuario = new Estudiante(correo);
        } catch (ErrorBD ex1) {
            try {
                usuario = new Tutor(correo);
            } catch (ErrorBD ex2) {
                try {
                    usuario = new Colaborador(correo);
                } catch (ErrorBD ex3) {
                    try {
                        usuario = new Administrador(correo);
                    } catch (ErrorBD ignored) { }
                }
            }
        }
        return usuario;
    }

    public static Usuario crearInvitado(){
        Usuario usuario = null;
        try {
            usuario = new Invitado();
        } catch (ErrorBD ex1){
            throw new ErrorBD("No se ha podido registrar invitado");
        }
        return usuario;
    }

    public static boolean correoEstaUsado(String correo) {
        BD bd = new BD();
        Object cuenta = bd.SelectEscalar("SELECT COUNT(*) FROM Usuario WHERE correo='" + correo + "'");
        return (long)cuenta > 0;
    }
    
    public ArrayList<Evento> getEventosFecha(Date fecha) {
    	java.sql.Date sqlfecha = new java.sql.Date(fecha.getTime());
    	ArrayList<Evento> eventos = new ArrayList<Evento>();

        BD bd = new BD();
        List<Object[]> resultados = bd.Select("SELECT nombre FROM Evento WHERE fecha='" + sqlfecha.toString() + "'");
        for (Object[] tupla : resultados) {
            eventos.add(Evento.buscarEvento((String)tupla[0]));
        }

        return eventos;
    }

    public String getCorp()
    {
    	return "";
    }

    public void abrirVentanaPrincipal() {
        VistaNuevaPrincipal.abrirVentana();
    }

}
