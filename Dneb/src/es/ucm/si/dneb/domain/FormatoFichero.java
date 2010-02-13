package es.ucm.si.dneb.domain;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

@Entity
@NamedQueries({
	@NamedQuery(name="FormatoFichero:dameFormatoPorDescripcion",query="select f from FormatoFichero f where description=?"),
	@NamedQuery(name="FormatoFichero:dameTodosFormatos",query="select f from FormatoFichero f")
})
public class FormatoFichero {
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="ID_FORMATO_FICHERO")
    private long idFormatoFichero;
	
	private String alias;
	private String description;
	
	
	@OneToMany(mappedBy="formatoFichero")
	private Collection<Tarea> tareas;
	
	
	
	public void setAlias(String descipcion) {
		this.alias = descipcion;
	}

	public String getAlias() {
		return alias;
	}

	public void setIdFormatoFichero(long idFormatoFichero) {
		this.idFormatoFichero = idFormatoFichero;
	}

	public long getIdFormatoFichero() {
		return idFormatoFichero;
	}

	public void setTareas(Collection<Tarea> tareas) {
		this.tareas = tareas;
	}

	public Collection<Tarea> getTareas() {
		return tareas;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}
	
	
	
}
