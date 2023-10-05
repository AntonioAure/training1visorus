package com.visorus.tranning1

import ch.qos.logback.core.util.FileUtil
import grails.core.GrailsApplication
import grails.gorm.transactions.Transactional
import grails.web.context.ServletContextHolder
import groovy.sql.GroovyRowResult
import groovy.sql.Sql
import org.grails.web.json.JSONArray
import org.grails.web.json.JSONObject
import org.springframework.web.multipart.MultipartHttpServletRequest

import javax.servlet.http.HttpServletRequest


@Transactional
class LibroService {

    AutorService autorService
    CategoriaService categoriaService
    Sql sqlS
    CalificacionService calificacionService
    GrailsApplication grailsApplication

    //Obtener Libros
    List<Libro> getLibrosActivos() throws Exception {
        return Libro.findAll('from Libro where activo = true')
    }

    //Obtener un Libro
    Libro get(long id) throws Exception {
        Libro libro = find(id)

        if(libro == null) {
            throw new Exception("no existe el libro")
        }
        return libro
    }

    //Obtener la calificacion general de un libro
    Map getLibroCalificacionGeneral(long id) throws Exception {
        Libro libro = get(id)
        GroovyRowResult promedioResult = sqlS.firstRow("SELECT AVG(calificacion) as calificacion from calificacion where libro_id=?",[libro.id])
        if (promedioResult == null)
            return ["calificacion": 0]

        return promedioResult
    }

    Libro find(long id) throws Exception {
        Libro libro = Libro.get(id)
        return libro
    }

    //Crear un libro
    Libro create(JSONObject json) throws Exception {
        Libro libro = new Libro()

        build(json, libro)

        return save(libro)
    }

    //Actualizar una libro
    Libro update(long id, JSONObject json) throws Exception {
        Libro libro = get(id)
        build(json, libro)
        return save(libro)
    }

    Libro build(JSONObject json, Libro libro)  throws Exception {
        libro.libro = json.optString("libro", libro.libro)
        libro.descripcion = json.optString("descripcion", libro.descripcion)
        libro.serie = json.optString("serie", libro.serie)
        libro.autor = autorService.get(json.optJSONObject("autor").optLong("id"))
        if (json.optString("imagen")) {
            libro.imagen = json.optString("imagen", libro.imagen)
        }
        if (json.optString("activo")) {
            libro.activo = json.optBoolean("activo", libro.activo)
        }

        return libro
    }

    Libro save(Libro libro) throws Exception {
        if(libro.validate() && libro.save(flush: true))
            return libro
        throw new Exception("No se pudo guardar")
    }

    //Eliminar libro
    Libro delete(long id) throws Exception{
        Libro libro = get(id)
        return libro.delete()
    }

    //insertar categorias de los libros
    Libro updateCategoriasLibro(long id,  JSONObject json) throws Exception {
        Libro libro = this.get(id)
        JSONArray array = json.optJSONArray("categorias")
        LibroCategoria libroCategoria

        for(int i; i<array.length(); i++) {
            JSONObject jsonNew = new JSONObject("idLibro":libro.id, "idCategoria":array[i].get("id"))
            createLibroCategoria(jsonNew)
        }

        return libro
    }

    //crear los registros en la tabla LibroCategoria
    LibroCategoria createLibroCategoria(JSONObject json) throws Exception {
        LibroCategoria libroCategoria = new LibroCategoria()

        buildLiBroCategoria(json, libroCategoria)

        return saveLibroCategoria(libroCategoria)
    }

    LibroCategoria buildLiBroCategoria(JSONObject json, LibroCategoria libroCategoria)  throws Exception {
        libroCategoria.libro = this.get(json.optLong("idLibro"))
        libroCategoria.categoria = categoriaService.get(json.optLong("idCategoria"))

        return libroCategoria
    }

    LibroCategoria saveLibroCategoria(LibroCategoria libroCategoria) throws Exception {
        int numRegistros= contadorCategorias(libroCategoria)
        if (numRegistros>0) {
            return null
            //throw new Exception("sss")
        }

        if (libroCategoria.validate() && libroCategoria.save(flush: true))
            return libroCategoria

        throw new Exception("error al insertar las categorias")
    }

    //Valida si existen registros duplicados en LibroCategoria
    int contadorCategorias(LibroCategoria libroCategoria) throws Exception{
        return LibroCategoria.countByCategoriaAndLibro(libroCategoria.categoria, libroCategoria.libro)
    }

    //Cargar la Imagen del libro, y actualizar el libro en la bd
    Libro uploadImgLibro(long id, MultipartHttpServletRequest request){
        Libro libro = this.get(id)

        def file = request.getFile("file")
        String name= libro.id+"img"+file.getOriginalFilename()

        def serveletContext = ServletContextHolder.servletContext
        def storagePath = serveletContext.getRealPath("\\img" )

        def storagePathDirectory = new File( storagePath )

        if( !storagePathDirectory.exists() ){
            println("creating directory ${storagePath}")
            if(storagePathDirectory.mkdirs()){
                println "SUCCESS"
            }else{
                println "FAILED"
            }
        }

        // Store file
        if(!file.isEmpty() && name.length()<23){
            File fileAntiguo = new File(libro.imagen)
            if (fileAntiguo.exists())
                fileAntiguo.delete()

            file.transferTo( new File("${storagePath}\\${name}") )
            println("Saved File: ${storagePath}\\${name}")
            libro.imagen="${storagePath}\\${name}"
            return libro.save(flush: true)
        }else{
            println "File: ${file.inspect()} was empty"
            return null
        }
    }

}
