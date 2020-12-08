package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import java.awt.Color;
import java.awt.Container;

import javax.swing.JComboBox;
import java.awt.SystemColor;

public class VistaPerfilTutor extends JFrame {

	private JPanel panelPrincipal;
	private JLabel lLogo;
	private JLabel lblNombreUsuario;
	private JLabel lblCorreo;
	private JLabel lblCorreoUma;
	private JLabel lblCampoNombreUsuario;
	private JLabel lblTutorgmailcom;
	private JLabel lblTutorumaes;
	private JButton btModificarDatos;
	private JButton btAtrs;
	private JButton btCerrarSesin;
	private JComboBox cbEventosCreados;
	private Container cbMisCursos;
	private JComboBox cbMisConferencias;
	private JComboBox cbMisActividades;

	
	
	/**
	 * Create the frame.
	 */
	public VistaPerfilTutor() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 689, 539);
		panelPrincipal = new JPanel();
		panelPrincipal.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(panelPrincipal);
		panelPrincipal.setLayout(null);
		
		lLogo = new JLabel("");
		lLogo.setBounds(34, 34, 207, 192);
		UtilidadesGUI.ajustarImagenALabel(lLogo, "/recursosApp/gato.png");
		panelPrincipal.add(lLogo);
		
		lblNombreUsuario = new JLabel("Nombre de usuario:");
		lblNombreUsuario.setFont(new Font("Microsoft JhengHei UI", Font.BOLD, 15));
		lblNombreUsuario.setBounds(288, 34, 146, 27);
		panelPrincipal.add(lblNombreUsuario);
		
		lblCorreo = new JLabel("Correo electr\u00F3nico:");
		lblCorreo.setFont(new Font("Microsoft JhengHei UI", Font.BOLD, 15));
		lblCorreo.setBounds(288, 88, 146, 27);
		panelPrincipal.add(lblCorreo);
		
		lblCorreoUma = new JLabel("Correo UMA:");
		lblCorreoUma.setFont(new Font("Microsoft JhengHei UI", Font.BOLD, 15));
		lblCorreoUma.setBounds(288, 151, 146, 27);
		panelPrincipal.add(lblCorreoUma);
		
		lblCampoNombreUsuario = new JLabel("Fulanito");
		lblCampoNombreUsuario.setFont(new Font("Microsoft JhengHei UI", Font.PLAIN, 15));
		lblCampoNombreUsuario.setBounds(475, 34, 146, 27);
		panelPrincipal.add(lblCampoNombreUsuario);
		
		lblTutorgmailcom = new JLabel("tutor@gmail.com");
		lblTutorgmailcom.setFont(new Font("Microsoft JhengHei UI", Font.PLAIN, 15));
		lblTutorgmailcom.setBounds(475, 88, 146, 27);
		panelPrincipal.add(lblTutorgmailcom);
		
		lblTutorumaes = new JLabel("tutor@uma.es");
		lblTutorumaes.setFont(new Font("Microsoft JhengHei UI", Font.PLAIN, 15));
		lblTutorumaes.setBounds(475, 151, 146, 27);
		panelPrincipal.add(lblTutorumaes);
		
		btModificarDatos = new JButton("Modificar datos");
		btModificarDatos.setFont(new Font("Microsoft JhengHei UI", Font.PLAIN, 15));
		btModificarDatos.setBackground(SystemColor.activeCaption);
		btModificarDatos.setBounds(356, 201, 143, 25);
		panelPrincipal.add(btModificarDatos);
		
		btAtrs = new JButton("Atr\u00E1s");
		btAtrs.setBackground(SystemColor.activeCaption);
		btAtrs.setFont(new Font("Microsoft JhengHei UI", Font.PLAIN, 15));
		btAtrs.setBounds(528, 450, 143, 41);
		panelPrincipal.add(btAtrs);
		
		btCerrarSesin = new JButton("Cerrar sesi\u00F3n");
		btCerrarSesin.setBackground(SystemColor.activeCaption);
		btCerrarSesin.setFont(new Font("Microsoft JhengHei UI", Font.PLAIN, 15));
		btCerrarSesin.setBounds(12, 450, 143, 41);
		panelPrincipal.add(btCerrarSesin);
		
		cbEventosCreados = new JComboBox();
		cbEventosCreados.setFont(new Font("Microsoft JhengHei UI", Font.PLAIN, 15));
		cbEventosCreados.setBounds(151, 274, 179, 34);
		panelPrincipal.add(cbEventosCreados);
		
		cbMisCursos = new JComboBox();
		cbMisCursos.setFont(new Font("Microsoft JhengHei UI", Font.PLAIN, 15));
		cbMisCursos.setBounds(151, 359, 179, 34);
		panelPrincipal.add(cbMisCursos);
		
		cbMisConferencias = new JComboBox();
		cbMisConferencias.setFont(new Font("Microsoft JhengHei UI", Font.PLAIN, 15));
		cbMisConferencias.setBounds(409, 274, 179, 34);
		panelPrincipal.add(cbMisConferencias);
		
		cbMisActividades = new JComboBox();
		cbMisActividades.setFont(new Font("Microsoft JhengHei UI", Font.PLAIN, 15));
		cbMisActividades.setBounds(409, 359, 179, 34);
		panelPrincipal.add(cbMisActividades);
	}
	
	public void controlador(ActionListener ctr)
	{
		btModificarDatos.addActionListener(ctr);
		btModificarDatos.setActionCommand("MODIFICAR");
		btCerrarSesin.addActionListener(ctr);
		btCerrarSesin.setActionCommand("CERRAR SESION");
		btAtrs.addActionListener(ctr);
		btAtrs.setActionCommand("ATRAS");
	}
}
