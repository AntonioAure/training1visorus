package com.visorus.tranning1

class Categoria {
    String codigo
    String nombre
    String descripcion
    boolean activo = true

    static constraints = {
        codigo(nullable: false, maxSize: 10)
        nombre(nullable: false, maxSize: 80)
        descripcion(nullable: false, maxSize: 60)
    }
}
