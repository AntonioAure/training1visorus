package com.visorus.tranning1


import grails.rest.*
import grails.converters.*
import org.springframework.http.HttpStatus

class CalificacionController {

    CalificacionService calificacionService

    static responseFormats = ['json', 'xml']

    def calificacionesActivas() {
        List<Calificacion> calificacionList = calificacionService.getCalificacionesActivas()
        //debe responder por un mapa data, totaly calificacion general
        respond(calificacionList, status: HttpStatus.OK)
    }

    def calificacion(long id){
        try {
            respond(calificacionService.get(id), status: HttpStatus.OK)
        }catch (e){
            respond(status: HttpStatus.BAD_REQUEST, [error: "no existe la calificacion"])
        }

    }


    def save(){
        try {
            Calificacion calificacion = calificacionService.create(request.getJSON())
            respond(status: HttpStatus.CREATED)
        }catch(e){
            respond(status: HttpStatus.BAD_REQUEST, [error: e.getMessage()])
        }
    }

    def update(long id) {
        try {
            Calificacion calificacion = calificacionService.update(id, request.getJSON())
            respond(calificacion, status: HttpStatus.CREATED)
        }catch(e){
            e.printStackTrace()
            respond(status: HttpStatus.BAD_REQUEST, [error: e.getMessage()])
        }
    }

    def delete(long id) {
        try {
            calificacionService.delete(id)
            respond(status: HttpStatus.NO_CONTENT)
        }catch(e){
            e.printStackTrace()
            respond(status: HttpStatus.BAD_REQUEST, [error: e.getMessage()])
        }
    }
}
