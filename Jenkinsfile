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
                bat "mvn clean test -Dsurefire.suiteXmlFiles=testng_regression.xml -Dsurefire.skipAfterFailureCount=0 -Dmaven.test.failure.ignore=true"
            }
            
            
        }

        stage('Publish Extent Report') {
			 when {
                always() // Run regardless of earlier failures
            }
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
        always {
            echo "Pipeline finished — check the Extent report for details."
        }
        failure {
            echo 'Pipeline failed!'
        }
    }
}
