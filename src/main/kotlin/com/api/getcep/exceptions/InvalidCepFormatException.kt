package com.api.getcep.exceptions

class InvalidCepFormatException(cep: String) : RuntimeException("Formato de CEP inválido: $cep")