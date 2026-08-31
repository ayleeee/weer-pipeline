# Pipeline Before / After

## Before

The original WeER pipelines were useful but mixed several concerns directly inside component Jenkinsfiles:

- source checkout
- environment preparation
- build/test commands
- image build and push
- cloud deployment commands
- GitOps manifest updates
- cleanup
- credential references

This made the pipeline hard to review as a reusable delivery system.

## After

The renewal design keeps component Jenkinsfiles focused on orchestration and extracts repeated behavior into shared library functions.

Backend and frontend Jenkinsfiles should show the same high-level flow:

```text
checkout
build/test
build image
push image
trigger GitOps handoff
record metadata
notify result
```

## Improvements

- Shared logic is reusable across backend and frontend.
- Jenkinsfiles are easier to scan in code review.
- Dockerfiles remain versioned and reviewable in application repositories.
- CI image publication is separated from GitOps manifest mutation.
- `wait: false` downstream handoff keeps CI jobs decoupled from CD jobs.
- Pipeline metadata preserves traceability from source commit to image tag/digest.

## Tradeoffs

- Asynchronous downstream jobs require stronger tracking.
- GitOps update failures can happen after the image build succeeds.
- A separate GitOps job needs retry and conflict handling.
- InfluxDB history is valuable but should be added after artifact/log metadata is working.
