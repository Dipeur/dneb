import nom.tam.fits.BasicHDU;
import nom.tam.fits.FitsException;

public class LectorImageHDU extends LectorHDU {
	
	private short[][] arrayData;
	private float min, max, average, scale;
	private int width;
	private int height;
	private String filename;
	
	public LectorImageHDU(BasicHDU bhdu, String f) {
		super(bhdu.getHeader());
		try {
			arrayData = (short[][]) bhdu.getData().getData();
			min = Float.parseFloat(getValue("DATAMIN"));
			max = Float.parseFloat(getValue("DATAMAX"));
			scale = (max-min)/(2*32767);
			width = Integer.parseInt(getValue("NAXIS1"));
			height = Integer.parseInt(getValue("NAXIS2"));
			filename = f;
			
			arreglarMatriz();
		} catch (FitsException e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	// Getters and setters
	public short[][] getArrayData() {
		return arrayData;
	}

	public void setArrayData(short[][] arrayData) {
		this.arrayData = arrayData;
	}
	

	public float getMin() {
		return min;
	}

	public void setMin(float min) {
		this.min = min;
	}


	public float getMax() {
		return max;
	}

	public void setMax(float max) {
		this.max = max;
	}


	public float getAverage() {
		return average;
	}

	public void setAverage(float average) {
		this.average = average;
	}


	public float getScale() {
		return scale;
	}

	public void setScale(float scale) {
		this.scale = scale;
	}


	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}


	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}


	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}




	@Override
	public void printArray() throws FitsException {
		// Obtengo la matriz de pixeles (Data)
		
		// Recorro la matriz
		for(int i = 0; i < arrayData.length; i++){
			for(int j = 0; j < arrayData[i].length; j++)
				System.out.print(arrayData[i][j] + "   ");
			System.out.println();
		}
		System.out.println("\n");
	}	
	
	
	
	
	private void arreglarMatriz() {
		/* Simetrica de la matriz respecto del
		 * eje x. Es necesario hacer esto ya que
		 * en una imagen FITS, las coordenadas (0,0)
		 * pertenecen a la esquina inferior izquierda
		 * y por tanto la primera fila es la de abajo.
		 */
		int numFilas = arrayData.length;
		short temp;
		for (int i=0; i<numFilas/2; i++)
			for (int j=0; j<arrayData[i].length; j++){
				temp = arrayData[i][j];
				arrayData[i][j] = arrayData[numFilas - 1 - i][j];
				arrayData[numFilas - 1 - i][j] = temp;
			}
		
		
		/* Cambio la escala para que el valor del pixel se mueva
		 * entre 0 y 65535
		 */		
		for (int i=0; i<arrayData.length; i++)
			for (int j=0; j<arrayData[i].length; j++){
				arrayData[i][j] = (short) ((arrayData[i][j] - min) / scale);
			}
		
		// Actualizo min, max, average y scale por haber cambiado la escala
		max = (max - min) / scale;
		average = (average - min) / scale;
		min = (min - min) / scale;
		scale = (max-min)/(2*32767);
	}
}