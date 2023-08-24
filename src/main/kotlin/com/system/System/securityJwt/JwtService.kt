package com.system.System.securityJwt

import com.system.System.model.Usuario
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jws
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.lang.Exception
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*

@Service
class JwtService {

    @Value("\${security.jwt.expiracao}")
    private val expiracao:String? = null;

    @Value("\${security.jwt.chave-assinatura}")
    private val chaveAssinatura: String? = null;

    fun gerarToken(usuario: Usuario ) : String? {
        val expString = java.lang.Long.valueOf(expiracao)
        val dataHoraExpiracao = LocalDateTime.now().plusMinutes(expString);
        var instant:Instant? = null;
        instant = dataHoraExpiracao.atZone(ZoneId.systemDefault()).toInstant();
        val data: Date = Date.from(instant);
        return Jwts
            .builder()
            .setSubject(usuario.login)
            .setExpiration(data)
            .signWith(SignatureAlgorithm.HS512, chaveAssinatura)
            .compact()

    }

     fun obterClaims(token:String) : Claims {
        return Jwts
            .parser()
            .setSigningKey(chaveAssinatura)
            .parseClaimsJwt(token)
            .body;
    }

    fun tokenValido(token: String) : Boolean {
        try {
            val claims = obterClaims(token);
            val dataExpiracao : Date = claims.expiration;
            val data : LocalDateTime = dataExpiracao
                .toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
            return !LocalDateTime.now().isAfter(data);

        } catch (e : Exception) {
            return throw e;
        }
    }

    fun obterLoginUsuario(token:String) : String {
        return obterClaims(token).subject;
    }

}