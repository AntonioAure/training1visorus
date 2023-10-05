package com.visorus.tranning1


import grails.rest.*
import grails.converters.*
import org.springframework.http.HttpStatus

class CategoriaController {

    CategoriaService categoriaService

	static responseFormats = ['json', 'xml']

    def categoriasActivas() {
        List<Categoria> categoriaList = categoriaService.getCategoriasActivas()
        respond(categoriaList)
    }

    def categoria(long id){
        try {
            respond(categoriaService.get(id))
        }catch (e){
            respond(status: HttpStatus.BAD_REQUEST, [error: "no existe esa categoria"])
        }

    }


    def save(){
        try {
            Categoria categoria = categoriaService.create(request.getJSON())
            respond(status: HttpStatus.CREATED)
        }catch(e){
            respond(status: HttpStatus.BAD_REQUEST, [error: e.getMessage()])
        }
    }

    def update(long id) {
        try {
            Categoria categoria = categoriaService.update(id, request.getJSON())
            respond(categoria)
        }catch(e){
            e.printStackTrace()
            respond(status: HttpStatus.BAD_REQUEST, [error: e.getMessage()])
        }
    }

    def delete(long id) {
        try {
            //Categoria categoria = categoriaService.update(id, request.getJSON())
            categoriaService.delete(id)
            respond(status: HttpStatus.NO_CONTENT)
        }catch(e){
            e.printStackTrace()
            respond(status: HttpStatus.BAD_REQUEST, [error: e.getMessage()])
        }
    }
}
