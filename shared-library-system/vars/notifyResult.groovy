def call(Map config = [:]) {
    def serviceName = config.serviceName ?: 'service'
    def buildResult = config.buildResult ?: 'UNKNOWN'

    echo "[${serviceName}] pipeline result: ${buildResult}"
}
