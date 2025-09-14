package com.api.getcep.exceptions

class CepAlreadyExistsException(cep: String) : RuntimeException("CEP $cep já existe")