def call(Map config = [:]) {
    def installCommand = config.installCommand ?: 'npm ci'
    def testCommand = config.testCommand ?: 'npm test -- --watchAll=false'
    def buildCommand = config.buildCommand ?: 'npm run build'
    def testEnabled = config.get('testEnabled', true)

    sh installCommand

    if (testEnabled) {
        sh testCommand
    }

    sh buildCommand
}
