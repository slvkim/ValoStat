package com.mikyegresl.valostat.base.error

class NotEnoughSpaceException : ValoStatException {

    constructor() : super()

    constructor(message: String?) : super(message)

    constructor(message: String?, cause: Throwable?) : super(message, cause)

    constructor(cause: Throwable?) : super(cause)
}
