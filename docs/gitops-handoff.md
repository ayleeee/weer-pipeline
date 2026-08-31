# GitOps Handoff

## Boundary

This repository does not own the full GitOps flow. It owns the Jenkins side of the handoff.

Jenkins responsibilities:

- build/test application
- build backend image from a versioned Dockerfile
- push backend image to registry
- collect backend image metadata
- trigger downstream `Update K8S Manifest` job for backend deployments
- preserve traceability metadata

GitOps repository responsibilities:

- update Helm values or Kubernetes manifests
- commit/push declarative deployment changes
- let Argo CD sync to k3s
- manage rollout and rollback evidence

## Handoff Parameters

The downstream job should receive:

- `SERVICE_NAME`
- `IMAGE_REPOSITORY`
- `IMAGE_TAG`
- `IMAGE_DIGEST`
- `SOURCE_COMMIT`
- `UPSTREAM_BUILD_URL`

## Downstream Job

The downstream Jenkinsfile is:

```text
jenkinsfiles/jenkinsfile.update-k8s-manifest
```

It checks out `ayleeee/weer-gitops`, updates `charts/weer/values-local.yaml`, commits the image tag change, and pushes to `main`.

## Async Trigger

The backend pipeline triggers the downstream job with `wait: false`.

The frontend pipeline does not use this GitOps handoff in the MVP. It builds the React app, uploads the static artifact to S3, and optionally invalidates CloudFront.

Reason:

- component pipelines do not need to block on GitOps update completion
- the backend pipeline stays decoupled from deployment state mutation
- GitOps update can be retried independently

Risk:

- the upstream job can succeed while the downstream GitOps update fails

Mitigation:

- store downstream job name/build URL when available
- archive `pipeline-metadata.json`
- add notifications for downstream failures
- document manual retry procedure

## Example Commit Message

```text
chore(gitops): update weer-backend image to weer-backend-42-a1b2c3d
```
