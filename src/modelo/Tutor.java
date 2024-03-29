package modelo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Tutor extends Usuario {
    private String correoUMA;
    private ArrayList<Evento> propuesto;
    private ArrayList<Usuario> usuario;

    public Tutor(String cUMA, String correo, String usr, String contr){
        super(correo, usr, contr);
        BD bd = new BD();
        bd.Insert("INSERT INTO Tutor VALUES ('" + correo + "', '" + cUMA + "');");
        correoUMA = cUMA;
        propuesto = null;
        usuario = null;
    }

    public Tutor(String correo) {
        super(correo);
        BD bd = new BD();
        List<Object[]> userList = bd.Select("SELECT correoUMA FROM Tutor WHERE correo = '" + correo + "';");

        if (userList.size() > 0) {
            Object[] user = userList.get(0);
            correoUMA = (String)user[0];
            propuesto = null;
            usuario = null;
        } else {
            throw new ErrorBD("No se ha encontrado un tutor con correo " + correo);
        }
    }

    public void crearCurso(Date dia, String nombre, int numClases, int duracion){
        Curso curso = new Curso(dia, nombre, this, numClases, duracion);
        if (propuesto == null) {
            propuesto = new ArrayList<>();
        }
        propuesto.add(curso);
        super.getEventosInscritos().add(curso);
        BD bd = new BD();
        bd.Insert("INSERT INTO UsuarioEvento (correo, nombre) VALUES ('" + getCorreo().toLowerCase() + "', '" + nombre + "');");
    }

    public void crearActividad(Date dia, String nombre, String lugar) {
        ActividadSocial actividad = new ActividadSocial(dia, nombre, this, lugar);
        if (propuesto == null){
            propuesto = new ArrayList<>();
        }
        propuesto.add(actividad);
        super.getEventosInscritos().add(actividad);
        BD bd = new BD();
        bd.Insert("INSERT INTO UsuarioEvento (correo, nombre) VALUES('" + getCorreo().toLowerCase() + "', '" + nombre + "');");
    }

    public void crearConferencia(Date dia, String nombre, String link) {
        Conferencia conferencia = new Conferencia(dia, nombre, this, link);
        if (propuesto == null){
            propuesto = new ArrayList<>();
        }
        propuesto.add(conferencia);
        super.getEventosInscritos().add(conferencia);
        BD bd = new BD();
        bd.Insert("INSERT INTO UsuarioEvento (correo, nombre) VALUES('" + getCorreo().toLowerCase() + "', '" + nombre + "');");
    }

    public void eliminarEvento(Evento evento){
        if (!(propuesto == null)){
            propuesto.removeIf(ev -> evento.getNombre().equals(evento.getNombre()));
            super.getEventosInscritos().remove(evento);
        }
    }

        public void modificarInformacion(String cor, String nombr, String corUMA){
        super.modificarInformacion(cor, nombr, null);
        BD bd = new BD();
        bd.Update("UPDATE Tutor SET correoUMA = '" + corUMA + "' WHERE correo = '" + getCorreo() + "';");
        correoUMA = corUMA;

    }

    public void eliminarCuenta(){
        BD bd = new BD();
        bd.Delete("DELETE FROM Tutor WHERE correo = '" + this.getCorreo() + "';");
        correoUMA = null;
        super.eliminarCuenta();
    }

    @Override
    public String getCorp() {
        return this.correoUMA;
    }


    public String getCorreoUMA() {
        return correoUMA;
    }

    public ArrayList<Usuario> getUsuario() {
        return usuario;
    }
}
