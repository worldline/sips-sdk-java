# Changelog
All notable changes to this project will be documented in this file.

## 1.4.3 - [2022-01-19]
### Added
- added returnContext to response data.

### Upgraded
- Update fasterxml/jackson to 2.13.1

## 1.4.2 - [2021-12-11]
### Changed
- now using global ObjectMapper

### Upgraded
- Update fasterxml/jackson to 2.13.0
- Update apache/httpclient to 5.1.2
- Update apache/commons-lang3 to 3.12

## 1.4.1 - [2021-03-04]
### Changed
- Sort PaymentMeanBrands for seal calculation
- Reinstated tests


## 1.4.0 - [2021-01-26]
:warning: **BREAKING:** Changed SIPS platform URLs

### Changed
- Bumped interface version to 21R1 (IR_WS_2.46)

### Upgraded
- Update fasterxml/jackson to 2.12.1

## 1.3.0 - [2020-12-03]
Updated classes conform Data Dictionary 20R6


### Upgraded
- Update fasterxml/jackson to 2.12.0
- Update apache/httpclient to 4.5.13
- Update apache/commons-lang3 to 3.11
- Update apache/commons-codec to 1.15

## 1.2.0 - [2019-09-09]
### Added
- Support for Carte Bancaire

### Upgraded
- Update fasterxml/jackson to 2.9.9
- Update apache/httpclient to 4.5.9
- Update apache/commons-lang3 to 3.9
- Update apache/commons-codec to 1.13
- Update to JUnit 5.5

### Changed
- Optimized Gradle build process
- Bumped interface version to 19R4 (IR_WS_2.26)

## 1.1.1 - [2019-03-13]

### Changed
- Optimized Gradle build process
- Bumped interface version to 19R1 (IR_WS_2.23)

## 1.1.0 - [2019-03-12]

### Changed
- Switched from Maven to Gradle for build

### Added
- Support for proxy on Sips HTTP Client
- Support for ISP groups

### Upgraded
- Update Jackson dependency to 2.9.8

## 1.0.1 - [2017-02-07]

### Fixed
- Dependency issue for Apache Commons Lang
- Exception message & javadoc for seal verification of payment page responses
- Typo in README

## 1.0.0 - [2017-02-06]

### Added
- Support for _SIPS 2.0_ in payment page mode
- Seal verification for initialization & payment responses
