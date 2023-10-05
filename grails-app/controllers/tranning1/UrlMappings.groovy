package tranning1

class UrlMappings {

    static mappings = {
        //endPoints Categoria
        delete "/$controller/$id(.$format)?"(action:"delete")
        get "/$controller(.$format)?"(action:"categoriasActivas")
        get "/$controller/$id(.$format)?"(action:"categoria")
        post "/$controller(.$format)?"(action:"save")
        put "/$controller/$id(.$format)?"(action:"update")
        patch "/$controller/$id(.$format)?"(action:"patch")

        //endPoints Autor
        delete "/$controller/$id(.$format)?"(action:"delete")
        get "/$controller(.$format)?"(action:"autoresActivos")
        get "/$controller/$id(.$format)?"(action:"autor")
        post "/$controller(.$format)?"(action:"save")
        put "/$controller/$id(.$format)?"(action:"update")
        patch "/$controller/$id(.$format)?"(action:"patch")

        //endPoints Libro
        delete "/$controller/$id(.$format)?"(action:"delete")
        get "/$controller(.$format)?"(action:"librosActivos")
        get "/$controller/$id(.$format)?"(action:"libro")
        post "/$controller(.$format)?"(action:"save")
        put "/$controller/$id(.$format)?"(action:"update")
        patch "/$controller/$id(.$format)?"(action:"patch")
        put "/$controller/updateCategorias/$id(.$format)?"(action:"categoriasLibro")
        get "/$controller/calGeneralLibro/$id(.$format)?"(action:"calGeneralLibro")
        post "/$controller/uploadImgLibro/$id(.$format)?"(action:"saveImg")

        //endPoints Calificacion
        delete "/$controller/$id(.$format)?"(action:"delete")
        get "/$controller(.$format)?"(action:"calificacionesActivas")
        get "/$controller/$id(.$format)?"(action:"calificacion")
        post "/$controller(.$format)?"(action:"save")
        put "/$controller/$id(.$format)?"(action:"update")
        patch "/$controller/$id(.$format)?"(action:"patch")

        "/"(controller: 'application', action:'index')
        "500"(view: '/error')
        "404"(view: '/notFound')
    }
}
