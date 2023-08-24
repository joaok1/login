package com.system.System.Dto

import javax.validation.constraints.NotEmpty


class TokenDto(login: @NotEmpty(message = "{campo.login.obrigatorio}") String?, token: String?) {
    private val login : String? = null;
    private val token : String? = null;


}