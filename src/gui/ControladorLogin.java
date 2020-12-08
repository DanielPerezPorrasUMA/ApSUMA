package gui;

import modelo.BD;
import modelo.Usuario;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Controlador para la pantalla de login.
 */
public class ControladorLogin implements ActionListener {

    private final VistaLogin vista;

    public ControladorLogin(VistaLogin vista) 
    {
        this.vista = vista;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {

            case "INICIAR":

                // Comprobar si existe un usuario con el nombre y contraseña dados.
                String correo = vista.getTextoUsuario();
                String contrasenia = vista.getTextoContrasenia();

                BD bd = new BD();
                int numUsuarios = (int)bd.SelectEscalar("SELECT COUNT(*) FROM Usuario WHERE " +
                        "correo = '" + correo + "' AND contra = '" + contrasenia + "'");

                if (numUsuarios > 0) {
                    Usuario usuario = Usuario.buscarUsuario(correo);
                    // Tenemos que tener algún sitio para guardar la información de sesión
                    // al que podamos acceder desde las diferentes pantallas.
                } else {
                    JOptionPane.showMessageDialog(vista,
                            "Usuario o contraseña incorrectos.",
                            "Error inicio de sesión",
                            JOptionPane.ERROR_MESSAGE);
                }

                break;

            case "REGISTRAR":
                // Ir a la pantalla de Registro.
                break;

            case "INVITADO":
                // Abrir como invitado la pantalla principal
                break;

        }
    }

}
