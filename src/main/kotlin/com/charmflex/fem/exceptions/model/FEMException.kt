package com.charmflex.fem.exceptions.model

abstract class FEMException : Exception() {
    abstract val module: String
    abstract override val message: String
}


class InvalidParamException(
    override val module: String,
    override val message: String
) : FEMException()