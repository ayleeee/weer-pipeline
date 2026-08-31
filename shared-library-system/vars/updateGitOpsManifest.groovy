def call(Map config = [:]) {
    def serviceName = config.serviceName ?: ''
    def imageRepository = config.imageRepository ?: ''
    def imageTag = config.imageTag ?: ''
    def sourceCommit = config.sourceCommit ?: ''
    def upstreamBuildUrl = config.upstreamBuildUrl ?: ''
    def valuesFile = config.valuesFile ?: 'charts/weer/values-local.yaml'
    def credentialsId = config.credentialsId ?: error('credentialsId is required')

    if (serviceName != 'weer-backend') {
        error("SERVICE_NAME must be weer-backend for the current GitOps MVP. Actual: ${serviceName}")
    }

    if (!imageRepository?.trim()) {
        error('IMAGE_REPOSITORY is required')
    }

    if (!imageTag?.trim()) {
        error('IMAGE_TAG is required')
    }

    def chartServiceName = serviceName.replace('weer-', '')
    def message = "chore(gitops): update ${serviceName} image to ${imageTag}"

    sh """
        chmod +x scripts/update-image-tag.sh
        scripts/update-image-tag.sh '${chartServiceName}' '${imageRepository}' '${imageTag}' '${valuesFile}'
        git diff -- '${valuesFile}'
        git config user.name 'weer-renewal-bot'
        git config user.email 'weer-renewal-bot@example.com'
        git add '${valuesFile}'
        git commit -m '${message}' \
          -m 'Source commit: ${sourceCommit}' \
          -m 'Upstream build: ${upstreamBuildUrl}' || echo 'No GitOps changes to commit.'
    """

    withCredentials([usernamePassword(credentialsId: credentialsId, usernameVariable: 'GIT_USER', passwordVariable: 'GIT_TOKEN')]) {
        sh """
            git push https://"\$GIT_USER":"\$GIT_TOKEN"@github.com/ayleeee/weer-gitops.git HEAD:main
        """
    }
}
