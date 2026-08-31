def call(Map config = [:]) {
    def repositoryUrl = config.repositoryUrl ?: error('repositoryUrl is required')
    def branch = config.branch ?: 'main'
    def credentialsId = config.credentialsId ?: null

    def remoteConfig = [url: repositoryUrl]
    if (credentialsId) {
        remoteConfig.credentialsId = credentialsId
    }

    checkout([
        $class: 'GitSCM',
        branches: [[name: "*/${branch}"]],
        userRemoteConfigs: [remoteConfig]
    ])
}
