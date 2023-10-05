package com.visorus.tranning1

class Autor {
    String nombre
    String biografia
    Date fechaNacimiento
    boolean activo = true

    static constraints = {
        nombre(nullable: false, maxSize: 80)
        biografia(nullable: true, maxSize: 2000)
        fechaNacimiento(nullable: false)
    }
}
