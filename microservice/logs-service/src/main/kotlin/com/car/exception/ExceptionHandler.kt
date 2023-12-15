package com.car.exception

import org.apache.commons.lang3.StringUtils
import org.slf4j.LoggerFactory
import org.springframework.boot.web.server.ErrorPage
import org.springframework.context.MessageSource
import org.springframework.context.i18n.LocaleContextHolder
import org.springframework.context.support.DefaultMessageSourceResolvable
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.ObjectError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException
import java.util.*


@RestControllerAdvice
class ExceptionHandler(
    private var messageSource: MessageSource
) {
    private val log = LoggerFactory.getLogger(this::class.java)

    @ExceptionHandler(GeneralException::class)
    fun generalException(ex: Exception): ResponseEntity<ResponseBase?>? {
        log.error("GeneralException", ex)
        var error = ResponseBase(errorMessage = getLangMessage(ex.message, null))
        return ResponseEntity(error, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(GeneralWarning::class)
    fun generalWarning(ex: java.lang.Exception): ResponseEntity<ResponseBase?>? {
        log.error("GeneralWarning", ex)
        val warning = ResponseBase(warningMessage = getLangMessage(ex.message, null))
        return ResponseEntity(warning, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(value = [RuntimeException::class])
    fun runtimeException(ex: java.lang.Exception): ResponseEntity<ResponseBase?>? {
        log.error("RuntimeException", ex)
        val error = ResponseBase(errorMessage = getLangMessage(ex.message, null))
        return ResponseEntity(error, HttpStatus.BAD_REQUEST)
    }


    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun methodArgumentNotValidException(ex: MethodArgumentNotValidException): ResponseEntity<ResponseBase?>? {
        log.error("MethodArgumentNotValidException", ex)

        val allError: MutableList<Any>? = ex.bindingResult.allErrors.stream().map<Any?> { m: ObjectError ->
            getLangMessage(
                m.defaultMessage,
                if (m.arguments != null)
                    Arrays.stream(m.arguments).filter { f -> f !is DefaultMessageSourceResolvable }.toArray() else null
            )
        }.toList()
        val error = ResponseBase(errorMessage = StringUtils.join(allError, ", "))
        return ResponseEntity(error, HttpStatus.BAD_REQUEST)
    }


    @ExceptionHandler(value = [MethodArgumentTypeMismatchException::class])
    fun MethodArgumentTypeMismatchException(ex: java.lang.Exception?) {
        log.error("MethodArgumentTypeMismatchException", ex)
        ErrorPage(HttpStatus.NOT_FOUND, "/error")
    }

    @ExceptionHandler(value = [java.lang.Exception::class])
    fun unknownException(ex: java.lang.Exception?): ResponseEntity<ResponseBase?>? {
        log.error("Exception", ex)
        val error = ResponseBase(errorMessage = getLangMessage("{unknown.exception}", null))
        return ResponseEntity(error, HttpStatus.BAD_REQUEST)
    }

    private fun getLangMessage(msg: String?, arguments: Array<Any>?): String? {
        val message: String = StringUtils.substringBetween(msg, "{", "}") ?: return msg
        val anotherMessage = msg?.replace("{$message}", "")
        return """
             ${messageSource.getMessage(message, arguments, LocaleContextHolder.getLocale())}
             ${if (!anotherMessage!!.isEmpty()) anotherMessage else ""}
             """.trimIndent()
    }


}