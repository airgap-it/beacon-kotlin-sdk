# Beacon Kotlin SDK

[![stable](https://img.shields.io/github/v/tag/airgap-it/beacon-kotlin-sdk?label=stable&sort=semver)](https://github.com/airgap-it/beacon-kotlin-sdk/releases)
[![latest](https://img.shields.io/github/v/tag/airgap-it/beacon-kotlin-sdk?color=orange&include_prereleases&label=latest)](https://github.com/airgap-it/beacon-kotlin-sdk/releases)
[![release](https://img.shields.io/jitpack/v/github/airgap-it/beacon-kotlin-sdk)](https://jitpack.io/#airgap-it/beacon-kotlin-sdk)
[![documentation](https://img.shields.io/badge/documentation-online-brightgreen.svg)](https://docs.walletbeacon.io/wallet/getting-started/kotlin/installation)
[![license](https://img.shields.io/github/license/airgap-it/beacon-kotlin-sdk)](https://github.com/airgap-it/beacon-kotlin-sdk/blob/main/LICENSE)

> Connect Wallets with dApps on Tezos

[Beacon](https://walletbeacon.io) is an implementation of the wallet interaction standard [tzip-10](https://gitlab.com/tzip/tzip/blob/master/proposals/tzip-10/tzip-10.md) which describes the connection of a dApp with a wallet.

## About

`Beacon Kotlin SDK` provides Kotlin and Java developers with tools useful for setting up communication between native wallets supporting Tezos and dApps that implement [`beacon-sdk`](https://github.com/airgap-it/beacon-sdk).

## Installation

To add `Beacon Kotlin SDK` into your project:

  1. Make sure the [JitPack](https://jitpack.io/) repository is included in your root `build.gradle` file:

  #### Groovy
  ```groovy
  allprojects {
    repositories {
      ...
      maven { url 'https://jitpack.io' }
    }
  }
  ```

  #### Kotlin
  ```kotlin
  allprojects {
    repositories {
      ...
      maven("https://jitpack.io")
    }
  }
  ```

  2. Add the dependencies:

  #### Groovy
  ```groovy
  dependencies {
    def beacon_version = "3.0.0"

    // REQUIRED, core
    implementation "com.github.airgap-it.beacon-kotlin-sdk:core:$beacon_version"

    // optional, client-dapp
    implementation "com.github.airgap-it.beacon-kotlin-sdk:client-dapp:$beacon_version"

    // optional, client-wallet
    implementation "com.github.airgap-it.beacon-kotlin-sdk:client-wallet:$beacon_version"
    // optional, client-wallet-compat
    implementation "com.github.airgap-it.beacon-kotlin-sdk:client-wallet-compat:$beacon_version"

    // optional, blockchain-substrate
    implementation "com.github.airgap-it.beacon-kotlin-sdk:blockchain-substrate:$beacon_version"
    // optional, blockchain-tezos
    implementation "com.github.airgap-it.beacon-kotlin-sdk:blockchain-tezos:$beacon_version"

    // optional, transport-p2p-matrix
    implementation "com.github.airgap-it.beacon-kotlin-sdk:transport-p2p-matrix:$beacon_version"

    ---

    // alternatively, all modules
    implementation "com.github.airgap-it:beacon-kotlin-sdk:$beacon_version"
  }
  ```

  #### Kotlin
  ```kotlin
  dependencies {
    val beaconVersion = "3.0.0"

    // REQUIRED, core
    implementation("com.github.airgap-it.beacon-kotlin-sdk:core:$beaconVersion")

    // optional, client-dapp
    implementation("com.github.airgap-it.beacon-kotlin-sdk:client-dapp:$beaconVersion")

    // optional, client-wallet
    implementation("com.github.airgap-it.beacon-kotlin-sdk:client-wallet:$beaconVersion")
    // optional, client-wallet-compat
    implementation("com.github.airgap-it.beacon-kotlin-sdk:client-wallet-compat:$beaconVersion")

    // optional, blockchain-substrate
    implementation("com.github.airgap-it.beacon-kotlin-sdk:blockchain-substrate:$beaconVersion")
    // optional, blockchain-tezos
    implementation("com.github.airgap-it.beacon-kotlin-sdk:blockchain-tezos:$beaconVersion")

    // optional, transport-p2p-matrix
    implementation("com.github.airgap-it.beacon-kotlin-sdk:transport-p2p-matrix:$beaconVersion")

    ---

    // alternatively, all modules
    implementation("com.github.airgap-it:beacon-kotlin-sdk:$beaconVersion")
  }
  ```
### Proguard and R8

`Beacon Kotlin SDK` internally uses various libraries that may require custom ProGuard rules. If you're using ProGuard or R8, please follow the guides listed below to make sure your app works correctly after obfuscation:

- [ProGuard rules for Kotlin Serialization](https://github.com/Kotlin/kotlinx.serialization#android)
- [ProGuard rules for LazySodium](https://github.com/terl/lazysodium-java/wiki/installation#proguard)

## Documentation

The documentation can be found [here](https://docs.walletbeacon.io/).

## Project Overview

The project consists of the following modules:

### Core

Core modules are the basis for other modules. They are required for the SDK to work as expected.

| Module  | Description            | Dependencies | Required by                                                                                                                                 |
|---------|------------------------|--------------|---------------------------------------------------------------------------------------------------------------------------------------------|
| `:core` | Base for other modules | ✖️           | `:client-dapp` <br /> `:client-wallet` <br /><br /> `:blockchain-substrate` <br /> `:blockchain-tezos` <br /><br /> `:transport-p2p-matrix` |

### Client

Client modules ship with Beacon implementations for different parts of the network.

| Module           | Description                       | Dependencies | Required by |
|------------------|-----------------------------------|--------------|-------------|
| `:client-dapp`   | Beacon implementation for dApps   | `:core`      | ✖️          |
| `:client-wallet` | Beacon implementation for wallets | `:core`      | ✖️          |

### Blockchain

Blockchain modules provide support for different blockchains.

| Module                  | Description                   | Dependencies | Required by |
|-------------------------|-------------------------------|--------------|-------------|
| `:blockchain-substrate` | Substrate specific components | `:core`      | ✖️          |
| `:blockchain-tezos`     | Tezos specific components     | `:core`      | ✖️          |

### Transport

Transport modules provide various interfaces used to establish connection between Beacon clients.

| Module                  | Description                                                                              | Dependencies | Required by |
|-------------------------|------------------------------------------------------------------------------------------|--------------|-------------|
| `:transport-p2p-matrix` | Beacon P2P implementation which uses [Matrix](https://matrix.org/) for the communication | `:core`      | ✖️          |

## Examples

The snippets below show how to quickly setup a wallet listening for incoming Beacon messages in Kotlin with coroutines.

### Create a Beacon wallet client and listen for incoming requests

```kotlin
import it.airgap.beaconsdk.blockchain.substrate.substrate
import it.airgap.beaconsdk.blockchain.tezos.tezos
import it.airgap.beaconsdk.client.wallet.BeaconWalletClient
import it.airgap.beaconsdk.transport.p2p.matrix.p2pMatrix

class MyBeaconHandler {
  lateinit var client: BeaconWalletClient

  // ...

  suspend fun listenForBeaconMessages() {
    // create a wallet Beacon client that can listen for Substrate and Tezos messages via Matrix network
    client = BeaconWalletClient("MyApp", MyStorage(), MySecureStorage()) {
      support(substrate(), tezos())
      use(p2pMatrix(MyP2pMatrixStoragePlugin()))
    }

    myCoroutineScope.launch {
      // subscribe to a message Flow
      client.connect().collect { /* process messages */ }
    }
  }
}

class MyStorage : Storage { /* implementation */ }
class MySecureStorage : SecureStorage { /* implementation */ }
class MyP2pMatrixStoragePlugin : P2pMatrixStoragePlugin { /* implementation */ }
```

## Development

The project is built with [Gradle](https://gradle.org/).

### Build

Build all modules:
```
$ ./gradlew assemble
```

Build a single module:
```
$ ./gradlew :${module}:assemble
```

### Run Tests

```
$ ./gradlew test
```

---
## Related Projects

[Beacon SDK](https://github.com/airgap-it/beacon-sdk) - an SDK for web developers

[Beacon Android SDK](https://github.com/airgap-it/beacon-android-sdk) - an SDK for Android developers
[Beacon iOS SDK](https://github.com/airgap-it/beacon-ios-sdk) - an SDK for iOS developers
