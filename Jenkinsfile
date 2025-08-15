pipeline
{
    agent any
    
    tools{
        maven 'maven'
    }
    
    stages
    {
        
        stage('Build')
        
        steps
        {
            
        }
        
        post
        {
            success
            {
                
            }
        }
        
        stage('Deploy to QA')
        {
            steps{
                echo("deploy to qa")
            }
        }
        
        stage("Regression Automation Test"){
            
            steps{
                cacheError(buildResult:'SUCCESS',stageResult:'FAILURE'){
                    git:'https://github.com/omgoyal/PlaywrightFramework'
                    sh:"mvn clean test -Dsurefire.suiteXmlFiles=testng_regression.xml"
                }
            }
        }
        
        stage('Publish Extent Report'){
            steps{
                publishHTML(allowMissing:false,
                alwaysLinkToLastBuild:false,
                keepAll:true,
                reportDir:'build',
                reportFiles:'TestExecutionReport*.html',
                reportName:'HTML Extent Report',
                reportTitles:''
                )
                
            }
        }
        
    }
}