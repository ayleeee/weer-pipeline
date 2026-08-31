def call(Map config = [:]) {
    def jobName = config.jobName ?: error('jobName is required')
    def waitForCompletion = config.get('waitForCompletion', false)

    build(
        job: jobName,
        wait: waitForCompletion,
        propagate: false,
        parameters: [
            string(name: 'SERVICE_NAME', value: config.serviceName ?: ''),
            string(name: 'IMAGE_REPOSITORY', value: config.imageRepository ?: ''),
            string(name: 'IMAGE_TAG', value: config.imageTag ?: ''),
            string(name: 'IMAGE_DIGEST', value: config.imageDigest ?: ''),
            string(name: 'SOURCE_COMMIT', value: config.sourceCommit ?: ''),
            string(name: 'UPSTREAM_BUILD_URL', value: env.BUILD_URL ?: '')
        ]
    )
}
