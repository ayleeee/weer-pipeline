import groovy.json.JsonOutput

def call(Map config = [:]) {
    def metadata = [
        serviceName    : config.serviceName ?: '',
        buildNumber    : env.BUILD_NUMBER ?: '',
        buildUrl       : env.BUILD_URL ?: '',
        branchName     : env.BRANCH_NAME ?: '',
        sourceCommit   : config.sourceCommit ?: '',
        imageRepository: config.imageRepository ?: '',
        imageTag       : config.imageTag ?: '',
        imageDigest    : config.imageDigest ?: '',
        buildResult    : config.buildResult ?: '',
        recordedAt     : new Date().format("yyyy-MM-dd'T'HH:mm:ssXXX")
    ]

    writeFile file: 'pipeline-metadata.json', text: JsonOutput.prettyPrint(JsonOutput.toJson(metadata))
    archiveArtifacts artifacts: 'pipeline-metadata.json', onlyIfSuccessful: false
}
