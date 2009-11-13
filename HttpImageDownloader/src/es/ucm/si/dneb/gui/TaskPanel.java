package es.ucm.si.dneb.gui;

import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.*;

import javax.swing.*;
import javax.swing.table.*;
/*
 * Created by JFormDesigner on Sun Oct 11 17:04:07 CEST 2009
 */

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import es.ucm.si.dneb.domain.Tarea;
import es.ucm.si.dneb.service.gestionTareas.ServicioGestionTareas;
import es.ucm.si.dneb.service.inicializador.ContextoAplicacion;



/**
 * @author Brainrain
 */
public class TaskPanel extends JPanel {
	
	private VentanaPcpal principal;
	private DefaultTableModel modelo;
	//private MyWorker worker;
	private ServicioGestionTareas servicioGestionTareas;
	
	public TaskPanel(VentanaPcpal pcpal) {
		initComponents();
		principal = pcpal;
		ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        servicioGestionTareas = (ServicioGestionTareas)ctx.getBean("servicioGestionTareas");
		rellenarTabla();
		//crearWorker();
	}
	
	private void rellenarTabla() {
		ApplicationContext ctx = ContextoAplicacion.getApplicationContext();
        ServicioGestionTareas servicioGestionTareas = (ServicioGestionTareas)ctx.getBean("servicioGestionTareas");
        
        ArrayList<Tarea> tareas = (ArrayList<Tarea>) servicioGestionTareas.getTareas();
        
        Object [] fila = new Object[tableTasks.getColumnCount()];
        for (Tarea tarea : tareas) {
        	fila[0] = tarea.getIdTarea();
        	fila[1] = tarea.getAlto();
        	fila[2] = tarea.getAncho();
        	fila[3] = tarea.getArInicial();
        	fila[4] = tarea.getArFinal();
        	fila[5] = tarea.getDecInicial();
        	fila[6] = tarea.getDecFinal();
        	fila[7] = tarea.getSolpamiento();
        	fila[8] = tarea.getFechaCreacion().toString();
        	fila[9] = tarea.getFechaUltimaActualizacion().toString();
        	fila[10] = tarea.getFormatoFichero().getDescipcion();
        	fila[11] = tarea.getRuta();
        	//fila[12] = tarea.getSurveys().get(0).getDescripcion();
        	//fila[13] = tarea.getSurveys().get(1).getDescripcion();
        	//fila[14] = servicioGestionTareas.obtenerPorcentajeCompletado(tarea.getIdTarea());
        	modelo.addRow(fila);
        	/*TableColumn column = tableTasks.getColumnModel().getColumn(14);
            column.setCellRenderer(new ProgressRenderer());*/
        }
	}

	private void buttonVolverActionPerformed(ActionEvent e) {
		// TODO add your code here
		JPanel vent = new MenuPanel(principal);
		principal.setSize(390, 300);
		principal.setLocationRelativeTo(null);
		principal.getContentPane().remove(0);
		principal.getContentPane().add(vent);
		principal.pack();
		vent.setVisible(true);
	}
	
	/*private void crearWorker() {
		if(worker == null){
			worker = new MyWorker();
			
			//Agrego un Listener para el cambio de la propiedad "progress"
			worker.addPropertyChangeListener(new PropertyChangeListener(){
				public void propertyChange(PropertyChangeEvent evt) {
					if ("progress".equals(evt.getPropertyName())) {
		                 //bar.setText(evt.getNewValue() + " %");
		            }
				}
			});
		}
		worker.execute();
	}*/

	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
		scrollPane = new JScrollPane();
		tableTasks = new JTable();
		buttonVolver = new JButton();

		//======== this ========

