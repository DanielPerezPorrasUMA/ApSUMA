package gui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import modelo.Evento;
import modelo.Usuario;
import net.sourceforge.jdatepicker.impl.JDatePanelImpl;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;
import net.sourceforge.jdatepicker.impl.UtilDateModel;
import javax.swing.JTabbedPane;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JList;
import javax.swing.JButton;
import javax.swing.JLabel;

public class VistaPrincipalInvitado extends JFrame {

	private JLabel lblDesc;
	private JLabel lblNombrePerfil;
	private JButton btnEntrar;
	private Usuario usuario;
	private JPanel contentPane;
	private JTabbedPane tabbedPane;
	private JPanel panelEventos;
	private JList listInscritos;
	private JPanel panelCalendario;
	private JButton bPerfil;

	public static void abrirVentana(Usuario usuario) {
		try {
			VistaPrincipalInvitado frame = new VistaPrincipalInvitado(usuario);
			frame.controlador(new ControladorPrincipalInvitado(frame));
			frame.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void cargarActividades() {

	}

	public VistaPrincipalInvitado(Usuario usuario) {
		this.usuario = usuario;
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 856, 579);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setFont(new Font("Microsoft JhengHei UI", Font.PLAIN, 11));
		tabbedPane.setBounds(10, 11, 830, 528);
		contentPane.add(tabbedPane);
		
		panelEventos = new JPanel();
		tabbedPane.addTab("Eventos", null, panelEventos, null);
		panelEventos.setLayout(null);
		
		listInscritos = new JList();
		listInscritos.setBounds(38, 48, 362, 172);
		panelEventos.add(listInscritos);
		
		panelCalendario = new JPanel();
		panelCalendario.setBounds(596, 66, 219, 225);
		panelEventos.add(panelCalendario);
		
	
		UtilDateModel model = new UtilDateModel();
		JDatePanelImpl datePanel = new JDatePanelImpl(model);
		JDatePickerImpl datePicker = new JDatePickerImpl(datePanel);
		
		panelCalendario.add(datePicker);
		
		bPerfil = new JButton("");
		bPerfil.setBounds(753, 11, 62, 49);
		panelEventos.add(bPerfil);
		
		lblNombrePerfil = new JLabel("");
		lblNombrePerfil.setBounds(662, 27, 46, 14);
		panelEventos.add(lblNombrePerfil);
		
		lblDesc = new JLabel("");
		lblDesc.setBounds(410, 49, 181, 127);
		panelEventos.add(lblDesc);

		btnEntrar = new JButton("Entrar");
		btnEntrar.setBounds(410, 187, 89, 23);
		panelEventos.add(btnEntrar);

		cargarActividades();
	}

	public void controlador(ControladorPrincipalInvitado ctr) {
		btnEntrar.addActionListener(ctr);
		btnEntrar.setActionCommand("ENTRAR");
	}

}