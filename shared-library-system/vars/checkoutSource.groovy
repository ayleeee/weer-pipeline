def call(Map config = [:]) {
    def repositoryUrl = config.repositoryUrl ?: error('repositoryUrl is required')
    def branch = config.branch ?: 'main'

    checkout([
        $class: 'GitSCM',
        branches: [[name: "*/${branch}"]],
        userRemoteConfigs: [[url: repositoryUrl]]
    ])
}
