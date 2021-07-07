<p align="center"><img src="hara_logo.png" width=20% height=20% ></p>
<h1 align="center">Eclipse Hara™ - hara-ddiclient</h1>
<p align="center">
<a href="https://travis-ci.org/Kynetics/uf-ddiclient"><img alt="Build Status" src="https://travis-ci.org/Kynetics/uf-ddiclient.svg?branch=master"></a>
<a href="https://codeclimate.com/github/Kynetics/uf-ddiclient/maintainability"><img src="https://api.codeclimate.com/v1/badges/e545d9c1d9256241e7f8/maintainability" /></a>
<a href="https://codeclimate.com/github/Kynetics/uf-ddiclient/test_coverage"><img alt="Test coverage" src="https://api.codeclimate.com/v1/badges/e545d9c1d9256241e7f8/test_coverage"></a>
<a href="https://www.eclipse.org/legal/epl-2.0"><img alt="License" src="https://img.shields.io/badge/License-EPL%202.0-red.svg"></a>
</p>

Hara-ddiclient is a Kotlin library that facilitates and speeds up the development
of [DDI API](https://www.eclipse.org/hawkbit/apis/ddi_api/) clients for devices 
connecting to [hawkBit](https://eclipse.org/hawkbit/) servers. It can be used from
any JVM compatible language (Java, Kotlin, etc).
Hara-ddiclient is part of the [Eclipse Hara™ project](https://projects.eclipse.org/projects/iot.hawkbit.hara)

## Install
To import this project use [jitpack](https://jitpack.io/) plugin.

## Example
Create a class that implements the [DirectoryForArtifactsProvider](core/core-api/src/main/kotlin/org/eclipse/hara/ddiclient/core/api/DirectoryForArtifactsProvider.kt) interface:

    class DirectoryForArtifactsProviderImpl(): DirectoryForArtifactsProvider {
        override fun directoryForArtifacts(): File {
            ...
        }
    }

Create a class that implements the [ConfigDataProvider](core/core-api/src/main/kotlin/org/eclipse/hara/ddiclient/core/api/ConfigDataProvider.kt) interface:

    class ConfigDataProviderImpl(): ConfigDataProvider {
        override fun configData(): Map<String, String> {
            ...
        }
    }

Create a class that implements the [DeploymentPermitProvider](core/core-api/src/main/kotlin/org/eclipse/hara/ddiclient/core/api/DeploymentPermitProvider.kt) interface:

    class DeploymentPermitProviderImpl: DeploymentPermitProvider {
        override fun downloadAllowed(): Deferred<Boolean> {
            ...
        }
        override fun updateAllowed(): Deferred<Boolean> {
            ...
        }
    }

Create a class that implements the [MessageListener](core/core-api/src/main/kotlin/org/eclipse/hara/ddiclient/core/api/MessageListener.kt) interface:

    class MessageListenerImpl(): MessageListener {
            override fun onMessage(message: MessageListener.Message) {
            ...
        }
    }

Create a class that implements the [Updater](core/core-api/src/main/kotlin/org/eclipse/hara/ddiclient/core/api/Updater.kt) interface:

    class UpdaterImpl(): Updater {
        override fun apply(modules: Set<Updater.SwModuleWithPath>,messenger: Updater.Messenger){
            ...
        }

Create the Client, add the provider and return the client:

    val client = HaraClientDefaultImpl()
    client.init(
        clientData,
        DirectoryForArtifactsProviderImpl(),
        ConfigDataProviderImpl(),
        DeploymentPermitProviderImpl(),
        listOf(MessageListenerImpl()),
        listOf(UpdaterImpl())
    )
    return client

## Contributing
To contribute to this project please [open a GitHub pull request](https://docs.github.com/en/github/collaborating-with-pull-requests/proposing-changes-to-your-work-with-pull-requests/about-pull-requests).

## Contact us

- Having questions about hara-ddiclient? Subscribe to the [hara-dev mailing list](https://accounts.eclipse.org/mailing-list/hara-dev) and post a question!
- Having issues with hara-ddiclient? Please open a GitHub issue.

## Third-Party Libraries
For information on the libraries used by this project see [NOTICE](NOTICE.md).

## Authors
* **Daniele Sergio** - *Initial work* - [danielesergio](https://github.com/danielesergio).

See also the list of [contributors](https://github.com/Kynetics/hara-ddiclient/graphs/contributors) who participated in this project.

## License
Copyright © 2017-2021, [Kynetics LLC](https://www.kynetics.com).

Released under the [EPLv2 License](https://www.eclipse.org/legal/epl-2.0).