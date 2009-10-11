package es.ucm.si.dneb.domain;

import java.util.Date;

import javax.persistence.*;


@Entity
@NamedQueries({
	@NamedQuery(name="Descarga:dameNumeroDescargasDeUnaTarea",query="select count(*) from Descarga d where tarea=?"),
	@NamedQuery(name="Descarga:dameNumeroDescargasPendientesDeUnaTarea",query="select count(*) from Descarga d where tarea=? and finalizada=false")
})
@Table(name="DESCARGA")
public class Descarga {
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="ID_DESCARGA")
    private long idDescarga;
	
	private String ascensionRecta;
	
	private String declinacion;
	
	@ManyToOne
	@JoinColumn(name="Survey_ID_FK",nullable=false)
	private Survey survey;
	
	private boolean finalizada;
	
	private Date fechaFinalizacion;
	
	private String rutaFichero;
	
	@ManyToOne
    @JoinColumn(name="TAREA_ID_FK",nullable=false)
	private Tarea tarea;

	public void setIdDescarga(long idDescarga) {
		this.idDescarga = idDescarga;
	}

	public long getIdDescarga() {
		return idDescarga;
	}

	public void setAscensionRecta(String ascensionRecta) {
		this.ascensionRecta = ascensionRecta;
	}

	public String getAscensionRecta() {
		return ascensionRecta;
	}

	public void setDeclinacion(String declinacion) {
		this.declinacion = declinacion;
	}

	public String getDeclinacion() {
		return declinacion;
	}

	public void setTarea(Tarea tarea) {
		this.tarea = tarea;
	}

	public Tarea getTarea() {
		return tarea;
	}



	public void setFinalizada(boolean finalizada) {
		this.finalizada = finalizada;
	}

	public boolean isFinalizada() {
		return finalizada;
	}

	public void setFechaFinalizacion(Date fechaFinalizacion) {
		this.fechaFinalizacion = fechaFinalizacion;
	}

	public Date getFechaFinalizacion() {
		return fechaFinalizacion;
	}

	public void setRutaFichero(String rutaFichero) {
		this.rutaFichero = rutaFichero;
	}

	public String getRutaFichero() {
		return rutaFichero;
	}

	public void setSurvey(Survey survey) {
		this.survey = survey;
	}

	public Survey getSurvey() {
		return survey;
	}
	
	
}
