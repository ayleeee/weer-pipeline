# Jenkins Setup

## Shared Library

Register this repository's `shared-library-system` as a Jenkins Global Pipeline Library.

Suggested library name:

```text
weer-shared-library
```

The Jenkinsfiles load it with:

```groovy
@Library('weer-shared-library') _
```

## Jenkinsfiles

Pipeline definitions are grouped under:

```text
jenkinsfiles/
```

Jobs:

- `jenkinsfiles/jenkinsfile.backend`
- `jenkinsfiles/jenkinsfile.frontend`
- `jenkinsfiles/jenkinsfile.update-k8s-manifest`

## Required Tool Names

The example Jenkinsfiles assume these Jenkins tool names:

- `jdk21`
- `node18`

## Credential Placeholders

Do not commit real secrets. Use Jenkins credentials.

Expected credential IDs:

- `registry-credentials`

Optional future credentials:

- `gitops-repo-token`
- `sonarqube-token`
- `influxdb-token`

## Required Plugins

Likely plugins:

- Pipeline
- Git
- Docker Pipeline or shell-based Docker access
- Credentials Binding
- NodeJS
- JDK tool installer or configured JDK

Optional plugins:

- SonarQube Scanner for Jenkins
- InfluxDB plugin
- Slack or generic notification plugin

## Local Execution Note

This repo is designed as a portfolio pipeline reference. A fully running Jenkins controller is useful evidence, but the first milestone is readable pipeline design plus sanitized original evidence.
