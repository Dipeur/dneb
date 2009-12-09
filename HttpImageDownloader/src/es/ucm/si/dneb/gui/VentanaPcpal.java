package es.ucm.si.dneb.gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public class VentanaPcpal extends JFrame{
	

	private static final long serialVersionUID = -1633763922217208939L;
	String survey1, survey2, ari, deci, arf, decf, eq, alto, ancho, solapamiento, ruta;
	
	
	private JTabbedPane  pane= new JTabbedPane();
	
	public VentanaPcpal(){
		super("kk");
		
		//this.setLayout()
		this.getContentPane().setLayout(new BorderLayout());
	    initComponents();
	    
	    initIcons();
		
	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    
	    this.getContentPane().add(pane);
			    
	    pane.removeAll();
        
	    pane.setTabLayoutPolicy(JTabbedPane.WRAP_TAB_LAYOUT);
        
	    pane.add("DENEB",new BackgroundPanel(pane));
	    
	    
	    this.initTabComponent(pane.getTabCount()-1);
		
	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    setSize(200,200);
	    setLocationRelativeTo(null);
	    setVisible(true);

	}
	 private void initTabComponent(int i) {
	        pane.setTabComponentAt(i,
	                 new ButtonTabComponent(pane));
	}  
	
	private void initIcons(){
		
		ImageIcon taskIcon = new ImageIcon("images/TASKICON.JPG");
		this.menu4.setIcon(new ImageIcon("images/import.png"));
		this.menu2.setIcon(new ImageIcon("images/download_icon3.jpg"));
		this.menu3.setIcon(new ImageIcon("images/Config-icon.png"));
		this.menu1.setIcon(taskIcon);
		
		this.configBBDD.setIcon(new ImageIcon("images/database_icon.png"));
		this.configDownload.setIcon(new ImageIcon("images/downconfig (Custom).JPG"));
		this.crearDescarga.setIcon(new ImageIcon("images/download_icon4.png"));
		this.crearNuevaTarea.setIcon(new ImageIcon("images/addtask.png"));
		
		this.gestionarDescargasConfig.setIcon(new ImageIcon("images/downconfig (Custom).JPG"));
		this.gestorTareas.setIcon(new ImageIcon("images/vista-taskmanager-icon.png"));
		this.importBBDD.setIcon(new ImageIcon("images/Database Insert.jpg"));
		this.importXML.setIcon(new ImageIcon("images/xml_icon_gif.gif"));		
	}


	private void crearNuevaTareaActionPerformed(ActionEvent e) {		
		pane.add("Nueva Tarea",new SurveyPanel(this) );
		this.initTabComponent(pane.getTabCount()-1);
		this.pack();
		setVisible(true);
	}

	private void gestorTareasActionPerformed(ActionEvent e) {
		JPanel vent = new TaskPanel(this);
		pane.addTab("Gestor de tareas", vent);
		this.initTabComponent(pane.getTabCount()-1);
		this.pack();
		setVisible(true);
	}

	private void crearDescargaActionPerformed(ActionEvent e) {
		JPanel vent = new CreateNewDownload(pane);
		pane.addTab("Nueva Descarga", vent);
		this.initTabComponent(pane.getTabCount()-1);
		this.pack();
		setVisible(true);
	}

	private void gestionarDescargasConfigActionPerformed(ActionEvent e) {
		DefaultDownloadSettingsConfig config = new DefaultDownloadSettingsConfig(this);
		pane.addTab("Configurar Descargas", config);
		this.initTabComponent(pane.getTabCount()-1);
		this.pack();
		setVisible(true);
	}

	private void configBBDDActionPerformed(ActionEvent e) {
		DataBaseConfig dataBaseConfig = new DataBaseConfig(this);
		pane.addTab("Configurar Base de Datos", dataBaseConfig);
		this.initTabComponent(pane.getTabCount()-1);
		this.pack();
		setVisible(true);
	}

	private void configDownloadActionPerformed(ActionEvent e) {
		DefaultDownloadSettingsConfig config = new DefaultDownloadSettingsConfig(this);
		pane.addTab("Configurar Descargas", config);
		this.initTabComponent(pane.getTabCount()-1);
		this.pack();
		setVisible(true);
	}

	private void importBBDDActionPerformed(ActionEvent e) {
		// TODO add your code here
	}

	private void importXMLActionPerformed(ActionEvent e) {
		// TODO add your code here
	}


	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
		menuBar1 = new JMenuBar();
		menu1 = new JMenu();
		crearNuevaTarea = new JMenuItem();
		gestorTareas = new JMenuItem();
		menu2 = new JMenu();
		crearDescarga = new JMenuItem();
		gestionarDescargasConfig = new JMenuItem();
		menu3 = new JMenu();
		configBBDD = new JMenuItem();
		configDownload = new JMenuItem();
		menu4 = new JMenu();
		importBBDD = new JMenuItem();
		importXML = new JMenuItem();

		//======== this ========
		setIconImage(null);
		setTitle("DNEB (DETECCI\u00d3N DE NUEVAS ESTRELLAS BINARIAS)");
		//Container contentPane = getContentPane();
		//contentPane.setLayout(null);

		//======== menuBar1 ========
		{

			//======== menu1 ========
			{
				menu1.setIcon(null);
				menu1.setText("TAREAS");

				//---- crearNuevaTarea ----
				crearNuevaTarea.setText("CREAR UNA NUEVA TAREA");
				crearNuevaTarea.setIcon(null);
				crearNuevaTarea.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						crearNuevaTareaActionPerformed(e);
					}
				});
				menu1.add(crearNuevaTarea);

				//---- gestorTareas ----
				gestorTareas.setText("GESTOR DE TAREAS");
				gestorTareas.setIcon(null);
				gestorTareas.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						gestorTareasActionPerformed(e);
					}
				});
				menu1.add(gestorTareas);
			}
			menuBar1.add(menu1);

			//======== menu2 ========
			{
				menu2.setText("DESCARGAS");
				menu2.setIcon(null);

				//---- crearDescarga ----
				crearDescarga.setText("CREAR UNA DESCARGA");
				crearDescarga.setIcon(null);
				crearDescarga.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						crearDescargaActionPerformed(e);
					}
				});
				menu2.add(crearDescarga);

				//---- gestionarDescargasConfig ----
				gestionarDescargasConfig.setText("CREAR CONFIGURARION DE DESCARGA");
				gestionarDescargasConfig.setIcon(null);
				gestionarDescargasConfig.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						gestionarDescargasConfigActionPerformed(e);
					}
				});
				menu2.add(gestionarDescargasConfig);
			}
			menuBar1.add(menu2);

			//======== menu3 ========
			{
				menu3.setIcon(null);
				menu3.setText("CONFIGURACION");

				//---- configBBDD ----
				configBBDD.setText("CONFIGURAR CONEXI\u00d3N DE BASE DE DATOS");
				configBBDD.setIcon(null);
				configBBDD.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						configBBDDActionPerformed(e);
					}
				});
				menu3.add(configBBDD);

				//---- configDownload ----
				configDownload.setText("CREAR CONFIGURACION DE DESCARGA");
				configDownload.setIcon(null);
				configDownload.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						configDownloadActionPerformed(e);
					}
				});
				menu3.add(configDownload);
			}
			menuBar1.add(menu3);

			//======== menu4 ========
			{
				menu4.setText("IMPORTAR DATOS");
				menu4.setIcon(null);

				//---- importBBDD ----
				importBBDD.setText("DESDE BBDD");
				importBBDD.setIcon(null);
				importBBDD.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						importBBDDActionPerformed(e);
					}
				});
				menu4.add(importBBDD);

				//---- importXML ----
				importXML.setText("DESDE XML");
				importXML.setIcon(null);
				importXML.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						importXMLActionPerformed(e);
					}
				});
				menu4.add(importXML);
			}
			menuBar1.add(menu4);
		}
		setJMenuBar(menuBar1);
		
		/*{ // compute preferred size
			Dimension preferredSize = new Dimension();
			for(int i = 0; i < contentPane.getComponentCount(); i++) {
				Rectangle bounds = contentPane.getComponent(i).getBounds();
				preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
				preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
			}
			Insets insets = contentPane.getInsets();
			preferredSize.width += insets.right;
			preferredSize.height += insets.bottom;
			contentPane.setMinimumSize(preferredSize);
			contentPane.setPreferredSize(preferredSize);
		}
		setSize(400, 300);
		setLocationRelativeTo(getOwner());*/
		// JFormDesigner - End of component initialization  //GEN-END:initComponents
	}

	// JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
	private JMenuBar menuBar1;
	private JMenu menu1;
	private JMenuItem crearNuevaTarea;
	private JMenuItem gestorTareas;
	private JMenu menu2;
	private JMenuItem crearDescarga;
	private JMenuItem gestionarDescargasConfig;
	private JMenu menu3;
	private JMenuItem configBBDD;
	private JMenuItem configDownload;
	private JMenu menu4;
	private JMenuItem importBBDD;
	private JMenuItem importXML;
	// JFormDesigner - End of variables declaration  //GEN-END:variables
}