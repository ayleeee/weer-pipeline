def call(Map config = [:]) {
    def serviceName = config.serviceName ?: error('serviceName is required')
    def imageTag = config.imageTag ?: error('imageTag is required')
    def sourceCommit = config.sourceCommit ?: ''
    def upstreamBuildUrl = config.upstreamBuildUrl ?: ''
    def credentialsId = config.credentialsId ?: error('credentialsId is required')
    def message = "chore(gitops): update ${serviceName} image to ${imageTag}"

    sh """
        git config user.name 'weer-renewal-bot'
        git config user.email 'weer-renewal-bot@example.com'
        git status --short
        git add charts/weer/values-local.yaml
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
