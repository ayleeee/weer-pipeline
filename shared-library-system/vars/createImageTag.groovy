def call(Map config = [:]) {
    def serviceName = config.serviceName ?: 'service'
    def commit = env.GIT_COMMIT ? env.GIT_COMMIT.take(7) : 'unknown'

    return "${serviceName}-${env.BUILD_NUMBER}-${commit}"
}
