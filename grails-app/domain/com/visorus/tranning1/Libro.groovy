package com.visorus.tranning1

class Libro {
    String libro
    String descripcion
    String imagen = null
    String serie
    Autor autor
    boolean activo = true

    static constraints = {
        libro(nullable: false, maxSize: 80)
        descripcion(nullable: false, maxSize: 600)
        imagen(maxSize: 80, nullable: true)
        serie(nullable: false, unique: true, maxSize: 30)
        autor(nullable: true)
    }
}
