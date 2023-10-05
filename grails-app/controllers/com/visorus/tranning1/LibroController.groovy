package com.visorus.tranning1


import grails.rest.*
import grails.converters.*
import org.springframework.http.HttpStatus

class LibroController {

    LibroService libroService

    static responseFormats = ['json', 'xml']

    def librosActivos() {
        List<Libro> libroList = libroService.getLibrosActivos()
        respond(libroList, status: HttpStatus.OK)
    }

    def libro(long id){
        try {
            respond(libroService.get(id), status: HttpStatus.OK)
        }catch (e){
            respond(status: HttpStatus.BAD_REQUEST, [error: "no existe el libro"])
        }

    }

    def calGeneralLibro(long id){
        try {
            respond(libroService.getLibroCalificacionGeneral(id), status: HttpStatus.OK)
        }catch (e){
            e.printStackTrace()
            respond(status: HttpStatus.BAD_REQUEST, [error: e.getMessage()])
        }

    }

    def save(){
        try {
            Libro libro = libroService.create(request.getJSON())
            respond(status: HttpStatus.CREATED)
        }catch(e){
            respond(status: HttpStatus.BAD_REQUEST, [error: e.getMessage()])
        }
    }

    def update(long id) {
        try {
            Libro libro = libroService.update(id, request.getJSON())
            respond(libro, status: HttpStatus.CREATED)
        }catch(e){
            e.printStackTrace()
            respond(status: HttpStatus.BAD_REQUEST, [error: e.getMessage()])
        }
    }

    def delete(long id) {
        try {
            libroService.delete(id)
            respond(status: HttpStatus.NO_CONTENT)
        }catch(e){
            e.printStackTrace()
            respond(status: HttpStatus.BAD_REQUEST, [error: e.getMessage()])
        }
    }

    def categoriasLibro(long id){
        try {
            Libro libro = libroService.updateCategoriasLibro(id, request.getJSON())
            respond(libro, status: HttpStatus.CREATED)

        }catch (e){
            respond(status: HttpStatus.BAD_REQUEST, [error: e.getMessage()])
        }

    }

    def saveImg(long id) {
        try {
            Libro libro = libroService.uploadImgLibro(id, request)
            respond(libro, status: HttpStatus.CREATED)

        }catch (e){
            respond(status: HttpStatus.BAD_REQUEST, [error: e.getMessage()])
        }
    }
}
