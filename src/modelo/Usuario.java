package modelo;

public abstract class Usuario {

    private String correo;
    private String DNI;
    private String nombre;
    private String apellido;
    private String contra;
    private Evento[] actividad;

    // Constructor para crear un nuevo usuario
    public Usuario(String cor, String dni, String nombr, String apell, String contr){
        // Hacer sentencia SQL "INSERT..."
        correo = cor;
        DNI = dni;
        nombre = nombr;
        apellido = apell;
        contra = contr;
    }

    // Constructor para recuperar los datos de un usuario ya existente
    public Usuario(String cor) {
        // Hacer sentencia SQL "SELECT..."
        correo = cor;
    }

    private void modificarInformacion(String cor, String dni, String nombr, String apell, String contr){
        correo = cor;
        DNI = dni;
        nombre = nombr;
        apellido = apell;
        contra = contr;
    }

    public void eliminarCuenta(){
        correo = null;
        DNI = null;
        nombre = null;
        apellido = null;
        contra = null;
        actividad = null;
    }

    public String getCorreo() {
        return correo;
    }

    public String getDNI() {
        return DNI;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public Evento[] getActividad() {
        return actividad;
    }

    public Tutor[] getTutor() {
        return tutor;
    }

    private Tutor[] tutor;

}
