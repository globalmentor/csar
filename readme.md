# Csar

The _Concern Separation Aspect Registrar_ ([**Csar**](https://csar.io/) /zɑːr/) provides access to some concern (usually cross-cutting) that may configured globally or locally to some section of the program. Csar acts like a global service locator that provides flexible, transparent local configuration. Like a "czar" in American politics, Csar governs configuration and access to program concerns.

Csar supports concerns to be configured for some local part of the program by associating a concern of a specific type, such as logging or internationalization, with some thread group. A consumer library will ask Csar for the configured concern and it will be looked up transparently, without the need for dependency injection. A global concern can also be set, which which serves as a fallback for that concern type when no thread group-specific concern is defined.

For an example of the "relatively difficult problem" Csar was meant to address, see [Logging separation](https://logback.qos.ch/manual/loggingSeparation.html) ([Logback](https://logback.qos.ch/)).

## Concern Providers

- Internationalization: [Rincl](https://rincl.io)
- Logging: [Clogr](https://clogr.io/)
- Dependency Injection

## Download

Csar is available in the Maven Central Repository in group [io.csar](https://search.maven.org/search?q=g:io.csar).

## Issues

Issues tracked by [JIRA](https://globalmentor.atlassian.net/projects/CSAR).

## Changelog

- 0.7.0:
	* [RINCL-1](https://globalmentor.atlassian.net/browse/CSAR-1): An easy way to create concerned thread groups.
- 0.6.0: Added concern provider mechanism.
- 0.5.0: First public release.
