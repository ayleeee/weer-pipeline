def call(Map config = [:]) {
    def serviceName = config.serviceName ?: error('serviceName is required')
    def imageRepository = config.imageRepository ?: error('imageRepository is required')
    def imageTag = config.imageTag ?: error('imageTag is required')
    def valuesFile = config.valuesFile ?: 'charts/weer/values-local.yaml'
    def chartServiceName = serviceName.replace('weer-', '')

    sh """
        chmod +x scripts/update-image-tag.sh
        scripts/update-image-tag.sh '${chartServiceName}' '${imageRepository}' '${imageTag}' '${valuesFile}'
        git diff -- '${valuesFile}'
    """
}
