def call(Map config = [:]) {
    def installCommand = config.installCommand ?: ''
    def buildCommand = config.buildCommand ?: error('buildCommand is required')
    def buildCommandWithoutTests = config.buildCommandWithoutTests ?: buildCommand
    def testCommand = config.testCommand ?: ''
    def testEnabled = config.get('testEnabled', true)

    if (installCommand?.trim()) {
        sh installCommand
    }

    if (testEnabled && testCommand?.trim()) {
        sh testCommand
    }

    sh(testEnabled ? buildCommand : buildCommandWithoutTests)
}
