package com.visorus.tranning1

import grails.core.GrailsApplication
import grails.gorm.transactions.Transactional
import org.grails.web.json.JSONObject

@Transactional
class CalificacionService {

    LibroService libroService
    GrailsApplication grailsApplication

    //Obtener calificaciones activas
    List<Calificacion> getCalificacionesActivas() throws Exception {
        return Calificacion.findAll('from Calificacion where activo = true')
    }


    //Obtener una calificacion
    Calificacion get(long id) throws Exception {
        Calificacion c = find(id)
        return c
    }


    Calificacion find(long id) throws Exception {
        Calificacion c = Calificacion.get(id)

        return c
    }

    //Crear una calificacion
    Calificacion create(JSONObject json) throws Exception {
        Calificacion calificacion = new Calificacion()

        build(json, calificacion)

        return save(calificacion)
    }

    //Actualizar una calificacion
    Calificacion update(long id, JSONObject json) throws Exception {
        Calificacion calificacion = get(id)
        build(json, calificacion)
        return save(calificacion)
    }


    Calificacion build(JSONObject json, Calificacion calificacion)  throws Exception {
        calificacion.resena = json.optString("resena", calificacion.resena)
        calificacion.calificacion = json.optInt("calificacion", calificacion.calificacion)
        calificacion.libro =  libroService.get(json.optJSONObject("libro").optLong("id"))
        if (json.optString("activo")) {
            calificacion.activo = json.optBoolean("activo", calificacion.activo)
        }

        return calificacion
    }

    Calificacion save(Calificacion calificacion) throws Exception {
        if(calificacion.validate() && calificacion.save(flush: true))
            return calificacion
        throw new Exception("No se pudo guatdar")
    }

    //Eliminar calificacion
    Calificacion delete(long id) throws Exception{
        Calificacion calificacion = get(id)
        return calificacion.delete()
    }
}
