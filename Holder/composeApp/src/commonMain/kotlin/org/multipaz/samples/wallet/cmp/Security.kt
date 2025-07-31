package org.multipaz.samples.wallet.cmp

import utopiasample.composeapp.generated.resources.Res

suspend fun getIaca_Cert(): String = Res.readBytes("files/iaca_certificate.pem").decodeToString()
val iaca_private_key="""
    -----BEGIN PRIVATE KEY-----
    MFcCAQAwEAYHKoZIzj0CAQYFK4EEACIEQDA+AgEBBDA/XoO2dHBj2nVeU4oCdLij8poIrIoDz3s3
    lND9v7f9CYQ02wIIYXacdRPeh7N44CmgBwYFK4EEACI=
    -----END PRIVATE KEY-----
""".trimIndent()

