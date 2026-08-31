def call(Map config = [:]) {
    def serviceName = config.serviceName ?: ''
    def imageRepository = config.imageRepository ?: ''
    def imageTag = config.imageTag ?: ''

    if (!(serviceName in ['weer-backend', 'weer-frontend'])) {
        error("SERVICE_NAME must be weer-backend or weer-frontend. Actual: ${serviceName}")
    }

    if (!imageRepository?.trim()) {
        error('IMAGE_REPOSITORY is required')
    }

    if (!imageTag?.trim()) {
        error('IMAGE_TAG is required')
    }
}
