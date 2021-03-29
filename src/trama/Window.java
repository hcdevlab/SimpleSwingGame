
package trama;

import java.awt.*;
import javax.swing.JFrame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

import dominio.IPersonaje;
import dominio.Personaje;

public class Window extends JFrame implements ActionListener {

	/*------------------------------------------------------------------------------------------*/
	/*------------------------------*/
	Celda c;
	Celda celda[][];
	Tablero t;
	/*------------------------------*/
	/*------------------------------*/
	GridLayout gl;
	int xGrid;
	int yGrid;
	/*------------------------------*/
	/*------------------------------*/
	JTextArea jtaInfo;

	JTextField jtXPositionInitial;
	JTextField jtYPositionInitial;

	JTextField jtXPositionDestiny;
	JTextField jtYPositionDestiny;

	JTextField jtXPositionInitialAttack;
	JTextField jtYPositionInitialAttack;

	JTextField jtXPositionDestinyAttack;
	JTextField jtYPositionDestinyAttack;
	/*------------------------------*/

	/*------------------------------*/
	JButton jbCell;
	JButton jbArray[][];

	JButton jbMove;
	JButton jbAttack;
	/*------------------------------*/

	/*------------------------------*/
	JPanel panel;
	/*------------------------------*/

	/*------------------------------*/
	private boolean finalizada;
	/*------------------------------*/
	/*------------------------------------------------------------------------------------------*/

	/*------------------------------------------------------------------------------------------*/
	public Window(Tablero t) {

		this.t = t;
		int x = t.getDimension()[0];
		int y = t.getDimension()[1];

		gl = new GridLayout(x, y);
		xGrid = gl.getRows();
		yGrid = gl.getColumns();

		init(x, y);
	}
	/*------------------------------------------------------------------------------------------*/

