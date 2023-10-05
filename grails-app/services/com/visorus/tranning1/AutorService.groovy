package com.visorus.tranning1

import grails.core.GrailsApplication
import grails.gorm.transactions.Transactional
import org.grails.web.json.JSONObject

@Transactional
class AutorService {

    GrailsApplication grailsApplication

    //Obtener Autores
    List<Autor> getAutoresActivos() throws Exception {
        return Autor.findAll('from Autor where activo = true')
    }


    //Obtener un Autor
    Autor get(long id) throws Exception {
        Autor a = find(id)
        if(a == null) {
            throw new Exception("no existe el autor")
        }
        return a
    }


    Autor find(long id) throws Exception {
        Autor autor = Autor.get(id)
        return autor
    }

    //Crear un autor
    Autor create(JSONObject json) throws Exception {
        Autor autor = new Autor()

        build(json, autor)

        return save(autor)
    }

    //Actualizar una autor
    Autor update(long id, JSONObject json) throws Exception {
        Autor autor = get(id)
        build(json, autor)
        return save(autor)
    }


    Autor build(JSONObject json, Autor autor)  throws Exception {
        autor.nombre = json.optString("nombre", autor.nombre)
        autor.biografia = json.optString("biografia", autor.biografia)
        autor.fechaNacimiento = new Date(json.optLong("fechaNacimiento", autor.fechaNacimiento ? autor.fechaNacimiento?.getTime() : 0))
        if (json.optString("activo")) {
            autor.activo = json.optBoolean("activo", autor.activo)
        }

        return autor
    }

    Autor save(Autor autor) throws Exception {
        if(autor.validate() && autor.save(flush: true))
            return autor
        throw new Exception("No se pudo guardar")
    }

    //Eliminar autor
    Autor delete(long id) throws Exception{
        Autor autor = get(id)
        return autor.delete()
    }

}
