def call(Map config = [:]) {
    def buildDir = config.buildDir ?: 'build'
    def bucketUri = config.bucketUri ?: error('bucketUri is required')
    def distributionId = config.distributionId ?: ''
    def credentialsId = config.credentialsId ?: error('credentialsId is required')
    def region = config.region ?: 'ap-northeast-2'

    withCredentials([[
        $class: 'AmazonWebServicesCredentialsBinding',
        credentialsId: credentialsId
    ]]) {
        sh """
            test -d '${buildDir}'
            aws s3 sync '${buildDir}/' '${bucketUri}/' --delete --region '${region}'
        """

        if (distributionId?.trim() && distributionId != 'CLOUDFRONT_DISTRIBUTION_ID_PLACEHOLDER') {
            sh """
                aws cloudfront create-invalidation \
                  --distribution-id '${distributionId}' \
                  --paths '/*' \
                  --region '${region}'
            """
        } else {
            echo 'CloudFront invalidation skipped.'
        }
    }
}
