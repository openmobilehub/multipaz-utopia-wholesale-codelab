package org.multipaz.simpledemo

import kotlinx.datetime.Clock
import kotlinx.io.bytestring.encodeToByteString
import org.multipaz.asn1.ASN1Integer
import org.multipaz.crypto.Algorithm
import org.multipaz.crypto.Crypto
import org.multipaz.crypto.EcCurve
import org.multipaz.crypto.X500Name
import org.multipaz.crypto.X509CertChain
import org.multipaz.document.Document
import org.multipaz.documenttype.knowntypes.DrivingLicense
import org.multipaz.mdoc.credential.MdocCredential
import org.multipaz.mdoc.util.MdocUtil
import org.multipaz.securearea.CreateKeySettings
import kotlin.time.Duration.Companion.days


suspend fun createCredential(document: Document): MdocCredential{
    val now = Clock.System.now()
    val signedAt = now
    val validFrom = now
    val validUntil = now + 365.days
    val iacaKey = Crypto.createEcPrivateKey(EcCurve.P256)
    val iacaCert = MdocUtil.generateIacaCertificate(
        iacaKey = iacaKey,
        subject = X500Name.fromName(name = "CN=Test IACA Key"),
        serial = ASN1Integer.fromRandom(numBits = 128),
        validFrom = validFrom,
        validUntil = validUntil,
        issuerAltNameUrl = "https://issuer.example.com",
        crlUrl = "https://issuer.example.com/crl"
    )
    val dsKey = Crypto.createEcPrivateKey(EcCurve.P256)
    val dsCert = MdocUtil.generateDsCertificate(
        iacaCert = iacaCert,
        iacaKey = iacaKey,
        dsKey = dsKey.publicKey,
        subject = X500Name.fromName(name = "CN=Test DS Key"),
        serial = ASN1Integer.fromRandom(numBits = 128),
        validFrom = validFrom,
        validUntil = validUntil
    )
    val mdocCredential =
        DrivingLicense.getDocumentType().createMdocCredentialWithSampleData(
            document = document,
            secureArea = secureArea,
            createKeySettings = CreateKeySettings(
                algorithm = Algorithm.ESP256,
                nonce = "Challenge".encodeToByteString(),
                userAuthenticationRequired = true
            ),
            dsKey = dsKey,
            dsCertChain = X509CertChain(listOf(dsCert)),
            signedAt = signedAt,
            validFrom = validFrom,
            validUntil = validUntil,
        )
    return mdocCredential
}

