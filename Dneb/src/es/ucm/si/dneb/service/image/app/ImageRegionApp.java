package es.ucm.si.dneb.service.image.app;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.ColorModel;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferInt;
import java.awt.image.Raster;
import java.awt.image.RenderedImage;
import java.awt.image.SampleModel;
import java.awt.image.renderable.ParameterBlock;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.media.jai.InterpolationBilinear;
import javax.media.jai.InterpolationNearest;
import javax.media.jai.JAI;
import javax.media.jai.PlanarImage;
import javax.media.jai.RasterFactory;
import javax.media.jai.RenderedImageAdapter;
import javax.media.jai.TiledImage;
import javax.swing.GroupLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.filechooser.FileFilter;

import nom.tam.fits.BasicHDU;
import nom.tam.fits.Fits;
import nom.tam.util.ArrayFuncs;
import es.ucm.si.dneb.gui.VentanaPcpal;
import es.ucm.si.dneb.service.image.segmentation.LectorImageHDU;
import es.ucm.si.dneb.service.image.segmentation.StarFinder;


public class ImageRegionApp extends JPanel implements AdjustmentListener, MouseListener, MouseMotionListener {
	
	private JScrollPane jsp1, jsp2;
	private JLabel label;
	private JPanel panel;
	private JButton buttonAbrir, buttonSalir, buttonBuscar, buttonZoomMas, buttonZoomMenos, buttonGirar;
	
	private DisplayImageWithRegions display1, display2;
	private PlanarImage input1, input2, scaledIm1, scaledIm2, rotatedIm1, rotatedIm2;
	private int scale; // la escala va del 10 al 1000%
	private int angle; // el angulo va del 0 al 360
	private LectorImageHDU l1, l2;
	
	private VentanaPcpal principal;
	private int position;
	
	public ImageRegionApp(VentanaPcpal pcpal,int position) {
		principal = pcpal;
		this.position=position;
		initComponents();
		
		//initIcons();
		//this.principal=principal;
		//this.rellenarModel();
	}
	
	public ImageRegionApp(JFrame parent) {
		scale = 100;
		angle = 0;
		display1 = null;
		display2 = null;
		input1 = null;
		input2 = null;
		scaledIm1 = null;
		scaledIm2 = null;
		rotatedIm1 = null;
		rotatedIm2 = null;
		l1 = null;
		l2 = null;
		initComponents();
		
		parent.setSize(500, 650);
		parent.setExtendedState(JFrame.MAXIMIZED_BOTH);  
		parent.setLocationRelativeTo(null);
		parent.setMinimumSize(new Dimension(105, 330));
	    
		parent.add(this);
		parent.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		parent.setVisible(true);
	}
	
