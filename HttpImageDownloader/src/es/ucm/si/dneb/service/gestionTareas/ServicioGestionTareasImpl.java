package es.ucm.si.dneb.service.gestionTareas;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.AbstractQueue;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;

import org.springframework.transaction.annotation.*;
import es.ucm.si.dneb.domain.Descarga;
import es.ucm.si.dneb.domain.FormatoFichero;
import es.ucm.si.dneb.domain.Survey;
import es.ucm.si.dneb.domain.Tarea;
import es.ucm.si.dneb.service.gestionHilos.GestorHilos;

@Service("servicioGestionTareas")
public class ServicioGestionTareasImpl implements ServicioGestionTareas {

	private static final Log LOG = LogFactory
			.getLog(ServicioGestionTareasImpl.class);

	@PersistenceContext
	EntityManager manager;
	
	@Resource
	private GestorHilos gestorHilos;


	public ServicioGestionTareasImpl() {
		
		//ApplicationContext ctx = new ClassPathXmlApplicationContext(
		//"applicationContext.xml");

		//GestorHilos gestorHilos = (GestorHilos) ctx.getBean("gestorHilos");
	}
	@Transactional(propagation = Propagation.REQUIRED)
	public void anadirTareasAlGestor(){
		
		gestorHilos.crearHilosParaTodasLasTareas(getTareas());
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void downloadImage(String survey, String ascensionRecta,
			String declinacion, String equinocio, String alto, String ancho,
			String formato, String compresion, String ruta) {

		LOG.info("Entrada en el m�todo downloadImage");
		final HttpClient httpclient = new DefaultHttpClient();

		final List<BasicNameValuePair> formparams = new ArrayList<BasicNameValuePair>(
				10);
		formparams.add(new BasicNameValuePair("v", survey));
		formparams.add(new BasicNameValuePair("r", ascensionRecta));
		formparams.add(new BasicNameValuePair("d", declinacion));
		formparams.add(new BasicNameValuePair("e", equinocio));
		formparams.add(new BasicNameValuePair("h", alto));
		formparams.add(new BasicNameValuePair("w", ancho));
		formparams.add(new BasicNameValuePair("f", formato));
		formparams.add(new BasicNameValuePair("c", compresion));
		formparams.add(new BasicNameValuePair("fov", "none"));
		formparams.add(new BasicNameValuePair("v3", ""));

		UrlEncodedFormEntity entity = null;
		try {
			entity = new UrlEncodedFormEntity(formparams, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			LOG.debug("Problema con la codificaci�n de los par�metros");
			e.printStackTrace();
		}

		LOG.info("Par�metros configurados correctamente");
		final HttpPost httppost = new HttpPost(
				"http://archive.stsci.edu/cgi-bin/dss_search");

		httppost.setEntity(entity);

		HttpResponse response = null;
		try {
			response = httpclient.execute(httppost);
		} catch (ClientProtocolException e) {
			LOG.debug("ERROR EJECUTANDO HTTPPOST");
			e.printStackTrace();
		} catch (IOException e) {
			LOG.debug("ERROR EJECUTANDO HTTPPOST");
			e.printStackTrace();
		}

		LOG.info("HTTPPOST EJECUTADO SATISFACTORIAMENTE");

		final HttpEntity resEnt = response.getEntity();
		if (resEnt != null) {
			BufferedInputStream bis = null;
			try {
				bis = new BufferedInputStream(resEnt.getContent());
			} catch (IllegalStateException e) {

				e.printStackTrace();
			} catch (IOException e) {

				e.printStackTrace();
			}

			FileOutputStream fos = null;
			try {
				/** TODO PONER LA RUTA DE MANERA CORRECTA **/
				fos = new FileOutputStream(new File(creaRuta(ruta, survey,
						ascensionRecta, declinacion, formato)));
			} catch (FileNotFoundException e) {
				LOG.debug("ERROR AL CREAR EL FICHERO");
				e.printStackTrace();
			}
			LOG.info("Se procede a la lecuta de datos");
			final byte[] buffer = new byte[1024];
			int cont = 0;
			try {
				cont = bis.read(buffer);
			} catch (IOException e) {
				LOG.debug("ERROR AL LEER DATOS DE LA URL");
				e.printStackTrace();
			}
			int total = cont;
			while (cont >= 0) {
				LOG.info("Leidos " + cont);
				try {
					fos.write(buffer, 0, cont);
				} catch (IOException e) {
					LOG.debug("ERROR AL ESCRIBIR DATOS EN EL FICHERO");
					e.printStackTrace();
				}
				try {
					cont = bis.read(buffer);
				} catch (IOException e) {
					LOG.debug("ERROR AL LEER DATOS DE LA URL");
					e.printStackTrace();
				}
				total += cont;
			}
			LOG.info("Fin de la lectura de datos");
			LOG.info("Leidos: " + total + " bytes");
			try {
				bis.close();
			} catch (IOException e) {
				LOG.debug("ERROR AL CERRAR EL FICHERO");
				e.printStackTrace();
			}
			try {
				fos.close();
			} catch (IOException e) {
				LOG.debug("ERROR AL CERRAR EL FICHERO");
				e.printStackTrace();
			}
			LOG.info("Fichero cerrado");
		} else {
			LOG.debug("PROBLEMA OBTENIENDO RESPONSE ENTITY");

		}

	}

	@Transactional(propagation = Propagation.REQUIRED)
	private String creaRuta(String rutaBase, String survey,
			String ascensionRecta, String declinacion, String formato) {

		String ruta = rutaBase;
		String nombreFichero = null;

		if (ruta != null) {
			if (rutaBase.charAt(rutaBase.length() - 1) != '/') {
				rutaBase = rutaBase + "/";
			}
			nombreFichero = "AR" + ascensionRecta + "DEC" + declinacion
					+ "SURV" + survey + "." + formato;
			ruta = rutaBase + nombreFichero;
			return ruta;

		} else {
			return null;
		}

	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void reanudarTarea(long tareaId) {

		Tarea tarea = manager.find(Tarea.class, tareaId);

		if (tarea.isActiva()) {
			throw new ServicioGestionTareasException(
					"ReanudarTarea: La tareas ya est� activa");
		}

		tarea.setActiva(true);

		GregorianCalendar calendar = new GregorianCalendar();
		Date fechaActual = calendar.getTime();
		tarea.setFechaUltimaActualizacion(fechaActual);

		procesoDescarga(tarea);

		gestorHilos.anadirHilo(tarea);
		gestorHilos.iniciarHilo(tarea.getIdTarea());

	}

	@Transactional(propagation = Propagation.REQUIRED)
	public double obtenerPorcentajeCompletado(long tareaId) {

		Tarea tarea = manager.find(Tarea.class, tareaId);
		Integer total = (Integer) manager.createNamedQuery(
				"Descarga:dameNumeroDescargasDeUnaTarea")
				.setParameter(1, tarea).getSingleResult();
		Integer pendientes = (Integer) manager.createNamedQuery(
				"Descarga:dameNumeroDescargasPendientesDeUnaTarea")
				.setParameter(1, tarea).getSingleResult();

		return (1 - (pendientes / total)) * 100;

	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void pararTarea(long tareaId) {

		Tarea tarea = manager.find(Tarea.class, tareaId);

		if (!tarea.isActiva()) {
			throw new ServicioGestionTareasException(
					"ReanudarTarea: La tareas ya est� parada");
		}
		tarea.setActiva(false);

		gestorHilos.interrumpirHilo(tareaId);

	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void procesoDescarga(Tarea tarea) {

	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<Survey> getAllSurveys() {
		List resultList = manager
				.createNamedQuery("Survey:dameTodosLosSurveys").getResultList();
		return resultList;
	}

	@Transactional(propagation = Propagation.SUPPORTS)
	public List<Tarea> getTareas() {
		return (List<Tarea>) manager.createNamedQuery("Tarea:DameTodasTareas")
				.getResultList();
	}

	@Transactional(propagation = Propagation.SUPPORTS)
	public List<FormatoFichero> getFormatosFichero() {
		return manager.createNamedQuery("FormatoFichero:dameTodosFormatos")
				.getResultList();
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void iniciarTarea(long tareaId) {

		Tarea tarea = manager.find(Tarea.class, tareaId);

		if (tarea.isActiva()) {
			throw new ServicioGestionTareasException(
					"IniciarTarea: La tareas ya est� activa");
		}

		tarea.setActiva(true);

		GregorianCalendar calendar = new GregorianCalendar();
		Date fechaActual = calendar.getTime();
		tarea.setFechaUltimaActualizacion(fechaActual);

		procesoDescarga(tarea);
		
		gestorHilos.iniciarHilo(tareaId);
	}
	
	public GestorHilos getGestorHilos() {
		return gestorHilos;
	}
	
	public void setGestorHilos(GestorHilos gestorHilos) {
		this.gestorHilos = gestorHilos;
	}

}
