package com.api.getcep.exceptions

class CepNotFoundException(cep: String) :
    RuntimeException("Cep $cep not found")