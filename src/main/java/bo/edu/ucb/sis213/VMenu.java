import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class VMenu extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VMenu frame = new VMenu();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public VMenu() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblBienvenido = new JLabel("Bienvenido \"inserte nombre\"");
		lblBienvenido.setHorizontalAlignment(SwingConstants.CENTER);
		lblBienvenido.setFont(new Font("Segoe UI", Font.BOLD, 20));
		lblBienvenido.setBounds(17, 20, 400, 30);
		contentPane.add(lblBienvenido);
		
		JButton btnDeposito = new JButton("DEPÓSITO");
		btnDeposito.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				VDeposito depositar = new VDeposito();
				depositar.setVisible(true);
			}
		});
		btnDeposito.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		btnDeposito.setBounds(64, 95, 120, 30);
		contentPane.add(btnDeposito);
		
		JButton btnRetiro = new JButton("RETIRO");
		btnRetiro.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				VRetiro retirar = new VRetiro();
				retirar.setVisible(true);
			}
		});
		btnRetiro.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		btnRetiro.setBounds(248, 95, 120, 30);
		contentPane.add(btnRetiro);
		
		JButton btnExtracto = new JButton("EXTRACTO");
		btnExtracto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				VHistorico extracto = new VHistorico();
				extracto.setVisible(true);
			}
		});
		btnExtracto.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		btnExtracto.setBounds(64, 150, 120, 30);
		contentPane.add(btnExtracto);
		
		JButton btnCambiarPin = new JButton("CAMBIAR PIN");
		btnCambiarPin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				VPin cambio = new VPin();
				cambio.setVisible(true);
			}
		});
		btnCambiarPin.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		btnCambiarPin.setBounds(248, 150, 120, 30);
		contentPane.add(btnCambiarPin);
		
		JButton btnCierre = new JButton("CERRAR SESION");
		btnCierre.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MConexion.closeConnection(null);
				dispose();
				VLogin salir = new VLogin();
				salir.setVisible(true);
			}
		});
		btnCierre.setFont(new Font("Segoe UI", Font.BOLD, 10));
		btnCierre.setBounds(157, 205, 120, 30);
		contentPane.add(btnCierre);
		
		JLabel lblOperacion = new JLabel("¿Qué le gustaría hacer a continuación?");
		lblOperacion.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		lblOperacion.setHorizontalAlignment(SwingConstants.CENTER);
		lblOperacion.setBounds(67, 60, 300, 20);
		contentPane.add(lblOperacion);
		
		UsuarioActivo activo = UsuarioActivo.getInstance();
		String a=activo.getUsuario();
		System.out.println(a);
		
		boolean isConnected = MConexion.isConnectionOpen();
        if (isConnected) {
            System.out.println("La conexión a la base de datos está abierta.");
        } else {
            System.out.println("La conexión a la base de datos está cerrada o hay un problema.");
        }
	}
}
