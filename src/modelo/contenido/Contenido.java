package modelo.contenido;

import gui.contenido.VistaContenido;
import modelo.BD;
import modelo.ErrorBD;
import modelo.Evento;
import java.util.List;
import java.util.Objects;

public abstract class Contenido {

    private final int id;
    private final Evento evento;

    // Crea un nuevo tipo de contenido
    public Contenido(Evento ev) {
        BD bd = new BD();
        id = getSiguienteID();
        bd.Insert("INSERT INTO Contenido (id, evento, tipo) VALUES (" + id + ", '" + ev.getNombre() + "', '" + getTipo() + "')");
        evento = ev;
    }

    // Recupera un contenido
    public Contenido(int id) {
        BD bd = new BD();
        List<Object[]> contenidos = bd.Select("SELECT evento FROM Contenido WHERE id=" + id);
        if (contenidos.size() > 0) {
            this.id = id;
            evento = Evento.buscarEvento(contenidos.get(0)[0].toString());
        } else {
            throw new ErrorBD("No se ha encontrado un contenido con id " + id);
        }
    }

    public abstract String getTipo();

    public abstract VistaContenido getVista(boolean modoEdicion);

    public static Contenido buscarContenido(int id) {
        Contenido c = null;
        BD bd = new BD();
        List<Object[]> contenidos = bd.Select("SELECT tipo FROM Contenido WHERE id=" + id);
        if (contenidos.size() > 0) {
            switch (contenidos.get(0)[0].toString()) {

                case "texto":
                    c = new ContenidoTexto(id);
                    break;

                case "link":
                    c = new ContenidoEnlace(id);
                    break;
                case "prueba":
                    c = new ContenidoTest(id);
                    break;
                case "documento":
                    c = new ContenidoDocumento(id);
                    break;
                case "llamada":
                    c = new ContenidoLlamada(id);
                    break;

            }
        }
        return c;
    }

    public int getId() {
        return id;
    }

    public void borrar() {
        BD bd = new BD();
        bd.Delete("DELETE FROM Contenido WHERE id=" + id);
    }

    @Override
    public String toString() {
        return "Contenido{" +
                "id=" + id +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Contenido)) return false;
        Contenido contenido = (Contenido) o;
        return id == contenido.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    private static int getSiguienteID() {
        BD bd = new BD();
        Object resultado = bd.SelectEscalar("SELECT MAX(id) FROM Contenido");
        if (resultado != null) {
            return (int)resultado + 1;
        } else {
            return 0;
        }
    }

}
