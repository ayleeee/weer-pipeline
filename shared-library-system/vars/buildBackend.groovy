def call(Map config = [:]) {
    def testEnabled = config.get('testEnabled', true)

    sh 'chmod +x gradlew'

    if (testEnabled) {
        sh './gradlew clean build --stacktrace'
    } else {
        sh './gradlew clean build -x test --stacktrace'
    }
}
