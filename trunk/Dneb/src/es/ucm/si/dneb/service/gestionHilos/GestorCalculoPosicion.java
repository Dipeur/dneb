package es.ucm.si.dneb.service.gestionHilos;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import es.ucm.si.dneb.domain.Tarea;
import es.ucm.si.dneb.domain.TareaProcesamiento;
import es.ucm.si.dneb.service.inicializador.ContextoAplicacion;

@Service("gestorCalculoPosicion")
public class GestorCalculoPosicion implements GestorHilos<TareaProcesamiento>{
	
	
	private Map<Long,Hilo> hilos;
	
	private static final Log LOG = LogFactory
	.getLog(GestorCalculoPosicion.class);
	
	public GestorCalculoPosicion(){
		
		hilos = new HashMap<Long, Hilo>();
		
	}

	@Override
	public void anadirHilo(TareaProcesamiento tareaProc) {
		
		LOG.debug("A�ADIR HILO:" + tareaProc.getIdTarea());

		ApplicationContext ctx = ContextoAplicacion.getApplicationContext();
		EjecutorCalculoPosicion gestor = (EjecutorCalculoPosicion) ctx.getBean("ejecutorCalculoPosicion");
		//gestor.set(tareaProc);
		//gestor.setIdTarea(tareaProc.getIdTarea());

		Hilo hilo = new Hilo(gestor);
		hilos.put(tareaProc.getIdTarea(), hilo);

		//LOG.info("HILO CREADO:" + tarea.getIdTarea());
		
	}

	@Override
	public void eleminarHilo(Long id) {
		
		Hilo hilo = hilos.get(id);

		if (hilo.isInterrupted() == false) {

			LOG.info("GESTOR C�CULO POSICI�N" + hilo.continuar());

			hilo.parar();

			LOG.info("GESTOR C�CULO POSICI�N" + hilo.continuar());

			hilo.interrupt();
		}

		hilos.remove(id);

		LOG.info("GESTOR C�CULO POSICI�N : ELIMINADO EL HILO:" + id);
		
	}

	@Override
	public void iniciarHilo(Long idHilo) {
		
		Hilo hilo = hilos.get(idHilo);

		hilo.start();

		LOG.info("GESTOR INICIADO EL HILO:" + idHilo);
		
	}

	@Override
	public void interrumpirHilo(Long idHilo) {
		
		Hilo hilo = hilos.get(idHilo);

		//Tarea tarea = hilo.getEjecutor().getTarea();

		this.eleminarHilo(idHilo);

		//this.anadirHilo(tarea);

		LOG.info("GESTOR   nombreGestor INTERRUMPIDO EL HILO:" + idHilo);
		
	}

	public Map<Long, Hilo> getHilos() {
		return hilos;
	}

	public void setHilos(Map<Long, Hilo> hilos) {
		this.hilos = hilos;
	}

	public static Log getLog() {
		return LOG;
	}

	
	
	
	

	

}
