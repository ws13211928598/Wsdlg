package com.example.lib_network

import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.reflect.Type

/**
 * @author Edwin Wu
 * @since 1.0.0
 */
class NullOnEmptyConverterFactory private constructor() : Converter.Factory() {

    companion object {
        fun create(): Converter.Factory {
            return NullOnEmptyConverterFactory()
        }
    }

    override fun responseBodyConverter(
        type: Type,
        annotations: Array<out Annotation>,
        retrofit: Retrofit
    ): Converter<ResponseBody, *>? {
//        return super.responseBodyConverter(type, annotations, retrofit)

        val delegate = retrofit.nextResponseBodyConverter<Any>(this, type, annotations)
        return Converter { body ->
            if (body.contentLength() == 0L) {
                null
            } else delegate.convert(body)
        }
    }

    override fun requestBodyConverter(
        type: Type,
        parameterAnnotations: Array<out Annotation>,
        methodAnnotations: Array<out Annotation>,
        retrofit: Retrofit
    ): Converter<*, RequestBody>? {
        return super.requestBodyConverter(type, parameterAnnotations, methodAnnotations, retrofit)
    }

    override fun stringConverter(
        type: Type,
        annotations: Array<out Annotation>,
        retrofit: Retrofit
    ): Converter<*, String>? {
        return super.stringConverter(type, annotations, retrofit)
    }

}