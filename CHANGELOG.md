# Changelog
All notable changes to this project will be documented in this file.

## 1.2.0 - [unreleased]
### Added
- Support for Carte Bancaire

### Upgraded
- Update fasterxml/jackson to 2.9.9
- Update apache/httpclient to 4.5.9
- Update apache/commons-lang3 to 3.9
- Upate apache/comons-codec to 1.13
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