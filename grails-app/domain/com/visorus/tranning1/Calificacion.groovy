package com.visorus.tranning1

class Calificacion {
    String resena
    int calificacion
    Libro libro
    boolean activo = true

    static constraints = {
        resena(nullable: false, maxSize: 2000)
        calificacion(nullable: false)
        libro(nullable: true)
    }
}
