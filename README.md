# WeER Pipeline

WeER Pipeline is the Jenkins renewal repository for the WeER Renewal portfolio project.

The original WeER team project used Jenkins-based CI/CD for backend and frontend delivery. This repository rebuilds that pipeline as a cleaner, reusable Jenkins structure:

- `jenkinsfile.backend` builds/tests the backend, builds a Docker image, pushes it to a registry, and asynchronously hands off image metadata to a GitOps manifest update job.
- `jenkinsfile.frontend` builds/tests the frontend, builds a Docker image or static artifact, pushes it to a registry, and uses the same GitOps handoff contract.
- `jenkinsfile.update-k8s-manifest` is the downstream job that updates the GitOps repository after an image is published.
- `shared-library-system/` contains reusable Jenkins shared library steps extracted from repeated pipeline logic.
- `docs/` records the original CI/CD evidence, before/after design decisions, shared-library extraction criteria, Jenkins setup, GitOps handoff contract, and sensitive-value handling.

## MVP Flow

```text
Application repository
  -> Jenkins backend/frontend pipeline
  -> build and test
  -> Docker image build from a versioned Dockerfile
  -> image push
  -> collect image tag, digest, git commit SHA, build URL
  -> trigger "Update K8S Manifest" downstream job with wait: false
  -> preserve build metadata as artifact/log

GitOps repository
  -> update Helm values or Kubernetes manifest image tag
  -> commit and push
  -> Argo CD syncs to k3s
```

## Repository Boundary

This repository owns Jenkins pipeline design and the handoff contract to GitOps.

It does not own:

- Helm chart implementation
- Argo CD Application details
- k3s cluster setup
- Terraform AWS reference architecture
- Prometheus/Grafana deployment

Those belong to separate WeER Renewal infrastructure/GitOps documentation.

## Status

Initial scaffold. No real production credentials or private endpoints are included.
