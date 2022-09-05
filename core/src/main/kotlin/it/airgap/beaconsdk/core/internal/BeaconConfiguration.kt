package it.airgap.beaconsdk.core.internal

import it.airgap.beaconsdk.core.internal.annotation.InternalBeaconApi

@InternalBeaconApi
public data class BeaconConfiguration(
    public val ignoreUnsupportedBlockchains: Boolean = false,
) {

    internal enum class CryptoProvider {
        LazySodium
    }

    internal enum class SerializerProvider {
        Base58Check
    }

    internal enum class HttpClientProvider {
        Ktor
    }

    public companion object {

        // -- SDK --

        public const val STORAGE_NAME: String = "beaconsdk"

        public const val BEACON_VERSION: String = "3"
        public const val SDK_VERSION: String = "3.0.0"

        internal val cryptoProvider: CryptoProvider = CryptoProvider.LazySodium
        internal val serializerProvider: SerializerProvider = SerializerProvider.Base58Check
        internal val httpClientProvider: HttpClientProvider = HttpClientProvider.Ktor
    }
}
