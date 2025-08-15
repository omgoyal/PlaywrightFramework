pipeline {
    agent any

    tools {
        maven '3.9.9'  // Use the Maven name from Jenkins Global Tool Configuration
    }

    stages {
        stage('Build') {
            steps {
                echo 'Building the project...'
            }
        }

        stage('Deploy to QA') {
            steps {
                echo "Deploying to QA..."
            }
        }

        stage("Regression Automation Test") {
            steps {
                git 'https://github.com/omgoyal/PlaywrightFramework'
                sh 'mvn clean test -Dsurefire.suiteXmlFiles=testng_regression.xml'
            }
        }

        stage('Publish Extent Report') {
            steps {
                publishHTML([
                    allowMissing: false,
                    alwaysLinkToLastBuild: false,
                    keepAll: true,
                    reportDir: 'build',
                    reportFiles: 'TestExecutionReport*.html',
                    reportName: 'HTML Extent Report',
                    reportTitles: ''
                ])
            }
        }
    }

    post {
        success {
            echo 'Pipeline completed successfully!'
        }
        failure {
            echo 'Pipeline failed!'
        }
    }
}