	/*------------------------------------------------------------------------------------------*/
	public void init(int x, int y) {
		JFrame jf = new JFrame("Guerra de falacias");

		/*----------------------------------------------------------------------------------------------------*/
		// El 'menu'.
		JMenuBar menuBar = new JMenuBar();

		JMenu menu = new JMenu();
		menu.setText("Archivo");

		JMenuItem saveItem = new JMenuItem("Guardar partida");
		JMenuItem stateItem = new JMenuItem("Estado de la partida");
		JMenuItem exitItem = new JMenuItem("Salir");

		// Agregamos el 'listener':
		saveItem.addActionListener(this);
		// Asignamos número para el 'switch':
		saveItem.setActionCommand("8");

		// Agregamos el 'listener':
		exitItem.addActionListener(this);
		// Asignamos número para el 'switch':
		exitItem.setActionCommand("9");

		// Agregamos el 'listener':
		stateItem.addActionListener(this);
		// Asignamos número para el 'switch':
		stateItem.setActionCommand("10");

		menu.add(saveItem);
		menu.add(stateItem);
		menu.add(exitItem);
		menuBar.add(menu);
		/*----------------------------------------------------------------------------------------------------*/

		/*----------------------------------------------------------------------------------------------------*/
		/* 1 de 6 */
		// Generamos el panel principal, contenedor de todos:
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.X_AXIS));
		/*----------------------------------------------------------------------------------------------------*/

		/*----------------------------------------------------------------------------------------------------*/
		/* 3 de 6 */
		// El tercer panel, contenedor de sub-paneles de juego:
		JPanel buttonPanelGeneral = new JPanel();
		buttonPanelGeneral.setLayout(new BoxLayout(buttonPanelGeneral, BoxLayout.Y_AXIS));
		// buttonPanelGeneral.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		buttonPanelGeneral.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
		buttonPanelGeneral.setAlignmentX(Component.CENTER_ALIGNMENT);
		/*----------------------------------------------------------------------------------------------------*/

		/*----------------------------------------------------------------------------------------------------*/
		/* 4 de 6 */
		// El cuarto panel - Información:
		JPanel jpInfo = new JPanel();
		jpInfo.setLayout(new BoxLayout(jpInfo, BoxLayout.Y_AXIS));

		jpInfo.setAlignmentX(Component.CENTER_ALIGNMENT);
		jpInfo.setPreferredSize(new Dimension(240, 110));
		jpInfo.setMaximumSize(new Dimension(240, 110));
		/*jpInfo.setPreferredSize(new Dimension(280, 110));
		jpInfo.setMaximumSize(new Dimension(280, 110));*/
		Border border_1 = BorderFactory.createLineBorder(Color.LIGHT_GRAY);
		jpInfo.setBorder(BorderFactory.createCompoundBorder(border_1, BorderFactory.createEmptyBorder(5, 5, 5, 5)));

		JLabel jlInfo = new JLabel("Obtener datos");

		jlInfo.setAlignmentX(Component.CENTER_ALIGNMENT);

		jtaInfo = new JTextArea(5, 10);
		jtaInfo.setFont(new Font("Verdana", Font.ITALIC, 11));
		jtaInfo.setForeground(Color.DARK_GRAY);
		jtaInfo.setMinimumSize(new Dimension(Integer.MIN_VALUE, jtaInfo.getMinimumSize().width));

		jpInfo.add(jlInfo);
		jpInfo.add(jtaInfo);
		/*----------------------------------------------------------------------------------------------------*/

		/*----------------------------------------------------------------------------------------------------*/
		// Agregamos los sub-paneles al contenedor:
		buttonPanelGeneral.add(jpInfo);
		buttonPanelGeneral.add(Box.createRigidArea(new Dimension(0, 5)));
		buttonPanelGeneral.add(getPanelMove());
		buttonPanelGeneral.add(Box.createRigidArea(new Dimension(0, 5)));
		// buttonPanelGeneral.add(Box.createRigidArea(new Dimension(2, 5)));
		buttonPanelGeneral.add(getPanelAttack());
		buttonPanelGeneral.add(Box.createRigidArea(new Dimension(0, 5)));
		/*----------------------------------------------------------------------------------------------------*/

		/*----------------------------------------------------------------------------------------------------*/
		// Colocamos los dos paneles en el principal.
		mainPanel.add(getBoardPanel(x, y));
		mainPanel.add(buttonPanelGeneral);
		jf.add(mainPanel);
		jf.setJMenuBar(menuBar);

		/*----------------------------------------------------------------------------------------------------*/

		/*----------------------------------------------------------------------------------------------------*/
		// Agregamos el panel principal:
		jf.pack();
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.setVisible(true);
	}
	/*------------------------------------------------------------------------------------------*/

	/*------------------------------------------------------------------------------------------*/
	/* 2 de 7 */
	// El segundo panel.
	public JPanel getBoardPanel(int x, int y) {
		celda = new Celda[x][y];

		jbArray = new JButton[x][y];

		/*----------------------------------------------------------------------------------------------------*/
		gl.setHgap(5);
		gl.setVgap(5);
		panel = new JPanel(gl);
		panel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		// panel = new JPanel();
		for (int i = 0; i < x; i++) {
			for (int j = 0; j < y; j++) {
				int bando = t.getCelda(i, j).getBando();
				int personaje = t.getCelda(i, j).getPersonaje();
				int arma = t.getCelda(i, j).getArma();
				int poder = t.getCelda(i, j).getPoder();

				/* Generamos una variable de tipo 'interface'; le asignamos una 'clase concreta'. */
				IPersonaje p = new Personaje(bando, personaje, arma, poder);

				/* Generamos un objeto 'Celda'; le colocamos como argumento la variable 'interface' que apunta a la 'clase concreta'. */
				c = new Celda(p);

				celda[i][j] = c;

				String d = Integer.toString(i) + " - " + Integer.toString(j);

				String g = c.showBoard();

				/*--------------------------------------------------*/
				jbCell = new JButton(g);

				jbArray[i][j] = jbCell;

				setButtonCollor(i, j);
				/*--------------------------------------------------*/
				jbCell.addActionListener(this);
				jbCell.setActionCommand("1");
				panel.add(jbCell);
			}
		}
		return panel;
	}
	/*------------------------------------------------------------------------------------------*/

	/*------------------------------------------------------------------------------------------*/
	public JPanel getPanelMove() {
		/* 4 de 6 */
		// El quinto panel - Mover personajes:
		JPanel buttonPanelMovimiento = new JPanel();

		buttonPanelMovimiento.setLayout(new BoxLayout(buttonPanelMovimiento, BoxLayout.Y_AXIS));
		buttonPanelMovimiento.setAlignmentX(Component.CENTER_ALIGNMENT);

		// 'Dimension': ancho - alto.
		buttonPanelMovimiento.setPreferredSize(new Dimension(240, 250));
		buttonPanelMovimiento.setMaximumSize(new Dimension(240, 250));
		/*buttonPanelMovimiento.setPreferredSize(new Dimension(280, 250));
		buttonPanelMovimiento.setMaximumSize(new Dimension(280, 250));*/

		Border border_2 = BorderFactory.createLineBorder(Color.LIGHT_GRAY);
		buttonPanelMovimiento.setBorder(BorderFactory.createCompoundBorder(border_2, BorderFactory.createEmptyBorder(5, 5, 5, 5)));

		JLabel jlInitial = new JLabel("Movimiento secuencial");
		jlInitial.setAlignmentX(Component.CENTER_ALIGNMENT);
		JLabel jlDestiny = new JLabel("Movimiento por salto");
		jlDestiny.setAlignmentX(Component.CENTER_ALIGNMENT);

		/*--------------------------------------------------------------------------------*/
		/* Sub-panel con datos sobre la posición iniciales. */
		JPanel jpInitial = new JPanel();
		jpInitial.setLayout(new BoxLayout(jpInitial, BoxLayout.X_AXIS));
		jpInitial.setAlignmentX(Component.CENTER_ALIGNMENT);

		jpInitial.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

		TitledBorder title_1;
		title_1 = BorderFactory.createTitledBorder("Actual");
		title_1.setTitleJustification(TitledBorder.CENTER);
		jpInitial.setBorder(title_1);

		/*----------*/
		JLabel jlXInitial = new JLabel("Y:");

		jtXPositionInitial = new JTextField(2);
		jtXPositionInitial.setMaximumSize(new Dimension(Integer.SIZE, jtXPositionInitial.getPreferredSize().height));
		/*----------*/

		/*----------*/
		JLabel jlYInitial = new JLabel("X:");

		jtYPositionInitial = new JTextField(2);
		jtYPositionInitial.setMaximumSize(new Dimension(Integer.SIZE, jtYPositionInitial.getPreferredSize().height));
		/*----------*/

		// jpInitial.add(Box.createRigidArea(new Dimension(5, 0)));
		jpInitial.add(jlXInitial);
		jpInitial.add(jtXPositionInitial);
		jpInitial.add(Box.createRigidArea(new Dimension(10, 0)));
		jpInitial.add(jlYInitial);
		jpInitial.add(jtYPositionInitial);
		/*--------------------------------------------------------------------------------*/

		/*--------------------------------------------------------------------------------*/
		/* Sub-panel de destino. */
		JPanel jpDestiny = new JPanel();
		jpDestiny.setLayout(new BoxLayout(jpDestiny, BoxLayout.X_AXIS));
		jpDestiny.setAlignmentX(Component.CENTER_ALIGNMENT);
		jpDestiny.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		TitledBorder title_2;
		title_2 = BorderFactory.createTitledBorder("Destino");
		title_2.setTitleJustification(TitledBorder.CENTER);
		jpDestiny.setBorder(title_2);

		JLabel jlXDestiny = new JLabel("Y:");

		jtXPositionDestiny = new JTextField(1);
		jtXPositionDestiny.setMaximumSize(new Dimension(Integer.SIZE, jtXPositionInitial.getPreferredSize().height));
		/*----------*/

		/*----------*/
		JLabel jlYDestiny = new JLabel("X:");

		jtYPositionDestiny = new JTextField(1);
		jtYPositionDestiny.setMaximumSize(new Dimension(Integer.SIZE, jtXPositionInitial.getPreferredSize().height));

		jpDestiny.add(jlXDestiny);
		jpDestiny.add(jtXPositionDestiny);
		jpDestiny.add(Box.createRigidArea(new Dimension(10, 0)));
		jpDestiny.add(jlYDestiny);
		jpDestiny.add(jtYPositionDestiny);
		/*--------------------------------------------------------------------------------*/

		jbMove = new JButton("Mover");
		jbMove.setAlignmentX(Component.CENTER_ALIGNMENT);

		jbMove.addActionListener(this);
		jbMove.setActionCommand("6");
		/*--------------------------------------------------------------------------------*/

		/*--------------------------------------------------------------------------------*/
		// Los íconos:
		/* 1 de 4 */
		ImageIcon down = new ImageIcon("src/down.png");

		// Para redimensionar íconos.
		/*Image img_1 = down.getImage();
		Image new_img_1 = img_1.getScaledInstance(20, 20, java.awt.Image.SCALE_SMOOTH);
		down = new ImageIcon(new_img_1);*/

		/* 2 de 4 */
		ImageIcon left = new ImageIcon("src/left.png");
		/*Image img_2 = left.getImage();
		Image new_img_2 = img_2.getScaledInstance(20, 20, java.awt.Image.SCALE_SMOOTH);
		left = new ImageIcon(new_img_2);*/

		/* 3 de 4 */
		ImageIcon right = new ImageIcon("src/right.png");
		/*Image img_3 = right.getImage();
		Image new_img_3 = img_3.getScaledInstance(20, 20, java.awt.Image.SCALE_SMOOTH);
		right = new ImageIcon(new_img_3);*/

		/* 4 de 4 */
		ImageIcon top = new ImageIcon("src/top.png");
		/*Image img_4 = right.getImage();
		Image new_img_4 = img_4.getScaledInstance(20, 20, java.awt.Image.SCALE_SMOOTH);
		top = new ImageIcon(new_img_4);*/

		JButton down_button = new JButton(down);
		down_button.setPreferredSize(new Dimension(30, 30));
		down_button.addActionListener(this);
		down_button.setActionCommand("5");

		JButton left_button = new JButton(left);
		left_button.setPreferredSize(new Dimension(30, 30));
		left_button.addActionListener(this);
		left_button.setActionCommand("3");

		JButton right_button = new JButton(right);
		right_button.setPreferredSize(new Dimension(30, 30));
		right_button.addActionListener(this);
		right_button.setActionCommand("4");

		JButton top_button = new JButton(top);
		top_button.setPreferredSize(new Dimension(30, 30));
		top_button.addActionListener(this);
		top_button.setActionCommand("2");

		JPanel cursorPanel = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();

		/*--------------------*/
		/* Con el panel medio. */
		gbc.fill = GridBagConstraints.CENTER;
		gbc.gridwidth = 1;
		gbc.gridy = 0;
		gbc.gridx = 1;
		cursorPanel.add(jlInitial, gbc);

		gbc.fill = GridBagConstraints.CENTER;
		// gbc.gridwidth = 1;
		gbc.gridy = 1;
		gbc.gridx = 1;
		cursorPanel.add(Box.createRigidArea(new Dimension(0, 5)), gbc);

		// 'gridy' = fila ('row'); 'gridx' = columna
		gbc.fill = GridBagConstraints.CENTER;
		gbc.gridwidth = 1;
		gbc.gridy = 2;
		gbc.gridx = 1;
		cursorPanel.add(top_button, gbc);

		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridwidth = 1;
		gbc.gridy = 3;
		gbc.gridx = 0;
		cursorPanel.add(left_button, gbc);

		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridy = 3;
		gbc.gridx = 1;
		cursorPanel.add(jpInitial, gbc);

		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridwidth = 1;
		gbc.gridy = 3;
		gbc.gridx = 2;
		cursorPanel.add(right_button, gbc);

		// Último botón.
		gbc.fill = GridBagConstraints.CENTER;
		gbc.gridwidth = 1;
		gbc.gridy = 4;
		gbc.gridx = 1;
		cursorPanel.add(down_button, gbc);

		gbc.fill = GridBagConstraints.CENTER;
		// gbc.gridwidth = 1;
		gbc.gridy = 5;
		gbc.gridx = 1;
		cursorPanel.add(Box.createRigidArea(new Dimension(0, 10)), gbc);

		// Última fila (vertical), 'gridy' 2.
		gbc.fill = GridBagConstraints.CENTER;
		// gbc.gridwidth = 1;
		gbc.gridy = 6;
		gbc.gridx = 1;
		cursorPanel.add(jlDestiny, gbc);

		gbc.fill = GridBagConstraints.CENTER;
		// gbc.gridwidth = 1;
		gbc.gridy = 7;
		gbc.gridx = 1;
		cursorPanel.add(Box.createRigidArea(new Dimension(0, 5)), gbc);

		gbc.fill = GridBagConstraints.HORIZONTAL;
		// gbc.gridwidth = 1;
		gbc.gridy = 8;
		gbc.gridx = 1;
		cursorPanel.add(jpDestiny, gbc);

		gbc.fill = GridBagConstraints.CENTER;
		// gbc.gridwidth = 1;
		gbc.gridy = 9;
		gbc.gridx = 1;
		cursorPanel.add(jbMove, gbc);
		/*--------------------------------------------------------------------------------*/

		/*--------------------------------------------------------------------------------*/
		// buttonPanelMovimiento.add(Box.createRigidArea(new Dimension(0, 5)));
		buttonPanelMovimiento.add(cursorPanel);
		/*----------------------------------------------------------------------------------------------------*/

		return buttonPanelMovimiento;
	}
	/*------------------------------------------------------------------------------------------*/

	/*------------------------------------------------------------------------------------------*/
	public JPanel getPanelAttack() {
		/* 5 de 6 */
		// El quinto panel - Mover personajes:
		JPanel buttonPanelAttack = new JPanel();
		buttonPanelAttack.setLayout(new BoxLayout(buttonPanelAttack, BoxLayout.Y_AXIS));
		buttonPanelAttack.setAlignmentX(Component.CENTER_ALIGNMENT);
		buttonPanelAttack.setPreferredSize(new Dimension(240, 180));
		buttonPanelAttack.setMaximumSize(new Dimension(240, 180));
		Border border_2 = BorderFactory.createLineBorder(Color.LIGHT_GRAY);
		buttonPanelAttack.setBorder(BorderFactory.createCompoundBorder(border_2, BorderFactory.createEmptyBorder(5, 5, 5, 5)));

		JLabel jlInitial = new JLabel("Posición actual");
		jlInitial.setAlignmentX(Component.CENTER_ALIGNMENT);
		JLabel jlDestiny = new JLabel("Destino de ataque");
		jlDestiny.setAlignmentX(Component.CENTER_ALIGNMENT);

		/*--------------------------------------------------------------------------------*/
		/* Sub-panel con datos sobre la posición iniciales. */
		JPanel jpInitial = new JPanel();
		jpInitial.setLayout(new BoxLayout(jpInitial, BoxLayout.X_AXIS));
		jpInitial.setBorder(BorderFactory.createEmptyBorder(5, 15, 15, 5));
		TitledBorder title_1;
		title_1 = BorderFactory.createTitledBorder("Actual");
		title_1.setTitleJustification(TitledBorder.CENTER);
		jpInitial.setBorder(title_1);

		/*----------*/
		JLabel jlXInitial = new JLabel("Y:");

		jtXPositionInitialAttack = new JTextField(3);
		jtXPositionInitialAttack.setMaximumSize(new Dimension(Integer.SIZE, jtXPositionInitialAttack.getPreferredSize().height));
		/*----------*/

		/*----------*/
		JLabel jlYInitial = new JLabel("X:");

		jtYPositionInitialAttack = new JTextField(3);
		jtYPositionInitialAttack.setMaximumSize(new Dimension(Integer.SIZE, jtYPositionInitialAttack.getPreferredSize().height));
		/*----------*/

		// jpInitial.add(Box.createRigidArea(new Dimension(10, 0)));
		jpInitial.add(jlXInitial);
		jpInitial.add(jtXPositionInitialAttack);
		jpInitial.add(Box.createRigidArea(new Dimension(10, 0)));
		jpInitial.add(jlYInitial);
		jpInitial.add(jtYPositionInitialAttack);
		/*--------------------------------------------------------------------------------*/

		/*--------------------------------------------------------------------------------*/
		/* Sub-panel con datos sobre la posición de destino. */
		JPanel jpDestiny = new JPanel();
		jpDestiny.setLayout(new BoxLayout(jpDestiny, BoxLayout.X_AXIS));
		jpDestiny.setBorder(BorderFactory.createEmptyBorder(5, 15, 15, 5));
		TitledBorder title_2;
		title_2 = BorderFactory.createTitledBorder("Destino");
		title_2.setTitleJustification(TitledBorder.CENTER);
		jpDestiny.setBorder(title_2);

		JLabel jlXDestiny = new JLabel("Y:");

		jtXPositionDestinyAttack = new JTextField(3);
		jtXPositionDestinyAttack.setMaximumSize(new Dimension(Integer.SIZE, jtXPositionDestinyAttack.getPreferredSize().height));
		/*----------*/

		/*----------*/
		JLabel jlYDestiny = new JLabel("X:");

		jtYPositionDestinyAttack = new JTextField(3);
		jtYPositionDestinyAttack.setMaximumSize(new Dimension(Integer.SIZE, jtYPositionDestinyAttack.getPreferredSize().height));

		// jpDestiny.add(Box.createRigidArea(new Dimension(10, 0)));
		jpDestiny.add(jlXDestiny);
		jpDestiny.add(jtXPositionDestinyAttack);
		jpDestiny.add(Box.createRigidArea(new Dimension(10, 0)));
		jpDestiny.add(jlYDestiny);
		jpDestiny.add(jtYPositionDestinyAttack);
		/*--------------------------------------------------------------------------------*/

		jbAttack = new JButton("Atacar");
		jbAttack.setAlignmentX(Component.LEFT_ALIGNMENT);

		jbAttack.addActionListener(this);
		jbAttack.setActionCommand("7");
		/*--------------------------------------------------------------------------------*/
		JPanel attackPanel = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();

		/* Panel. */
		gbc.fill = GridBagConstraints.CENTER;
		// gbc.gridwidth = 1;
		gbc.gridy = 0;
		gbc.gridx = 0;
		attackPanel.add(jlInitial, gbc);

		gbc.fill = GridBagConstraints.CENTER;
		// gbc.gridwidth = 1;
		gbc.gridy = 1;
		gbc.gridx = 1;
		attackPanel.add(Box.createRigidArea(new Dimension(0, 5)), gbc);

		gbc.fill = GridBagConstraints.HORIZONTAL;
		// gbc.gridwidth = 1;
		gbc.gridy = 2;
		gbc.gridx = 0;
		attackPanel.add(jpInitial, gbc);

		gbc.fill = GridBagConstraints.CENTER;
		// gbc.gridwidth = 1;
		gbc.gridy = 3;
		gbc.gridx = 1;
		attackPanel.add(Box.createRigidArea(new Dimension(0, 10)), gbc);

		gbc.fill = GridBagConstraints.CENTER;
		// gbc.gridwidth = 1;
		gbc.gridy = 4;
		gbc.gridx = 0;
		attackPanel.add(jlDestiny, gbc);

		gbc.fill = GridBagConstraints.CENTER;
		// gbc.gridwidth = 1;
		gbc.gridy = 5;
		gbc.gridx = 1;
		attackPanel.add(Box.createRigidArea(new Dimension(0, 5)), gbc);

		gbc.fill = GridBagConstraints.HORIZONTAL;
		// gbc.gridwidth = 1;
		gbc.gridy = 6;
		gbc.gridx = 0;
		attackPanel.add(jpDestiny, gbc);

		gbc.fill = GridBagConstraints.CENTER;
		// gbc.gridwidth = 1;
		gbc.gridy = 7;
		gbc.gridx = 0;
		attackPanel.add(jbAttack, gbc);
		/*--------------------------------------------------------------------------------*/

		buttonPanelAttack.add(attackPanel);
		/*----------------------------------------------------------------------------------------------------*/

		return buttonPanelAttack;
	}
	/*------------------------------------------------------------------------------------------*/

	/*------------------------------------------------------------------------------------------*/
	public JButton getButtonFromGrid(int x, int y) {
		for (int i = 0; i < xGrid; i++) {
			for (int j = 0; j < yGrid; j++) {
				if ((x == i) && (y == j)) {
					jbArray[x][y] = jbArray[i][j];
				}
			}
		}
		return jbArray[x][y];
	}
	/*------------------------------------------------------------------------------------------*/

	/*------------------------------------------------------------------------------------------*/
	public Celda getCeldaFromGrid(int x, int y) {
		for (int i = 0; i < xGrid; i++) {
			for (int j = 0; j < yGrid; j++) {
				if ((x == i) && (y == j)) {
					celda[x][y] = celda[i][j];
				}
			}
		}
		return celda[x][y];
	}
	/*------------------------------------------------------------------------------------------*/

	/*public void move(String xInitial, String yInitial, String xDestiny, String yDestiny)
	 {
	 String s = jtXPositionInitial.getText();
	 xInitial = s;
	 }*/
	public void setButtonCollor(int x, int y) {
		if (celda[x][y].getPersonaje() == 0) {
			Color first = new Color(156, 156, 156);
			// jbArray[x][y].setBackground(Color.LIGHT_GRAY);
			jbArray[x][y].setBackground(first);

			jbArray[x][y].setFont(new Font("Courier", Font.BOLD, 18));
			jbArray[x][y].setForeground(Color.WHITE);

			jbArray[x][y].setContentAreaFilled(false);
			jbArray[x][y].setOpaque(true);
			// jbArray[x][y].setBorderPainted(true);
			// jbArray[x][y].setBorder(new LineBorder(Color.GRAY, 1));
		} else if (celda[x][y].getBando() == 0) {
			Color second = new Color(54, 100, 139);
			// jbArray[x][y].setBackground(Color.BLUE);
			jbArray[x][y].setBackground(second);

			jbArray[x][y].setFont(new Font("Courier", Font.BOLD, 14));
			jbArray[x][y].setForeground(Color.WHITE);

			jbArray[x][y].setContentAreaFilled(false);
			jbArray[x][y].setOpaque(true);
			// jbArray[x][y].setBorderPainted(true);
			// jbArray[x][y].setBorder(new LineBorder(Color.BLUE, 1));
		} else if (celda[x][y].getBando() == 1) {
			Color third = new Color(165, 42, 42);
			// jbArray[x][y].setBackground(Color.RED);
			jbArray[x][y].setBackground(third);

			jbArray[x][y].setFont(new Font("Courier", Font.BOLD, 14));
			jbArray[x][y].setForeground(Color.WHITE);

			jbArray[x][y].setContentAreaFilled(false);
			jbArray[x][y].setOpaque(true);
			// jbArray[x][y].setBorderPainted(true);
			// jbArray[x][y].setBorder(new LineBorder(Color.RED, 1));
		}
	}

	/*--------------------------------------------------------------------------------*/
	public void writeFile() {
		try {
			FileWriter fw = new FileWriter("src/datosActualizados.txt");
			BufferedWriter bw = new BufferedWriter(fw);

			String s = "";
			s = Integer.toString(xGrid) + "," + Integer.toString(yGrid) + "\n";
			for (int i = 0; i < t.getDimension()[0]; i++) {
				for (int j = 0; j < t.getDimension()[1]; j++) {
					// String bando = Integer.toString(celda[i][j].getContent()[0]);
					String bando = Integer.toString(celda[i][j].getBando());
					// String personaje = Integer.toString(celda[i][j].getContent()[1]);
					String personaje = Integer.toString(celda[i][j].getPersonaje());
					// String arma = Integer.toString(celda[i][j].getContent()[2]);
					String arma = Integer.toString(celda[i][j].getArma());
					// String poder = Integer.toString(celda[i][j].getContent()[3]);
					String poder = Integer.toString(celda[i][j].getPoder());

					if (j == (t.getDimension()[1] - 1)) {
						s += bando + "," + personaje + "," + arma + "," + poder + "\n";
					} else {
						s += bando + "," + personaje + "," + arma + "," + poder + ",";
					}
				}
			}

			bw.write(s);

			bw.flush();
			bw.close();
		} catch (IOException ioe) {
			ioe.getMessage();
		}
	}

	// Opcional. Es igual que el anterior.
	// Para chequear por consola que se guarden los datos.
	public void writeFileConsole() {
		String s = "";
		s = Integer.toString(xGrid) + "," + Integer.toString(yGrid) + "\n";
		for (int i = 0; i < t.getDimension()[0]; i++) {
			for (int j = 0; j < t.getDimension()[1]; j++) {
				// String bando = Integer.toString(celda[i][j].getContent()[0]);
				String bando = Integer.toString(celda[i][j].getBando());
				// String personaje = Integer.toString(celda[i][j].getContent()[1]);
				String personaje = Integer.toString(celda[i][j].getPersonaje());
				// String arma = Integer.toString(celda[i][j].getContent()[2]);
				String arma = Integer.toString(celda[i][j].getArma());
				// String poder = Integer.toString(celda[i][j].getContent()[3]);
				String poder = Integer.toString(celda[i][j].getPoder());

				if (j == (t.getDimension()[1] - 1)) {
					s += bando + "," + personaje + "," + arma + "," + poder + "\n";
				} else {
					s += bando + "," + personaje + "," + arma + "," + poder + ",";
				}
			}
		}
		System.out.println(s);
	}

	public void attack(int xAttacker, int yAttacker, int xAttacked, int yAttacked) {
		if ((xAttacker > (xGrid - 1))
				|| (yAttacker > (yGrid - 1))
				|| (xAttacked > (xGrid - 1))
				|| (yAttacked > (yGrid - 1))
				|| (xAttacker < 0)
				|| (yAttacker < 0)
				|| (xAttacked < 0)
				|| (yAttacked < 0)) {
			JOptionPane.showMessageDialog(panel, "Revise las posiciones indicadas!", "Información", JOptionPane.WARNING_MESSAGE);
		} else if ((celda[xAttacker][yAttacker].getBando()) == (celda[xAttacked][yAttacked].getBando())) {
			// JOptionPane.showMessageDialog(null, "La celda de destino está ocupada!");
			JOptionPane.showMessageDialog(panel, "No ataque a su bando!", "Información", JOptionPane.WARNING_MESSAGE);
		} else if (celda[xAttacker][yAttacker].getPersonaje() == 0) {
			// JOptionPane.showMessageDialog(null, "La celda de origen está vacía!");
			JOptionPane.showMessageDialog(panel, "Una celda vacía no puede atacar!", "Información", JOptionPane.WARNING_MESSAGE);
		} else if (celda[xAttacked][yAttacked].getPersonaje() == 0) {
			// JOptionPane.showMessageDialog(null, "La celda de origen está vacía!");
			JOptionPane.showMessageDialog(panel, "No puede atacar a una celda vacía!", "Información", JOptionPane.WARNING_MESSAGE);
		} else if ((xAttacker == xAttacked) && (yAttacker == yAttacked)) {
			JOptionPane.showMessageDialog(panel, "No sea autodestructivo!", "Información", JOptionPane.WARNING_MESSAGE);
		} else if ((xAttacked > (xAttacker + 1)) || (yAttacked > (yAttacker + 1)) || (xAttacked < (xAttacker - 1)) || (yAttacked < (yAttacker - 1))) {
			JOptionPane.showMessageDialog(panel, "No se puede atacar desde esta distancia!", "Información", JOptionPane.WARNING_MESSAGE);
		} else if ((xAttacker != xAttacked) && (yAttacker != yAttacked)) {
			JOptionPane.showMessageDialog(panel, "No se puede atacar en diagonal!", "Información", JOptionPane.WARNING_MESSAGE);
		} else {
			int personajeAttacker = celda[xAttacker][yAttacker].getPersonaje();
			int armaAttacker = celda[xAttacker][yAttacker].getArma();
			int powerAttacker = celda[xAttacker][yAttacker].getPoder();
			int attackerCapacity = personajeAttacker * armaAttacker;

			int personajeAttacked = celda[xAttacked][yAttacked].getPersonaje();
			// int armaAttacked = celda[xAttacked][yAttacked].getArma();
			int powerAttacked = celda[xAttacked][yAttacked].getPoder();
			// int semiTotalAttacked = personajeAttacked * armaAttacked;

			int result = powerAttacked - attackerCapacity;
			celda[xAttacked][yAttacked].setPoder(result);
			JOptionPane.showMessageDialog(panel, "El poder del "
					+ celda[xAttacked][yAttacked].showBoard()
					+ " "
					+ celda[xAttacked][yAttacked].getBandoFinal(celda[xAttacked][yAttacked].getBando())
					+ " ahora es de "
					+ result
					+ "!", "Información", JOptionPane.WARNING_MESSAGE);
			if ((celda[xAttacked][yAttacked].getPersonaje() == 3) && (celda[xAttacked][yAttacked].getPoder() <= 0)) {
				JOptionPane.showMessageDialog(panel, "El juego ha finalizado! Ganó el bando " + (celda[xAttacker][yAttacker].getBandoFinal(celda[xAttacker][yAttacker].getBando())) + "!", "Información", JOptionPane.WARNING_MESSAGE);
				finalizada = true;
				return;
			}
			if (celda[xAttacked][yAttacked].getPoder() <= 0) {
				celda[xAttacked][yAttacked].setPersonaje(0);
				celda[xAttacked][yAttacked].setBando(0);
				celda[xAttacked][yAttacked].setArma(0);
				celda[xAttacked][yAttacked].setPoder(0);
				jbArray[xAttacked][yAttacked].setText(celda[xAttacked][yAttacked].showBoard());
				setButtonCollor(xAttacked, yAttacked);
			}
		}
	}

	/*------------------------------------------------------------------------------------------*/
	@Override
	public void actionPerformed(ActionEvent e) {
		int action = Integer.parseInt(e.getActionCommand());
		switch (action) {
			/*--------------------------------------------------*/
			case 1:
				jbCell = (JButton) e.getSource();
				for (int i = 0; i < xGrid; i++) {
					for (int j = 0; j < yGrid; j++) {
						if (jbArray[i][j] == jbCell) {
							String d = "Ubicación: " + Integer.toString(i) + " - " + Integer.toString(j);
							String t = celda[i][j].getInfo();
							jtaInfo.setText(d + "\n" + t);
							String row = Integer.toString(i);
							String column = Integer.toString(j);

							// Agregamos los datos a las posiciones iniciales de 'movimiento'.
							jtXPositionInitial.setText(row);
							jtYPositionInitial.setText(column);

							// Agregamos los datos a las posiciones iniciales de 'ataque'.
							jtXPositionInitialAttack.setText(row);
							jtYPositionInitialAttack.setText(column);
						}
					}
				}
				break;
			/*--------------------------------------------------*/

			/*--------------------------------------------------*/
			// Movimiento del botón hacia arriba.
			case 2:
				if (finalizada == false) {
					// Validamos que haya seleccionado una posición inicial:
					if ((jtXPositionInitial.getText().equals(""))
							|| (jtYPositionInitial.getText().equals(""))) {
						JOptionPane.showMessageDialog(panel, "Debe indicar una posición inicial!", "Información", JOptionPane.WARNING_MESSAGE);
						return;
					}
					int xInitialTop = Integer.parseInt(jtXPositionInitial.getText());
					int yInitialTop = Integer.parseInt(jtYPositionInitial.getText());

					// Obtenemos el 'personaje' y el 'bando' de esa ubicación:
					int personajeTop = celda[xInitialTop][yInitialTop].getPersonaje();
					int bandoTop = celda[xInitialTop][yInitialTop].getBando();
					int armaTop = celda[xInitialTop][yInitialTop].getArma();
					int poderTop = celda[xInitialTop][yInitialTop].getPoder();

					// Cambiamos los valores:
					int xDestinyTop = xInitialTop - 1;
					int yDestinyTop = yInitialTop;

					if ((celda[xInitialTop][yInitialTop].getPersonaje() == 3) && (celda[xInitialTop][yInitialTop].getPoder() <= 0)) {
						finalizada = true;
						JOptionPane.showMessageDialog(panel, "El juego terminó!", "Información", JOptionPane.WARNING_MESSAGE);
						return;
					}

					if ((xInitialTop > (xGrid - 1))
							|| (yInitialTop > (yGrid - 1))
							|| (xDestinyTop > (xGrid - 1))
							|| (yDestinyTop > (yGrid - 1))
							|| (xInitialTop < 0)
							|| (yInitialTop < 0)
							|| (xDestinyTop < 0)
							|| (yDestinyTop < 0)) {
						JOptionPane.showMessageDialog(panel, "Revise las posiciones indicadas!", "Información", JOptionPane.WARNING_MESSAGE);
						return;
					}
					if (celda[xInitialTop][yInitialTop].getPersonaje() == 0) {
						// JOptionPane.showMessageDialog(null, "La celda de origen está vacía!");
						JOptionPane.showMessageDialog(panel, "Una celda vacía no tiene a dónde ir!", "Información", JOptionPane.WARNING_MESSAGE);
						return;
					} else if (celda[xDestinyTop][yDestinyTop].getPersonaje() != 0) {
						// JOptionPane.showMessageDialog(null, "La celda de destino está ocupada!");
						JOptionPane.showMessageDialog(panel, "La celda de destino está ocupada!", "Información", JOptionPane.WARNING_MESSAGE);
						return;
					} else if (celda[xInitialTop][yInitialTop].getPoder() <= 0) {
						JOptionPane.showMessageDialog(panel, "No dispone de poder para moverse!", "Información", JOptionPane.WARNING_MESSAGE);
						return;
					} else {
						// Eliminamos el 'personaje' de la posición inicial:
						celda[xInitialTop][yInitialTop].setPersonaje(0);
						celda[xInitialTop][yInitialTop].setBando(0);
						celda[xInitialTop][yInitialTop].setArma(0);
						celda[xInitialTop][yInitialTop].setPoder(0);

						// Asignamos el 'personaje' y el 'bando' a la nueva posición:
						celda[xDestinyTop][yDestinyTop].setPersonaje(personajeTop);
						celda[xDestinyTop][yDestinyTop].setBando(bandoTop);
						celda[xDestinyTop][yDestinyTop].setArma(armaTop);
						celda[xDestinyTop][yDestinyTop].setPoder(poderTop - 1);

						// Cambiando los valores de los 'buttons', primero el 'String':
						jbArray[xInitialTop][yInitialTop].setText(celda[xInitialTop][yInitialTop].showBoard());
						jbArray[xDestinyTop][yDestinyTop].setText(celda[xDestinyTop][yDestinyTop].showBoard());

						// Cambiando los valores de los 'buttons', el color:
						setButtonCollor(xInitialTop, yInitialTop);
						setButtonCollor(xDestinyTop, yDestinyTop);

						// Actualizamos la información en los indicadores:
						jtXPositionInitial.setText(Integer.toString(xDestinyTop));
						jtYPositionInitial.setText(Integer.toString(yDestinyTop));
						jtXPositionInitialAttack.setText(Integer.toString(xDestinyTop));
						jtYPositionInitialAttack.setText(Integer.toString(yDestinyTop));
						String d = "Ubicación: " + Integer.toString(xDestinyTop) + " - " + Integer.toString(yDestinyTop);
						String t = celda[xDestinyTop][yDestinyTop].getInfo();
						jtaInfo.setText(d + "\n" + t);
					}
				} else {
					JOptionPane.showMessageDialog(panel, "La partida ha concluido!", "Información", JOptionPane.WARNING_MESSAGE);
					return;
				}
				break;
			/*--------------------------------------------------*/

			/*--------------------------------------------------*/
			// Movimiento del botón hacia la izquierda.
			case 3:
				if (finalizada == false) {
					// Validamos que haya seleccionado una posición inicial:
					if ((jtXPositionInitial.getText().equals(""))
							|| (jtYPositionInitial.getText().equals(""))) {
						JOptionPane.showMessageDialog(panel, "Debe indicar una posición inicial!", "Información", JOptionPane.WARNING_MESSAGE);
						return;
					}
					int xInitialLeft = Integer.parseInt(jtXPositionInitial.getText());
					int yInitialLeft = Integer.parseInt(jtYPositionInitial.getText());

					// Obtenemos el 'personaje' y el 'bando' de esa ubicación:
					int personajeLeft = celda[xInitialLeft][yInitialLeft].getPersonaje();
					int bandoLeft = celda[xInitialLeft][yInitialLeft].getBando();
					int armaLeft = celda[xInitialLeft][yInitialLeft].getArma();
					int poderLeft = celda[xInitialLeft][yInitialLeft].getPoder();

					// Tomamos los datos del para de 'JTextFields' de destino:
					int xDestinyLeft = xInitialLeft;
					int yDestinyLeft = yInitialLeft - 1;

					if ((celda[xInitialLeft][yInitialLeft].getPersonaje() == 3) && (celda[xInitialLeft][yInitialLeft].getPoder() <= 0)) {
						finalizada = true;
						JOptionPane.showMessageDialog(panel, "El juego terminó!", "Información", JOptionPane.WARNING_MESSAGE);
						return;
					}

					if ((xInitialLeft > (xGrid - 1))
							|| (yInitialLeft > (yGrid - 1))
							|| (xDestinyLeft > (xGrid - 1))
							|| (yDestinyLeft > (yGrid - 1))
							|| (xInitialLeft < 0)
							|| (yInitialLeft < 0)
							|| (xDestinyLeft < 0)
							|| (yDestinyLeft < 0)) {
						JOptionPane.showMessageDialog(panel, "Revise las posiciones indicadas!", "Información", JOptionPane.WARNING_MESSAGE);
						return;
					}
					if (celda[xInitialLeft][yInitialLeft].getPersonaje() == 0) {
						// JOptionPane.showMessageDialog(null, "La celda de origen está vacía!");
						JOptionPane.showMessageDialog(panel, "Una celda vacía no tiene a dónde ir!", "Información", JOptionPane.WARNING_MESSAGE);
						return;
					} else if (celda[xDestinyLeft][yDestinyLeft].getPersonaje() != 0) {
						// JOptionPane.showMessageDialog(null, "La celda de destino está ocupada!");
						JOptionPane.showMessageDialog(panel, "La celda de destino está ocupada!", "Información", JOptionPane.WARNING_MESSAGE);
						return;
					} else if (celda[xInitialLeft][yInitialLeft].getPoder() <= 0) {
						JOptionPane.showMessageDialog(panel, "No dispone de poder para moverse!", "Información", JOptionPane.WARNING_MESSAGE);
						return;
					} else {
						// Eliminamos el 'personaje' de la posición inicial:
						celda[xInitialLeft][yInitialLeft].setPersonaje(0);
						celda[xInitialLeft][yInitialLeft].setBando(0);
						celda[xInitialLeft][yInitialLeft].setArma(0);
						celda[xInitialLeft][yInitialLeft].setPoder(0);

						// Asignamos el 'personaje' y el 'bando' a la nueva posición:
						celda[xDestinyLeft][yDestinyLeft].setPersonaje(personajeLeft);
						celda[xDestinyLeft][yDestinyLeft].setBando(bandoLeft);
						celda[xDestinyLeft][yDestinyLeft].setArma(armaLeft);
						celda[xDestinyLeft][yDestinyLeft].setPoder(poderLeft - 1);

						// Cambiando los valores de los 'buttons', primero el 'String':
						jbArray[xInitialLeft][yInitialLeft].setText(celda[xInitialLeft][yInitialLeft].showBoard());
						jbArray[xDestinyLeft][yDestinyLeft].setText(celda[xDestinyLeft][yDestinyLeft].showBoard());

						// Cambiando los valores de los 'buttons', el color:
						setButtonCollor(xInitialLeft, yInitialLeft);
						setButtonCollor(xDestinyLeft, yDestinyLeft);

						// Actualizamos la información en los indicadores:
						jtXPositionInitial.setText(Integer.toString(xDestinyLeft));
						jtYPositionInitial.setText(Integer.toString(yDestinyLeft));
						jtXPositionInitialAttack.setText(Integer.toString(xDestinyLeft));
						jtYPositionInitialAttack.setText(Integer.toString(yDestinyLeft));
						String d = "Ubicación: " + Integer.toString(xDestinyLeft) + " - " + Integer.toString(yDestinyLeft);
						String t = celda[xDestinyLeft][yDestinyLeft].getInfo();
						jtaInfo.setText(d + "\n" + t);
					}
				} else {
					JOptionPane.showMessageDialog(panel, "La partida ha concluido!", "Información", JOptionPane.WARNING_MESSAGE);
					return;
				}
				break;
			/*--------------------------------------------------*/

			/*--------------------------------------------------*/
			// Movimiento del botón hacia la derecha.
			case 4:
				if (finalizada == false) {
					// Validamos que haya seleccionado una posición inicial:
					if ((jtXPositionInitial.getText().equals(""))
							|| (jtYPositionInitial.getText().equals(""))) {
						JOptionPane.showMessageDialog(panel, "Debe indicar una posición inicial!", "Información", JOptionPane.WARNING_MESSAGE);
						return;
					}
					int xInitialRight = Integer.parseInt(jtXPositionInitial.getText());
					int yInitialRight = Integer.parseInt(jtYPositionInitial.getText());

					// Obtenemos el 'personaje' y el 'bando' de esa ubicación:
					int personajeRight = celda[xInitialRight][yInitialRight].getPersonaje();
					int bandoRight = celda[xInitialRight][yInitialRight].getBando();
					int armaRight = celda[xInitialRight][yInitialRight].getArma();
					int poderRight = celda[xInitialRight][yInitialRight].getPoder();

					// Tomamos los datos del para de 'JTextFields' de destino:
					int xDestinyRight = xInitialRight;
					int yDestinyRight = yInitialRight + 1;

					if ((celda[xInitialRight][yInitialRight].getPersonaje() == 3) && (celda[xInitialRight][yInitialRight].getPoder() <= 0)) {
						finalizada = true;
						JOptionPane.showMessageDialog(panel, "El juego terminó!", "Información", JOptionPane.WARNING_MESSAGE);
						return;
					}

					if ((xInitialRight > (xGrid - 1))
							|| (yInitialRight > (yGrid - 1))
							|| (xDestinyRight > (xGrid - 1))
							|| (yDestinyRight > (yGrid - 1))
							|| (xInitialRight < 0)
							|| (yInitialRight < 0)
							|| (xDestinyRight < 0)
							|| (yDestinyRight < 0)) {
						JOptionPane.showMessageDialog(panel, "Revise las posiciones indicadas!", "Información", JOptionPane.WARNING_MESSAGE);
						return;
					}
					if (celda[xInitialRight][yInitialRight].getPersonaje() == 0) {
						// JOptionPane.showMessageDialog(null, "La celda de origen está vacía!");
						JOptionPane.showMessageDialog(panel, "Una celda vacía no tiene a dónde ir!", "Información", JOptionPane.WARNING_MESSAGE);
						return;
					} else if (celda[xDestinyRight][yDestinyRight].getPersonaje() != 0) {
						// JOptionPane.showMessageDialog(null, "La celda de destino está ocupada!");
						JOptionPane.showMessageDialog(panel, "La celda de destino está ocupada!", "Información", JOptionPane.WARNING_MESSAGE);
						return;
					} else if (celda[xInitialRight][yInitialRight].getPoder() <= 0) {
						JOptionPane.showMessageDialog(panel, "No dispone de poder para moverse!", "Información", JOptionPane.WARNING_MESSAGE);
						return;
					} else {
						// Eliminamos el 'personaje' de la posición inicial:
						celda[xInitialRight][yInitialRight].setPersonaje(0);
						celda[xInitialRight][yInitialRight].setBando(0);
						celda[xInitialRight][yInitialRight].setArma(0);
						celda[xInitialRight][yInitialRight].setPoder(0);

						// Asignamos el 'personaje' y el 'bando' a la nueva posición:
						celda[xDestinyRight][yDestinyRight].setPersonaje(personajeRight);
						celda[xDestinyRight][yDestinyRight].setBando(bandoRight);
						celda[xDestinyRight][yDestinyRight].setArma(armaRight);
						celda[xDestinyRight][yDestinyRight].setPoder(poderRight - 1);

						// Cambiando los valores de los 'buttons', primero el 'String':
						jbArray[xInitialRight][yInitialRight].setText(celda[xInitialRight][yInitialRight].showBoard());
						jbArray[xDestinyRight][yDestinyRight].setText(celda[xDestinyRight][yDestinyRight].showBoard());

						// Cambiando los valores de los 'buttons', el color:
						setButtonCollor(xInitialRight, yInitialRight);
						setButtonCollor(xDestinyRight, yDestinyRight);

						// Actualizamos la información en los indicadores:
						jtXPositionInitial.setText(Integer.toString(xDestinyRight));
						jtYPositionInitial.setText(Integer.toString(yDestinyRight));
						jtXPositionInitialAttack.setText(Integer.toString(xDestinyRight));
						jtYPositionInitialAttack.setText(Integer.toString(yDestinyRight));
						String d = "Ubicación: " + Integer.toString(xDestinyRight) + " - " + Integer.toString(yDestinyRight);
						String t = celda[xDestinyRight][yDestinyRight].getInfo();
						jtaInfo.setText(d + "\n" + t);
					}
				} else {
					JOptionPane.showMessageDialog(panel, "La partida ha concluido!", "Información", JOptionPane.WARNING_MESSAGE);
					return;
				}
				break;
			/*--------------------------------------------------*/

			/*--------------------------------------------------*/
			// Movimiento del botón hacia abajo.
			case 5:
				if (finalizada == false) {
					// Validamos que haya seleccionado una posición inicial:
					if ((jtXPositionInitial.getText().equals(""))
							|| (jtYPositionInitial.getText().equals(""))) {
						JOptionPane.showMessageDialog(panel, "Debe indicar una posición inicial!", "Información", JOptionPane.WARNING_MESSAGE);
						return;
					}
					int xInitialDown = Integer.parseInt(jtXPositionInitial.getText());
					int yInitialDown = Integer.parseInt(jtYPositionInitial.getText());

					// Obtenemos el 'personaje' y el 'bando' de esa ubicación:
					int personajeDown = celda[xInitialDown][yInitialDown].getPersonaje();
					int bandoDown = celda[xInitialDown][yInitialDown].getBando();
					int armaDown = celda[xInitialDown][yInitialDown].getArma();
					int poderDown = celda[xInitialDown][yInitialDown].getPoder();

					// Tomamos los datos del para de 'JTextFields' de destino:
					int xDestinyDown = xInitialDown + 1;
					int yDestinyDown = yInitialDown;

					if ((celda[xInitialDown][yInitialDown].getPersonaje() == 3) && (celda[xInitialDown][yInitialDown].getPoder() <= 0)) {
						finalizada = true;
						JOptionPane.showMessageDialog(panel, "El juego terminó!", "Información", JOptionPane.WARNING_MESSAGE);
						return;
					}

					if ((xInitialDown > (xGrid - 1))
							|| (yInitialDown > (yGrid - 1))
							|| (xDestinyDown > (xGrid - 1))
							|| (yDestinyDown > (yGrid - 1))
							|| (xInitialDown < 0)
							|| (yInitialDown < 0)
							|| (xDestinyDown < 0)
							|| (yDestinyDown < 0)) {
						JOptionPane.showMessageDialog(panel, "Revise las posiciones indicadas!", "Información", JOptionPane.WARNING_MESSAGE);
						return;
					}
					if (celda[xInitialDown][yInitialDown].getPersonaje() == 0) {
						// JOptionPane.showMessageDialog(null, "La celda de origen está vacía!");
						JOptionPane.showMessageDialog(panel, "Una celda vacía no tiene a dónde ir!", "Información", JOptionPane.WARNING_MESSAGE);
						return;
					} else if (celda[xDestinyDown][yDestinyDown].getPersonaje() != 0) {
						// JOptionPane.showMessageDialog(null, "La celda de destino está ocupada!");
						JOptionPane.showMessageDialog(panel, "La celda de destino está ocupada!", "Información", JOptionPane.WARNING_MESSAGE);
						return;
					} else if (celda[xInitialDown][yInitialDown].getPoder() <= 0) {
						JOptionPane.showMessageDialog(panel, "No dispone de poder para moverse!", "Información", JOptionPane.WARNING_MESSAGE);
						return;
					} else {
						// Eliminamos el 'personaje' de la posición inicial:
						celda[xInitialDown][yInitialDown].setPersonaje(0);
						celda[xInitialDown][yInitialDown].setBando(0);
						celda[xInitialDown][yInitialDown].setArma(0);
						celda[xInitialDown][yInitialDown].setPoder(0);

						// Asignamos el 'personaje' y el 'bando' a la nueva posición:
						celda[xDestinyDown][yDestinyDown].setPersonaje(personajeDown);
						celda[xDestinyDown][yDestinyDown].setBando(bandoDown);
						celda[xDestinyDown][yDestinyDown].setArma(armaDown);
						celda[xDestinyDown][yDestinyDown].setPoder(poderDown - 1);

						// Cambiando los valores de los 'buttons', primero el 'String':
						jbArray[xInitialDown][yInitialDown].setText(celda[xInitialDown][yInitialDown].showBoard());
						jbArray[xDestinyDown][yDestinyDown].setText(celda[xDestinyDown][yDestinyDown].showBoard());

						// Cambiando los valores de los 'buttons', el color:
						setButtonCollor(xInitialDown, yInitialDown);
						setButtonCollor(xDestinyDown, yDestinyDown);

						// Actualizamos la información en los indicadores:
						jtXPositionInitial.setText(Integer.toString(xDestinyDown));
						jtYPositionInitial.setText(Integer.toString(yDestinyDown));
						jtXPositionInitialAttack.setText(Integer.toString(xDestinyDown));
						jtYPositionInitialAttack.setText(Integer.toString(yDestinyDown));
						String d = "Ubicación: " + Integer.toString(xDestinyDown) + " - " + Integer.toString(yDestinyDown);
						String t = celda[xDestinyDown][yDestinyDown].getInfo();
						jtaInfo.setText(d + "\n" + t);
					}
				} else {
					JOptionPane.showMessageDialog(panel, "La partida ha concluido!", "Información", JOptionPane.WARNING_MESSAGE);
					return;
				}
				break;
			/*--------------------------------------------------*/

			/*--------------------------------------------------*/
			// Movimiento del botón de salto.
			case 6:
				if (finalizada == false) {
					// Validamos que haya seleccionado una posición de destino:
					if ((jtXPositionInitial.getText().equals(""))
							|| (jtYPositionInitial.getText().equals(""))
							|| (jtXPositionDestiny.getText().equals(""))
							|| (jtYPositionDestiny.getText().equals(""))) {
						JOptionPane.showMessageDialog(panel, "Debe completar todos los datos!", "Información", JOptionPane.WARNING_MESSAGE);
						return;
					}

					// Tomamos los datos del para de 'JTextFields' iniciales:
					int xInitial = Integer.parseInt(jtXPositionInitial.getText());
					int yInitial = Integer.parseInt(jtYPositionInitial.getText());

					// Obtenemos el 'personaje' y el 'bando' de esa ubicación:
					int personaje = celda[xInitial][yInitial].getPersonaje();
					int bando = celda[xInitial][yInitial].getBando();
					int arma = celda[xInitial][yInitial].getArma();
					int poder = celda[xInitial][yInitial].getPoder();

					// Tomamos los datos del para de 'JTextFields' de destino:
					int xDestiny = Integer.parseInt(jtXPositionDestiny.getText());
					int yDestiny = Integer.parseInt(jtYPositionDestiny.getText());

					if ((celda[xInitial][yInitial].getPersonaje() == 3) && (celda[xInitial][yInitial].getPoder() <= 0)) {
						finalizada = true;
						JOptionPane.showMessageDialog(panel, "El juego terminó!", "Información", JOptionPane.WARNING_MESSAGE);
						return;
					}

					if ((xInitial > (xGrid - 1))
							|| (yInitial > (yGrid - 1))
							|| (xDestiny > (xGrid - 1))
							|| (yDestiny > (yGrid - 1))
							|| (xInitial < 0)
							|| (yInitial < 0)
							|| (xDestiny < 0)
							|| (yDestiny < 0)) {
						JOptionPane.showMessageDialog(panel, "Revise las posiciones indicadas!", "Información", JOptionPane.WARNING_MESSAGE);
						return;
					}

					if (celda[xInitial][yInitial].getPersonaje() == 0) {
						// JOptionPane.showMessageDialog(null, "La celda de origen está vacía!");
						JOptionPane.showMessageDialog(panel, "Una celda vacía no tiene a dónde ir!", "Información", JOptionPane.WARNING_MESSAGE);
						return;
					} else if (celda[xDestiny][yDestiny].getPersonaje() != 0) {
						// JOptionPane.showMessageDialog(null, "La celda de destino está ocupada!");
						JOptionPane.showMessageDialog(panel, "La celda de destino está ocupada!", "Información", JOptionPane.WARNING_MESSAGE);
						return;
					} else if (poder < 5) {
						JOptionPane.showMessageDialog(panel, "No tiene suficiente poder para saltar!", "Información", JOptionPane.WARNING_MESSAGE);
						return;
					} else {
						// Eliminamos el 'personaje' de la posición inicial:
						celda[xInitial][yInitial].setPersonaje(0);
						celda[xInitial][yInitial].setBando(0);
						celda[xInitial][yInitial].setArma(0);
						celda[xInitial][yInitial].setPoder(0);

						// Asignamos el 'personaje' y el 'bando' a la nueva posición:
						celda[xDestiny][yDestiny].setPersonaje(personaje);
						celda[xDestiny][yDestiny].setBando(bando);
						celda[xDestiny][yDestiny].setArma(arma);
						celda[xDestiny][yDestiny].setPoder(poder - 5);

						// Cambiando los valores de los 'buttons', primero el 'String':
						jbArray[xInitial][yInitial].setText(celda[xInitial][yInitial].showBoard());
						jbArray[xDestiny][yDestiny].setText(celda[xDestiny][yDestiny].showBoard());

						// Cambiando los valores de los 'buttons', el color:
						setButtonCollor(xInitial, yInitial);
						setButtonCollor(xDestiny, yDestiny);

						// Actualizamos la información en los indicadores:
						jtXPositionInitial.setText(Integer.toString(xDestiny));
						jtYPositionInitial.setText(Integer.toString(yDestiny));
						jtXPositionInitialAttack.setText(Integer.toString(xDestiny));
						jtYPositionInitialAttack.setText(Integer.toString(yDestiny));
						String d = "Ubicación: " + Integer.toString(xDestiny) + " - " + Integer.toString(yDestiny);
						String t = celda[xDestiny][yDestiny].getInfo();
						jtaInfo.setText(d + "\n" + t);
					}
				} else {
					JOptionPane.showMessageDialog(panel, "La partida ha concluido!", "Información", JOptionPane.WARNING_MESSAGE);
					return;
				}
				// writeFile();
				break;
			/*--------------------------------------------------*/

			/*--------------------------------------------------*/
			// El botón de ataque.
			case 7:
				if (finalizada == false) {
					// Validamos que haya seleccionado una posición de destino:
					if ((jtXPositionInitialAttack.getText().equals(""))
							|| (jtYPositionInitialAttack.getText().equals(""))
							|| (jtXPositionDestinyAttack.getText().equals(""))
							|| (jtYPositionDestinyAttack.getText().equals(""))) {
						JOptionPane.showMessageDialog(panel, "Debe completar todos los datos!", "Información", JOptionPane.WARNING_MESSAGE);
						return;
					}
					// Tomamos los datos del para de 'JTextFields' iniciales:
					int xInitialAttack = Integer.parseInt(jtXPositionInitialAttack.getText());
					int yInitialAttack = Integer.parseInt(jtYPositionInitialAttack.getText());

					// Obtenemos el 'personaje' y el 'bando' de esa ubicación:
					// int personajeAttack = celda[xInitialAttack][yInitialAttack].getPersonaje();
					// int bandoAttack = celda[xInitialAttack][yInitialAttack].getBando();
					// Tomamos los datos del para de 'JTextFields' de destino:
					int xDestinyAttack = Integer.parseInt(jtXPositionDestinyAttack.getText());
					int yDestinyAttack = Integer.parseInt(jtYPositionDestinyAttack.getText());
					attack(xInitialAttack, yInitialAttack, xDestinyAttack, yDestinyAttack);

					String d = "Ubicación: " + Integer.toString(xDestinyAttack) + " - " + Integer.toString(yDestinyAttack);
					String t = celda[xDestinyAttack][yDestinyAttack].getInfo();
					jtaInfo.setText(d + "\n" + t);
				} else {
					JOptionPane.showMessageDialog(panel, "La partida ha concluido!", "Información", JOptionPane.WARNING_MESSAGE);
					return;
				}
				break;
			/*--------------------------------------------------*/

			/*--------------------------------------------------*/
			case 8:
				writeFileConsole();
				writeFile();
				break;
			/*--------------------------------------------------*/

			/*--------------------------------------------------*/
			case 9:
				System.exit(0);
				break;
			/*--------------------------------------------------*/

			/*--------------------------------------------------*/
			case 10:
				if (finalizada == false) {
					JOptionPane.showMessageDialog(panel, "El juego se encuentra activo!", "Información", JOptionPane.WARNING_MESSAGE);
				} else {
					JOptionPane.showMessageDialog(panel, "El juego ha finalizado!", "Información", JOptionPane.WARNING_MESSAGE);
				}
				break;
			/*--------------------------------------------------*/
		}
	}
}
