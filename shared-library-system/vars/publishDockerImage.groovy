def call(Map config = [:]) {
    def imageRepository = config.imageRepository ?: error('imageRepository is required')
    def imageTag = config.imageTag ?: error('imageTag is required')
    def dockerfilePath = config.dockerfilePath ?: 'Dockerfile'
    def credentialsId = config.credentialsId ?: error('credentialsId is required')
    def buildArgs = config.buildArgs ?: [:]
    def registryHost = imageRepository.split('/')[0]
    def buildArgFlags = buildArgs.collect { key, value -> "--build-arg ${key}='${value}'" }.join(' ')

    sh """
        docker build \
          -f ${dockerfilePath} \
          -t ${imageRepository}:${imageTag} \
          ${buildArgFlags} \
          .
    """

    withCredentials([usernamePassword(credentialsId: credentialsId, usernameVariable: 'REGISTRY_USER', passwordVariable: 'REGISTRY_PASSWORD')]) {
        sh """
            echo "\$REGISTRY_PASSWORD" | docker login ${registryHost} --username "\$REGISTRY_USER" --password-stdin
            docker push ${imageRepository}:${imageTag}
        """
    }

    return sh(
        script: "docker inspect --format='{{index .RepoDigests 0}}' ${imageRepository}:${imageTag} 2>/dev/null | awk -F'@' '{print \$2}' || true",
        returnStdout: true
    ).trim()
}
