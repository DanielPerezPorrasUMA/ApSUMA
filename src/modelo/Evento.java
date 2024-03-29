package modelo;

import modelo.contenido.Contenido;

import javax.swing.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public abstract class Evento {

    protected static final SimpleDateFormat formatoFecha = new SimpleDateFormat("dd-MM-yyyy");

    protected Date Fecha;
    private String Nombre;
    private List<Usuario> usuarios;
    private Usuario creador;
    private Administrador administrador;

    // Constructor para crear un nuevo evento
    public Evento(Date dia, String nom, Usuario dueno) {
        if (dueno instanceof Tutor || dueno instanceof Colaborador) {
            BD bd = new BD();
            bd.Insert("INSERT INTO Evento VALUES('" + dia + "', '" + nom + "', '" + dueno.getCorreo() + "');");
            Fecha = dia;
            Nombre = nom;
            usuarios = null;
            creador = dueno;
        } else {
            throw new RuntimeException("El creador debe ser un tutor o un colaborador");
        }
    }

    // Constructor para recuperar los datos de un evento ya existente
    public Evento(String nombre) {
        BD bd = new BD();
        List<Object[]> eventList = bd.Select("SELECT * FROM Evento WHERE nombre = '" + nombre + "';");

        if (eventList.size() > 0) {
            Object[] event = eventList.get(0);
            Nombre = nombre;
            Fecha = (Date)event[0];
            creador = Usuario.buscarUsuario((String)event[2]);
        } else {
            throw new ErrorBD("No se ha encontrado un evento con nombre " + nombre);
        }
    }

    public void eliminar(){
        creador.eliminarEvento(this);
        BD bd = new BD();
        bd.Delete("DELETE FROM Evento where nombre = '" + getNombre() + "';");
        Fecha = null;
        Nombre = null;
        creador = null;
    }

    public void inscripcionUsuario(Usuario user) {
        if (getUsuarios().contains(user)) {
            throw new RuntimeException("El usuario ya está registrado en el evento");
        } else {
            BD bd = new BD();
            bd.Insert("INSERT INTO UsuarioEvento VALUES ('" + user.getCorreo() + "', '" + Nombre + "');");
            usuarios.add(user);
        }
    }

    public List<Usuario> getUsuarios() {
        if (usuarios == null) {
            usuarios = new ArrayList<>();
            BD bd = new BD();
            List<Object[]> tuplas = bd.Select("SELECT correo FROM UsuarioEvento WHERE nombre='" + Nombre + "' ");
            for (Object[] tupla : tuplas) {
                usuarios.add(Usuario.buscarUsuario((String)tupla[0]));
            }
        }
        return usuarios;
    }

    public Date getFecha() {
        return Fecha;
    }

    public String getNombre() {
        return Nombre;
    }

    public Usuario getCreador() {
        return creador;
    }

    public Administrador getAdministrador() {
        return administrador;
    }

    public List<Contenido> getContenidos() {
        List<Contenido> contenidos = new ArrayList<>();
        BD bd = new BD();
        String consulta = "SELECT id FROM Contenido WHERE evento='" + Nombre + "' ORDER BY id";
        List<Object[]> resultados = bd.Select(consulta);
        for (Object[] tupla : resultados) {
            contenidos.add(Contenido.buscarContenido((int)tupla[0]));
        }
        return contenidos;
    }

    public List<Foro> getForos() {
        List<Foro> foros = new ArrayList<>();
        BD bd = new BD();
        String consulta = "SELECT idForo FROM Foro WHERE evento='" + Nombre + "'";
        List<Object[]> resultados = bd.Select(consulta);
        for (Object[] tupla : resultados) {
            foros.add(new Foro((int)tupla[0]));
        }
        return foros;
    }

    public void modificar(Date fecha, String nombre) {
        BD bd = new BD();
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
        String fechaString = formato.format(fecha);
        bd.Update("UPDATE Evento SET fecha='" + fechaString + "', nombre='" + nombre + "' WHERE nombre='" + Nombre + "'");
        System.out.println("sdahflksd");
        Fecha = fecha;
        Nombre = nombre;
    }

    public static Evento buscarEvento(String nombre) {
        Evento evento = null;
        BD bd = new BD();
        String where = " WHERE nombre='" + nombre + "'";
        if ((long)bd.SelectEscalar("SELECT COUNT(*) FROM Curso" + where) > 0) {
            evento = new Curso(nombre);
        } else if ((long)bd.SelectEscalar("SELECT COUNT(*) FROM ActividadSocial" + where) > 0) {
            evento = new ActividadSocial(nombre);
        } else if ((long)bd.SelectEscalar("SELECT COUNT(*) FROM Conferencia" + where) > 0) {
            evento = new Conferencia(nombre);
        }
        return evento;
    }

    // Devuelve todos los eventos si fecha es null, o los eventos en la fecha si la fecha no es null
    public static List<Evento> getEventos(Date fecha) {

        List<Evento> eventos = new ArrayList<>();
        BD bd = new BD();
        String consulta = "SELECT nombre FROM Evento";
        if (fecha != null) {
            java.sql.Date sqlfecha = new java.sql.Date(fecha.getTime());
            consulta += " WHERE fecha='" + sqlfecha.toString() + "'";
        }

        List<Object[]> resultados = bd.Select(consulta);
        for (Object[] tupla : resultados) {
            eventos.add(Evento.buscarEvento((String)tupla[0]));
        }
        return eventos;

    }

    public String toString() {
        return Nombre;
    }

    public abstract JLabel getSubtituloPaginaEvento();

}
