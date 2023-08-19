import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;

public class VPin extends JFrame {

	private JPanel contentPane;
	private JTextField tfPActual;
	private JTextField tfPNuevo;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VPin frame = new VPin();
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
	public VPin() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblCambioPin = new JLabel("Cambio Pin");
		lblCambioPin.setHorizontalAlignment(SwingConstants.CENTER);
		lblCambioPin.setFont(new Font("Segoe UI", Font.BOLD, 20));
		lblCambioPin.setBounds(157, 30, 120, 30);
		contentPane.add(lblCambioPin);
		
		JLabel lblPActual = new JLabel("Ingrese su pin actual");
		lblPActual.setHorizontalAlignment(SwingConstants.CENTER);
		lblPActual.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		lblPActual.setBounds(137, 65, 160, 20);
		contentPane.add(lblPActual);
		
		JLabel lblPNuevo = new JLabel("Ingrese su nuevo pin");
		lblPNuevo.setHorizontalAlignment(SwingConstants.CENTER);
		lblPNuevo.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		lblPNuevo.setBounds(137, 125, 160, 20);
		contentPane.add(lblPNuevo);
		
		tfPActual = new JTextField();
		tfPActual.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		tfPActual.setBounds(162, 95, 110, 20);
		contentPane.add(tfPActual);
		tfPActual.setColumns(10);
		
		tfPNuevo = new JTextField();
		tfPNuevo.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		tfPNuevo.setColumns(10);
		tfPNuevo.setBounds(162, 155, 110, 20);
		contentPane.add(tfPNuevo);
		
		JButton btnDAceptar = new JButton("Aceptar");
		btnDAceptar.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		btnDAceptar.setBounds(256, 195, 100, 30);
		contentPane.add(btnDAceptar);
		
		JButton btnDMenu = new JButton("Ir atras");
		btnDMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				VMenu menu = new VMenu();
				menu.setVisible(true);
			}
		});
		btnDMenu.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		btnDMenu.setBounds(78, 195, 100, 30);
		contentPane.add(btnDMenu);
		
		JLabel lblPMensaje = new JLabel("");
		lblPMensaje.setHorizontalAlignment(SwingConstants.CENTER);
		lblPMensaje.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		lblPMensaje.setBounds(117, 235, 200, 20);
		contentPane.add(lblPMensaje);
	}
}
