package br.com.luizcampos.deepink.model

import com.google.firebase.firestore.Exclude

data class NewUser(
    val username: String? = null,
    val email: String? = null,
    val phone: String? = null,
    @Exclude val password: String? = null
)