	private void initComponents() {
		
		setLayout(new GridBagLayout());
	    GridBagConstraints c = new GridBagConstraints();
	    
	    
	    jsp1 = new JScrollPane();
	    c.gridx = 0;
	    c.gridy = 0;
	    c.gridwidth = 1;
	    c.gridheight = 1;
	    c.fill = GridBagConstraints.BOTH;
	    c.weighty = 1.0;
	    c.weightx = 1.0;
	    add(jsp1, c);
	    
	    
	    jsp2 = new JScrollPane();
	    c.gridx = 1;
	    c.gridy = 0;
	    add(jsp2, c);
	    
	    
	    label = new JLabel();
	    c.gridx = 0;
	    c.gridy = 1;
	    c.gridwidth = 3;
	    c.weighty = 0.0;
	    add(label, c);
	    
	    
	    panel = new JPanel();
	    GroupLayout layout = new GroupLayout(panel);
	    panel.setLayout(layout);
	    
	    layout.setAutoCreateGaps(true);
	    layout.setAutoCreateContainerGaps(true);
	    
	    buttonAbrir = new JButton();
	    Icon icon = new ImageIcon("images/abrir.gif");
	    buttonAbrir.setIcon(icon);
	    buttonAbrir.setToolTipText("Abrir imagenes");
	    buttonAbrir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buttonAbrirActionPerformed(e);
			}
		});
	    
	    buttonBuscar = new JButton();
	    icon = new ImageIcon("images/buscar.gif");
	    buttonBuscar.setIcon(icon);
	    buttonBuscar.setToolTipText("Buscar estrellas en la imagen y marcarlas con cuadrados");
	    buttonBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buttonBuscarActionPerformed(e);
			}
		});
	    
	    buttonSalir = new JButton();
	    icon = new ImageIcon("images/salir.gif");
	    buttonSalir.setIcon(icon);
	    buttonSalir.setToolTipText("Salir del programa");
	    buttonSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
	    
	    buttonZoomMas = new JButton();
	    icon = new ImageIcon("images/zoom+.gif");
	    buttonZoomMas.setIcon(icon);
	    buttonZoomMas.setToolTipText("Acercar la imagen");
	    buttonZoomMas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buttonZoomMasActionPerformed(e);
			}
		});
	    
	    buttonZoomMenos = new JButton();
	    icon = new ImageIcon("images/zoom-.gif");
	    buttonZoomMenos.setIcon(icon);
	    buttonZoomMenos.setToolTipText("Alejar la imagen");
	    buttonZoomMenos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buttonZoomMenosActionPerformed(e);
			}
		});
	    
	    buttonGirar = new JButton();
	    icon = new ImageIcon("images/girar.gif");
	    buttonGirar.setIcon(icon);
	    buttonGirar.setToolTipText("Girar la imagen");
	    buttonGirar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buttonGirarActionPerformed(e);
			}
		});
	    
	    layout.setHorizontalGroup(
		   layout.createSequentialGroup()
		      .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
		           .addComponent(buttonAbrir)
		           .addComponent(buttonBuscar)
		           .addComponent(buttonZoomMas)
		           .addComponent(buttonZoomMenos)
		           .addComponent(buttonGirar)
		           .addComponent(buttonSalir))
		);
	    
		layout.setVerticalGroup(
		   layout.createSequentialGroup()
		      .addComponent(buttonAbrir)
		      .addComponent(buttonBuscar)
		      .addComponent(buttonZoomMas)
		      .addComponent(buttonZoomMenos)
		      .addComponent(buttonGirar)
		      .addComponent(buttonSalir)
		);
		
	    c.gridx = 2;
	    c.gridy = 0;
	    c.gridwidth = 1;
	    c.gridheight = 1;
	    c.fill = GridBagConstraints.NONE;
	    c.weightx = 0.0;
	    add(panel, c);
	    
	    
	    // Horizontal scroll bar of the first image.
	    jsp1.getHorizontalScrollBar().addAdjustmentListener(this);
	    // Vertical scroll bar of the first image.
	    jsp1.getVerticalScrollBar().addAdjustmentListener(this);
	    // Horizontal scroll bar of the second image.
	    jsp2.getHorizontalScrollBar().addAdjustmentListener(this);
	    // Vertical scroll bar of the second image.
	    jsp2.getVerticalScrollBar().addAdjustmentListener(this);
	}
	
	private void buttonAbrirActionPerformed(ActionEvent e) {
		FiltreExtensible filtre = new FiltreExtensible("Imagenes");
		filtre.addExtension("dss_search.fits");
		JFileChooser fc = new JFileChooser(".");
		fc.addChoosableFileFilter(filtre);
		
		String file1, file2;
		int retval = fc.showOpenDialog(this);
		try {
			if (retval == JFileChooser.APPROVE_OPTION) {
				file1 = fc.getSelectedFile().toString();
				retval = fc.showOpenDialog(this);
				if (retval == JFileChooser.APPROVE_OPTION) {
					file2 = fc.getSelectedFile().toString();
					
					Fits imagenFITS = new Fits(new File(file1));			
					BasicHDU imageHDU = imagenFITS.getHDU(0);
					l1 = new LectorImageHDU(imageHDU, file1);
					//input1 = createPlanarImage(l1.getArrayData(), l1.getWidth(), l1.getHeight(), 1);
					input1 = JAI.create("fileload", "dss_search.gif");
					scaledIm1 = input1;
					rotatedIm1 = input1;
					display1 = new DisplayImageWithRegions(input1);
					display1.addMouseMotionListener(this);
				    display1.addMouseListener(this);
					jsp1.setViewportView(display1);
					
					imagenFITS = new Fits(new File(file2));
					imageHDU = imagenFITS.getHDU(0);
					l2 = new LectorImageHDU(imageHDU, file1);
					//input2 = createPlanarImage(l2.getArrayData(), l2.getWidth(), l2.getHeight(), 1);
					input2 = JAI.create("fileload", "dss_search.gif");
					scaledIm2 = input2;
					rotatedIm2 = input2;
					display2 = new DisplayImageWithRegions(input2);
					display2.addMouseMotionListener(this);
					display2.addMouseListener(this);
					jsp2.setViewportView(display2);
				}
			}
		} catch(Exception ex) {
        	JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
	}
	
	private PlanarImage createPlanarImage(int[][] arrayData, int width, int height, int numBands) {
		int len = width * height;

		// create a float sample model
		SampleModel sampleModel = RasterFactory.createBandedSampleModel(DataBuffer.TYPE_INT, width, height, numBands);

		// create a compatible ColorModel
		ColorModel colourModel = PlanarImage.createColorModel(sampleModel);
		
		// create a TiledImage using the int SampleModel
		TiledImage tiledImage = new TiledImage(0, 0, width, height, 0, 0, sampleModel, colourModel);
		
		// create a DataBuffer from the int[][] array
		int[] arrayDataAplanado = (int[]) ArrayFuncs.flatten(arrayData);
		DataBufferInt dataBuffer = new DataBufferInt(arrayDataAplanado, len);
		
		// create a Raster
		Raster raster = RasterFactory.createWritableRaster(sampleModel, dataBuffer, new Point(0, 0));

		// set the TiledImage data to that of the Raster
		tiledImage.setData(raster);
		
		RenderedImageAdapter img = new RenderedImageAdapter((RenderedImage)tiledImage);

		return img;
	}
	
	private void buttonZoomMasActionPerformed(ActionEvent e) {
		if (input1 != null && input2 != null && scale >= 10 && scale <= 1000) {
			scale += 10;
			angle = 0;
	
		    ParameterBlock pb = new ParameterBlock();
		    pb.addSource(rotatedIm1);
		    pb.add(scale/100f);
		    pb.add(scale/100f);
		    pb.add(0.0F);
		    pb.add(0.0F);
		    pb.add(new InterpolationNearest());
		    scaledIm1 = JAI.create("scale", pb);
		    display1.set(scaledIm1);
		    pb.addSource(rotatedIm2);
		    scaledIm2 = JAI.create("scale", pb);
		    display2.set(scaledIm2);
		}
	}
	
	private void buttonZoomMenosActionPerformed(ActionEvent e) {
		if (input1 != null && input2 != null && scale >= 10 && scale <= 1000) {
			scale -= 10;
			angle = 0;
			
			ParameterBlock pb = new ParameterBlock();
		    pb.addSource(rotatedIm1);
		    pb.add(scale/100f);
		    pb.add(scale/100f);
		    pb.add(0.0F);
		    pb.add(0.0F);
		    pb.add(new InterpolationNearest());
		    scaledIm1 = JAI.create("scale", pb);
		    display1.set(scaledIm1);
		    pb.addSource(rotatedIm2);
		    scaledIm2 = JAI.create("scale", pb);
		    display2.set(scaledIm2);
		}
	}
	
	private void buttonBuscarActionPerformed(ActionEvent e) {
		StarFinder sf1 = new StarFinder(), sf2 = new StarFinder();
		float umbral = 10000;
		float brilloEstrella = 40000;
		sf1.buscarEstrellas(l1, brilloEstrella, umbral);
		sf1.printRectStars(input1, display1);
		sf2.buscarEstrellas(l2, brilloEstrella, umbral);
		sf2.printRectStars(input2, display2);
		jsp1.repaint();
		jsp2.repaint();
	}
	
	private void buttonGirarActionPerformed(ActionEvent e) {
		if (input1 != null && input2 != null) {
			angle = (angle - 90) % 360;
			scale = 100;
			float angleRad = (float)Math.toRadians(angle);
			
			// Gets the rotation center.
			float centerX; float centerY;
			centerX = scaledIm1.getWidth()/2f;
			centerY = scaledIm1.getHeight()/2f;
			
			// Rotates the original image.
			ParameterBlock pb = new ParameterBlock();
			pb.addSource(scaledIm1);
			pb.add(centerX);
			pb.add(centerY);
			pb.add(angleRad);
			pb.add(new InterpolationBilinear());
			rotatedIm1 = JAI.create("rotate", pb);
			display1.set(rotatedIm1);
			pb.addSource(scaledIm2);
			rotatedIm2 = JAI.create("rotate", pb);
			display2.set(rotatedIm2);
		}
	}

	@Override
	public void adjustmentValueChanged(AdjustmentEvent e) {
		
		// If the horizontal bar of the first image was changed...
	    if (e.getSource() == jsp1.getHorizontalScrollBar()) {
	      // We change the position of the horizontal bar of the second image.
	      jsp2.getHorizontalScrollBar().setValue(e.getValue());
	    }
	    
	    // If the vertical bar of the first image was changed...
	    if (e.getSource() == jsp1.getVerticalScrollBar()) {
	      // We change the position of the vertical bar of the second image.
	      jsp2.getVerticalScrollBar().setValue(e.getValue());
	    }
	    
	    // If the horizontal bar of the second image was changed...
	    if (e.getSource() == jsp2.getHorizontalScrollBar()) {
	      // We change the position of the horizontal bar of the first image.
	      jsp1.getHorizontalScrollBar().setValue(e.getValue());
	    }
	    
	    // If the vertical bar of the second image was changed...
	    if (e.getSource() == jsp2.getVerticalScrollBar()) {
	      // We change the position of the vertical bar of the first image.
	      jsp1.getVerticalScrollBar().setValue(e.getValue());
	    }
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		String pos = "(" + e.getX() + "," + e.getY() + ") ";
		if (e.getSource() == display1)
			label.setText(pos + display1.getPixelInfo());
		if (e.getSource() == display2)
			label.setText(pos + display2.getPixelInfo());
	}
	
	@Override
	public void mousePressed(MouseEvent e) {}
	
	@Override
	public void mouseClicked(MouseEvent e) {}
	
	@Override
	public void mouseDragged(MouseEvent e) {}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}

	@Override
	public void mouseReleased(MouseEvent e) {}


	class FiltreExtensible extends FileFilter{
		
		   // descripci�n y extensiones aceptadas por el filtro
		   private String description;
		   private List<String> extensions;
		 
		   // constructor a partir de la descripci�n
		   public FiltreExtensible(String description){
		      this.description = description;
		      this.extensions = new ArrayList<String>();
		   }
		 
		   public String getDescription(){
			  if (description == null)
				  return "No description";
			  
		      StringBuffer buffer = new StringBuffer(description);
		      buffer.append(" (");
		      for(String extension : extensions){
		         buffer.append(extension).append(" ");
		      }
		      return buffer.append(")").toString();
		   }
		 
		   public void setDescription(String description){
		      this.description = description;
		   }
		 
		   public void addExtension(String extension){
		      if(extension == null){
		         throw new NullPointerException("La extensi�n no puede ser null.");
		      }
		      extensions.add(extension);
		   }
		 
		   public void removeExtension(String extension){
		      extensions.remove(extension);
		   }
		 
		   public void clearExtensions(){
		      extensions.clear();
		   }
		 
		   public List<String> getExtensions(){
		      return extensions;
		   }
		
			@Override
			public boolean accept(File file) {
				if(file.isDirectory() || extensions.size()==0) { 
			         return true; 
			    } 
			    String nombreFichero = file.getName().toLowerCase(); 
			    for(String extension : extensions){
			       if(nombreFichero.endsWith(extension)){
			          return true;
			       }
			    }
			    return false;
			}
	}
}