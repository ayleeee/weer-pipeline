# Sensitive Values

## Do Not Commit

- AWS access keys
- AWS account IDs
- full ARN values
- registry credentials
- GitHub personal access tokens
- Jenkins credential secret values
- webhook URLs
- private bucket names
- internal IPs or private domains
- real application secret files
- personal emails copied from old automation

## Safe Placeholder Examples

```text
registry.example.com/weer/backend
registry.example.com/weer/frontend
registry-credentials
gitops-repo-token
Update-K8S-Manifest
```

## Review Checklist

- Search for `AKIA`.
- Search for `secret`.
- Search for `token`.
- Search for `password`.
- Search for real email addresses.
- Search for private IP ranges.
- Search for cloud account IDs.
