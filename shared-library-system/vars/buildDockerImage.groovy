def call(Map config = [:]) {
    def imageRepository = config.imageRepository ?: error('imageRepository is required')
    def imageTag = config.imageTag ?: error('imageTag is required')
    def dockerfilePath = config.dockerfilePath ?: 'Dockerfile'
    def buildArgs = config.buildArgs ?: [:]

    def buildArgFlags = buildArgs.collect { key, value -> "--build-arg ${key}='${value}'" }.join(' ')

    sh """
        docker build \
          -f ${dockerfilePath} \
          -t ${imageRepository}:${imageTag} \
          ${buildArgFlags} \
          .
    """
}
