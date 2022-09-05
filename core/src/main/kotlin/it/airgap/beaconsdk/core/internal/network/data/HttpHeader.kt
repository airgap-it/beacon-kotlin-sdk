package it.airgap.beaconsdk.core.internal.network.data

import it.airgap.beaconsdk.core.internal.annotation.InternalBeaconApi
import it.airgap.beaconsdk.core.network.data.HttpHeader

@InternalBeaconApi
@Suppress("FunctionName")
public fun AuthorizationHeader(value: String): HttpHeader = HttpHeader("Authorization", value)

@InternalBeaconApi
@Suppress("FunctionName")
public fun BearerHeader(token: String): HttpHeader = AuthorizationHeader("Bearer $token")

@InternalBeaconApi
@Suppress("FunctionName")
public fun ApplicationJson(): HttpHeader = HttpHeader("Content-Type", "application/json")