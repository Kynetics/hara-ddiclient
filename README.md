<h1 align="center">HARA ddi client</h1>
<p align="center">
<a href="https://travis-ci.org/Kynetics/uf-ddiclient"><img alt="Build Status" src="https://travis-ci.org/Kynetics/uf-ddiclient.svg?branch=master"></a>
<a href="https://codeclimate.com/github/Kynetics/uf-ddiclient/maintainability"><img src="https://api.codeclimate.com/v1/badges/e545d9c1d9256241e7f8/maintainability" /></a>
<a href="https://codeclimate.com/github/Kynetics/uf-ddiclient/test_coverage"><img alt="Test coverage" src="https://api.codeclimate.com/v1/badges/e545d9c1d9256241e7f8/test_coverage"></a>
<a href="https://www.eclipse.org/legal/epl-2.0"><img alt="License" src="https://img.shields.io/badge/License-EPL%202.0-red.svg"></a>
</p>

HARA-DDICLIENT is a Kotlin library that facilitates and speeds up the development 
of [DDI API](https://www.eclipse.org/hawkbit/apis/ddi_api/) clients for devices 
connecting to [hawkBit](https://eclipse.org/hawkbit/) servers. It can be used from
any JVM compatible language (Java, Kotlin, etc).

## Install
To import this project use [jitpack](https://jitpack.io/) plugin.

## Example
Create a class that implements the Observer interface:

     private class ObserverState implements Observer {
        @Override
        public void update(Observable observable, Object o) {
            if (o instanceof UFService.SharedEvent) {
                ...
            }
        }
     }

Create the Service, add the observer and start the service:

    DdiRestApi api = new ClientBuilder()
                .withBaseUrl("https://personal.updatefactory.io")
                .withGatewayToken("[gatewayToken]")
                .withHttpBuilder(new OkHttpClient.Builder())
                .withOnTargetTokenFound(System.out::println)
                .build();

    Map<String,String> map = new HashMap<>();
    map.put("test","tes");

    ufService = UFService.builder()
                .withClient(api)
                .withControllerId("controllerId")
                .withTargetData(()->map)
                .withTenant("test")
                .build();

    ufService.addObserver(new ObserverState());

    ufService.start();

## Third-Party Libraries
For information on the libraries used by this project see [NOTICE](NOTICE.md)

## Authors
* **Daniele Sergio** - *Initial work* - [danielesergio](https://github.com/danielesergio)

See also the list of [contributors](https://github.com/Kynetics/hara-ddiclient/graphs/contributors) who participated in this project.

## License
Copyright © 2017-2021, [Kynetics LLC](https://www.kynetics.com).

Released under the [EPLv2 License](https://www.eclipse.org/legal/epl-2.0).
