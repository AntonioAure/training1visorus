package com.visorus.tranning1

import grails.core.GrailsApplication
import grails.gorm.transactions.Transactional
import org.grails.web.json.JSONObject

@Transactional
class CategoriaService {

    GrailsApplication grailsApplication

    //Obtener categorias activas
    List<Categoria> getCategoriasActivas() throws Exception {
        return Categoria.findAll('from Categoria where activo = true')
    }


    //Obtener una categoria
    Categoria get(long id) throws Exception {
        Categoria c = find(id)
        return c
    }


    Categoria find(long id) throws Exception {
        Categoria c = Categoria.get(id)

        return c
    }

    //Crear una categoria
    Categoria create(JSONObject json) throws Exception {
        Categoria categoria = new Categoria()

        build(json, categoria)

        return save(categoria)
    }

    //Actualizar una categoria
    Categoria update(long id, JSONObject json) throws Exception {
        Categoria categoria = get(id)
        build(json, categoria)
        return save(categoria)
    }


    Categoria build(JSONObject json, Categoria categoria)  throws Exception {
        categoria.codigo = json.optString("codigo", categoria.codigo)
        categoria.nombre = json.optString("nombre", categoria.nombre)
        categoria.descripcion = json.optString("descripcion", categoria.descripcion)
        if (json.optString("activo")) {
            categoria.activo = json.optBoolean("activo", categoria.activo)
        }

        return categoria
    }

    Categoria save(Categoria categoria) throws Exception {
        if(categoria.validate() && categoria.save(flush: true))
            return categoria
        throw new Exception("No se pudo guatdar")
    }

    //Eliminar categoria
    Categoria delete(long id) throws Exception{
        Categoria categoria = get(id)
        return categoria.delete()
    }

}
