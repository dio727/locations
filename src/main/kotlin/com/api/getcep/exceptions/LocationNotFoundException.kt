package com.api.getcep.exceptions

class LocationNotFoundException(id: Long) :
    RuntimeException("Location with id $id not found")