# Original CI/CD Inventory

## Source Repositories

| Component | Repository | Files checked |
|---|---|---|
| Backend | `Woori-Emergency/WeER_backend` | `jenkinsfile`, `Dockerfile`, `build.gradle`, `docker-compose.yml`, `prometheus.yml` |
| Frontend | `Woori-Emergency/WeER_frontend` | `jenkinsfile`, `package.json` |

## Backend Pipeline Observations

Original backend Jenkins stages:

- workspace cleanup
- repository clone from `Woori-Emergency/WeER_backend`
- secret file injection into `src/main/resources/application-secret.yml`
- Gradle build using Java 21
- SonarQube analysis
- Docker image build
- image push to ECR
- placeholder GCR stage
- direct GitOps manifest update and push
- workspace/docker cleanup in `post`

Original backend build details:

- Java 21
- Gradle
- Spring Boot 3.3.5
- Actuator and Micrometer Prometheus registry are present
- Dockerfile packages the built JAR and runs it with `java -jar`

## Frontend Pipeline Observations

Original frontend Jenkins stages:

- workspace cleanup
- repository clone
- `.env` creation from Jenkins credentials
- React build using Node.js 18.3.1
- S3 upload
- GCS upload
- CloudFront invalidation

Original frontend build details:

- React 18
- `react-scripts`
- `npm run build`
- S3/GCS/CloudFront deployment path instead of container-first delivery

## Renewal Implications

- Keep backend/frontend Jenkinsfiles thin.
- Extract repeated checkout, build, image/static publish, handoff, metadata, and notification logic into shared library functions.
- Keep backend Dockerfiles versioned in application repositories; Jenkins builds from those Dockerfiles.
- Keep frontend delivery on the static hosting path: React build, S3 upload, optional CloudFront invalidation.
- Replace direct backend GitOps mutation inside app pipelines with a downstream `Update K8S Manifest` job.
- Preserve image tag/digest or artifact target, source commit, build URL, and downstream job information for traceability.

## Sensitive Values Found Or Suspected

Do not copy original values directly into the renewal repo.

- Jenkins credential IDs
- SonarQube host URL
- GitOps token / personal access token usage
- hard-coded Git user name and email
- private or internal repository URLs
- registry URLs
- cloud bucket names
- CloudFront distribution ID
- GCP credential and bucket names
- application secret file path
