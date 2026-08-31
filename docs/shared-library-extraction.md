# Shared Library Extraction

## Goal

Reduce repeated Jenkinsfile logic while keeping service-specific behavior visible.

## Extract To Shared Library

Good candidates:

- `checkoutSource`
- `buildApplication`
- `publishDockerImage`
- `publishStaticSite`
- `triggerGitOpsUpdate`
- `updateGitOpsManifest`
- `recordPipelineMetadata`

The library should not split every stage into a separate function. Functions are extracted only when they remove repeated backend/frontend logic or define a reusable boundary.

## Keep In Jenkinsfile

Keep these close to each service:

- service name
- source repository URL
- source branch
- Dockerfile path
- image repository
- S3 bucket URI
- CloudFront distribution ID
- Jenkins credential IDs
- build command choices
- test enabled/disabled decision
- downstream job name
- image tag format
- result notification wording

## Do Not Extract

- real secret values
- private tokens
- environment files
- full raw logs containing sensitive data
- one-off service-specific fixes that are not reusable

## Design Rule

The Jenkinsfile should read like a delivery story. The shared library should hold reusable mechanics.

Current intended shared library surface:

```text
checkoutSource
buildApplication
publishDockerImage
publishStaticSite
triggerGitOpsUpdate
updateGitOpsManifest
recordPipelineMetadata
```
