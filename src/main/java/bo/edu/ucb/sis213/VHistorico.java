import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.JToggleButton;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextArea;
import java.awt.Color;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VHistorico extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VHistorico frame = new VHistorico();
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
	public VHistorico() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblExtracto = new JLabel("Extracto");
		lblExtracto.setHorizontalAlignment(SwingConstants.CENTER);
		lblExtracto.setFont(new Font("Segoe UI", Font.BOLD, 20));
		lblExtracto.setBounds(167, 30, 100, 30);
		contentPane.add(lblExtracto);
		
		JLabel lblHMonto = new JLabel("Su saldo es");
		lblHMonto.setHorizontalAlignment(SwingConstants.RIGHT);
		lblHMonto.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		lblHMonto.setBounds(110, 70, 100, 20);
		contentPane.add(lblHMonto);
		
		JLabel lblSaldo = new JLabel("36012");
		lblSaldo.setHorizontalAlignment(SwingConstants.LEFT);
		lblSaldo.setFont(new Font("Segoe UI", Font.BOLD, 14));
		lblSaldo.setBounds(220, 70, 100, 20);
		contentPane.add(lblSaldo);
		/*
		TextArea taHistorico = new TextArea();
		taHistorico.setBackground(new Color(240, 240, 240));
		taHistorico.setText("dfgdfgdgf\r\ndfgdfgdgf\r\ndfgdfgdgf\r\ndfgdfgdgf\r\ndfgdfgdgf\r\ndfgdfgdgf\r\ndfgdfgdgf\r\ndfgdfgdgf\r\ndfgdfgdgf\r\ndfgdfgdgf\r\ndfgdfgdgf\r\ndfgdfgdgf\r\ndfgdfgdgf\r\ndfgdfgdgf\r\ndfgdfgdgf\r\ndfgdfgdgf\r\ndfgdfgdgf\r\nv\r\ndfgdfgdgf\r\ndfgdfgdgf");
		taHistorico.setBounds(17, 140, 400, 110);
		contentPane.add(taHistorico);
		*/
		JTextArea taHistorico = new JTextArea();
		taHistorico.setEditable(false);
		taHistorico.setBackground(new Color(240, 240, 240));
		taHistorico.setText("asdasd");

		JScrollPane BarraDesplazamiento = new JScrollPane(taHistorico);
		BarraDesplazamiento.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		BarraDesplazamiento.setBounds(17, 100, 400, 110);
		contentPane.add(BarraDesplazamiento);
		
		JButton btnDAceptar = new JButton("Aceptar");
		btnDAceptar.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		btnDAceptar.setBounds(256, 220, 100, 30);
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
		btnDMenu.setBounds(78, 220, 100, 30);
		contentPane.add(btnDMenu);
	}
}
