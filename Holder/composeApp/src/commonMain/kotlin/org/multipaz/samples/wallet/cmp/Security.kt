package org.multipaz.samples.wallet.cmp

import utopiasample.composeapp.generated.resources.Res

suspend fun getIaca_Cert(): String = Res.readBytes("files/iaca_certificate.pem").decodeToString()

suspend fun getIaca_Private_Key(): String = Res.readBytes("files/iaca_private_key.pem").decodeToString()
suspend fun getReader_Root_Cert(): String = Res.readBytes("files/reader_root_certificate.pem").decodeToString()

suspend fun getReader_Root_Cert_for_untrust_device(): String = Res.readBytes("files/reader_root_certificate_for_untrust_device.pem").decodeToString()
