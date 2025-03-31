package com.charmflex.fem.exceptions

import com.charmflex.fem.exceptions.model.ErrorResponse
import com.charmflex.fem.exceptions.model.InvalidParamException
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.context.request.WebRequest

@RestControllerAdvice
class ExceptionControllerAdvice {

    @ExceptionHandler(InvalidParamException::class)
    fun handleInvalidParamsException(invalidParamException: InvalidParamException, webRequest: WebRequest): ResponseEntity<ErrorResponse> {
        val errorResponse = ErrorResponse(invalidParamException.module, invalidParamException.message)
        return ResponseEntity.badRequest().body(errorResponse)
    }
}