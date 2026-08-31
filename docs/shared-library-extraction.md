# Shared Library Extraction

## Goal

Reduce repeated Jenkinsfile logic while keeping service-specific behavior visible.

## Extract To Shared Library

Good candidates:

- `checkoutSource`
- `buildBackend`
- `buildFrontend`
- `createImageTag`
- `buildDockerImage`
- `pushImage`
- `triggerGitOpsUpdate`
- `validateGitOpsHandoff`
- `updateGitOpsImageTag`
- `commitGitOpsUpdate`
- `recordPipelineMetadata`
- `notifyResult`

## Keep In Jenkinsfile

Keep these close to each service:

- service name
- source repository URL
- source branch
- Dockerfile path
- image repository
- Jenkins credential IDs
- build command choices
- test enabled/disabled decision
- downstream job name

## Do Not Extract

- real secret values
- private tokens
- environment files
- full raw logs containing sensitive data
- one-off service-specific fixes that are not reusable

## Design Rule

The Jenkinsfile should read like a delivery story. The shared library should hold reusable mechanics.
