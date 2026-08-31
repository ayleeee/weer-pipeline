def call(Map config = [:]) {
    def imageRepository = config.imageRepository ?: error('imageRepository is required')
    def imageTag = config.imageTag ?: error('imageTag is required')
    def credentialsId = config.credentialsId ?: error('credentialsId is required')

    withCredentials([usernamePassword(credentialsId: credentialsId, usernameVariable: 'REGISTRY_USER', passwordVariable: 'REGISTRY_PASSWORD')]) {
        sh """
            echo "\$REGISTRY_PASSWORD" | docker login ${imageRepository.split('/')[0]} --username "\$REGISTRY_USER" --password-stdin
            docker push ${imageRepository}:${imageTag}
        """
    }

    return sh(
        script: "docker inspect --format='{{index .RepoDigests 0}}' ${imageRepository}:${imageTag} 2>/dev/null | awk -F'@' '{print \$2}' || true",
        returnStdout: true
    ).trim()
}