		//======== scrollPane ========
		{

			//---- tableTasks ----
			modelo = new DefaultTableModel(
				new Object[][] {
				},
				new String[] {
					"Iden", "Alto", "Ancho", "ARI", "ARF", "DECI", "DECF", "Solapamiento", "Fecha Creacion", "Fecha Actualizacion", "Formato", "Ruta", "Survey 1", "Survey 2", "Completada"
				}
			) {
				Class[] columnTypes = new Class[] {
						Object.class, Object.class, Object.class, Object.class, Object.class, Object.class, Object.class, Object.class, Object.class, Object.class, Object.class, Object.class, Object.class, Object.class, Object.class
				};
				boolean[] columnEditable = new boolean[] {
					false, false, false, false, false, false, false, false, false, false, false, false, false, false, false
				};
				@Override
				public Class<?> getColumnClass(int columnIndex) {
					return columnTypes[columnIndex];
				}
				@Override
				public boolean isCellEditable(int rowIndex, int columnIndex) {
					return columnEditable[columnIndex];
				}
			};
			tableTasks.setModel(modelo);
			{
				TableColumnModel cm = tableTasks.getColumnModel();
				cm.getColumn(0).setPreferredWidth(35);
				cm.getColumn(1).setPreferredWidth(35);
				cm.getColumn(2).setPreferredWidth(45);
				cm.getColumn(3).setPreferredWidth(35);
				cm.getColumn(4).setPreferredWidth(35);
				cm.getColumn(5).setPreferredWidth(40);
				cm.getColumn(6).setPreferredWidth(40);
				cm.getColumn(7).setPreferredWidth(80);
				cm.getColumn(8).setPreferredWidth(145);
				cm.getColumn(9).setPreferredWidth(145);
				cm.getColumn(10).setPreferredWidth(55);
				cm.getColumn(11).setPreferredWidth(107);
				cm.getColumn(12).setPreferredWidth(60);
				cm.getColumn(13).setPreferredWidth(60);
				cm.getColumn(14).setPreferredWidth(80);
			}
			tableTasks.setRowSelectionAllowed(false);
			tableTasks.setPreferredScrollableViewportSize(new Dimension(1000, 300));
			tableTasks.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
			DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
	    	tcr.setHorizontalAlignment(SwingConstants.CENTER);
	    	tableTasks.setAutoCreateRowSorter(true);
	    	tableTasks.getColumnModel().getColumn(0).setCellRenderer(tcr);
	    	tableTasks.getColumnModel().getColumn(1).setCellRenderer(tcr);
	    	tableTasks.getColumnModel().getColumn(2).setCellRenderer(tcr);
	    	tableTasks.getColumnModel().getColumn(3).setCellRenderer(tcr);
	    	tableTasks.getColumnModel().getColumn(4).setCellRenderer(tcr);
	    	tableTasks.getColumnModel().getColumn(5).setCellRenderer(tcr);
	    	tableTasks.getColumnModel().getColumn(6).setCellRenderer(tcr);
	    	tableTasks.getColumnModel().getColumn(7).setCellRenderer(tcr);
	    	tableTasks.getColumnModel().getColumn(8).setCellRenderer(tcr);
	    	tableTasks.getColumnModel().getColumn(9).setCellRenderer(tcr);
	    	tableTasks.getColumnModel().getColumn(10).setCellRenderer(tcr);
	    	tableTasks.getColumnModel().getColumn(12).setCellRenderer(tcr);
	    	tableTasks.getColumnModel().getColumn(13).setCellRenderer(tcr);
			scrollPane.setViewportView(tableTasks);
		}

		//---- buttonVolver ----
		buttonVolver.setText("VOLVER");
		buttonVolver.setFont(new Font("Arial", Font.PLAIN, 11));
		buttonVolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buttonVolverActionPerformed(e);
			}
		});

		GroupLayout layout = new GroupLayout(this);
		setLayout(layout);
		layout.setHorizontalGroup(
			layout.createParallelGroup()
				.addGroup(layout.createSequentialGroup()
					.addContainerGap()
					.addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
						.addComponent(buttonVolver, GroupLayout.PREFERRED_SIZE, 113, GroupLayout.PREFERRED_SIZE)
						.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 1000, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		layout.setVerticalGroup(
			layout.createParallelGroup()
				.addGroup(layout.createSequentialGroup()
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 260, Short.MAX_VALUE)
					.addGap(37, 37, 37)
					.addComponent(buttonVolver)
					.addGap(40, 40, 40))
		);
		// JFormDesigner - End of component initialization  //GEN-END:initComponents
	}

	// JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
	private JScrollPane scrollPane;
	private JTable tableTasks;
	private JButton buttonVolver;
	// JFormDesigner - End of variables declaration  //GEN-END:variables
	
	/*class MyWorker extends SwingWorker<Integer, Integer>{

		@Override
		protected Integer doInBackground() throws Exception {
			double porcentaje;
			while(true){
				porcentaje = servicioGestionTareas.obtenerPorcentajeCompletado(0);
				//bar.setValue((int) porcentaje);
				setProgress((int) porcentaje);
				if (porcentaje == 100.0)
					break;
			}
			
			return (int) porcentaje;
		}
		
		@Override
		protected void process() {
            modelo.setValueAt(, 0, 14);
        }
		
		@Override
		public void done(){
			try {
				lblFinish.setText(get());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}*/
	
	class ProgressRenderer extends DefaultTableCellRenderer {
		private JProgressBar bar = new JProgressBar(0, 100);
		
	    public ProgressRenderer() {
	        super();
	        setOpaque(true);
	        bar.setBorder(BorderFactory.createEmptyBorder(1,1,1,1));
	    }
	    public Component getTableCellRendererComponent(JTable table, Object value,
	                                                   boolean isSelected, boolean hasFocus,
	                                                   int row, int column) {
	        Integer i = (Integer)value;
	        String text = "Terminada";
	        if(i<100) {
	        	bar.setValue(i);
	            return bar;
	        }
	        super.getTableCellRendererComponent(table, text, isSelected, hasFocus, row, column);
	        return this;
	    }
	}
	
}