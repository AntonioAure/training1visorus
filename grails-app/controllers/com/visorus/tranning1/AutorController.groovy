package com.visorus.tranning1


import grails.rest.*
import grails.converters.*
import org.springframework.http.HttpStatus

class AutorController {

    AutorService autorService

    static responseFormats = ['json', 'xml']

    def autoresActivos() {
        List<Autor> autorList = autorService.getAutoresActivos()
        respond(autorList, status: HttpStatus.OK)
    }

    def autor(long id){
        try {
            respond(autorService.get(id), status: HttpStatus.OK)
        }catch (e){
            respond(status: HttpStatus.BAD_REQUEST, [error: "no existe ese autor"])
        }

    }


    def save(){
        try {
            Autor autor = autorService.create(request.getJSON())
            respond(status: HttpStatus.CREATED)
        }catch(e){
            respond(status: HttpStatus.BAD_REQUEST, [error: e.getMessage()])
        }
    }

    def update(long id) {
        try {
            Autor autor = autorService.update(id, request.getJSON())
            respond(autor, status: HttpStatus.CREATED)
        }catch(e){
            e.printStackTrace()
            respond(status: HttpStatus.BAD_REQUEST, [error: e.getMessage()])
        }
    }

    def delete(long id) {
        try {
            autorService.delete(id)
            respond(status: HttpStatus.NO_CONTENT)
        }catch(e){
            e.printStackTrace()
            respond(status: HttpStatus.BAD_REQUEST, [error: e.getMessage()])
        }
    }
}
