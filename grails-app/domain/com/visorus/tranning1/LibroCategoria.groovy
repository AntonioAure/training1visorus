package com.visorus.tranning1

class LibroCategoria {
    Libro libro
    Categoria categoria

    static constraints = {
        libro(nullable: false)
        categoria(nullable: false)
    }
}
