package classes;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.BoxLayout;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Color;
import javax.swing.JTextField;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class zipPrograma {

	private JFrame frame;
	private JTextField txtArquivo;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					zipPrograma window = new zipPrograma();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public zipPrograma() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setForeground(Color.RED);
		frame.setBounds(100, 100, 468, 273);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		frame.getContentPane().setLayout(gridBagLayout);
		
		JLabel label = new JLabel("");
		GridBagConstraints gbc_label = new GridBagConstraints();
		gbc_label.insets = new Insets(0, 0, 5, 5);
		gbc_label.gridx = 9;
		gbc_label.gridy = 0;
		frame.getContentPane().add(label, gbc_label);
		
		JLabel lblDigiteOLocal = new JLabel("Digite o Local do arquivo que deseja compactar:");
		lblDigiteOLocal.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblDigiteOLocal.setForeground(Color.RED);
		GridBagConstraints gbc_lblDigiteOLocal = new GridBagConstraints();
		gbc_lblDigiteOLocal.gridwidth = 11;
		gbc_lblDigiteOLocal.insets = new Insets(0, 0, 5, 5);
		gbc_lblDigiteOLocal.gridx = 1;
		gbc_lblDigiteOLocal.gridy = 1;
		frame.getContentPane().add(lblDigiteOLocal, gbc_lblDigiteOLocal);
		
		JButton btnCompactar = new JButton("Compactar");
		btnCompactar.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent arg0) {
				
				if(txtArquivo.getText() == "")
				{	JOptionPane.showInputDialog("Digite o local do arquivo "
						+ "		que quer compacatar");
					return;
				}
				
			
			Compactador Comp = new Compactador(txtArquivo.getText());
			Comp.Compactar();
			//JOptionPane.showInputDialog(Comp.toString());
				
			}
		});
		
		txtArquivo = new JTextField();
		GridBagConstraints gbc_txtArquivo = new GridBagConstraints();
		gbc_txtArquivo.gridwidth = 9;
		gbc_txtArquivo.insets = new Insets(0, 0, 5, 5);
		gbc_txtArquivo.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtArquivo.gridx = 2;
		gbc_txtArquivo.gridy = 3;
		frame.getContentPane().add(txtArquivo, gbc_txtArquivo);
		txtArquivo.setColumns(10);
		btnCompactar.setForeground(Color.RED);
		GridBagConstraints gbc_btnCompactar = new GridBagConstraints();
		gbc_btnCompactar.insets = new Insets(0, 0, 5, 5);
		gbc_btnCompactar.gridx = 6;
		gbc_btnCompactar.gridy = 4;
		frame.getContentPane().add(btnCompactar, gbc_btnCompactar);
	}

}
