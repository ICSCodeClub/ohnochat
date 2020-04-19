pipeline { 
    agent any
    stages { 
        stage('clean up before build') {
            steps {
                deleteDir()
        }
    }
        stage('test') { 
            steps { 
               echo 'test with maven' 
               sh '''
               git clone https://github.com/gitcloneguy/ohnochat.git
               cd ohnochat
               mvn install -Dmaven.javadoc.skip=true -B -V
               mvn package
               '''
            }
        }
        stage ("Extract test results") {
            steps{
            cobertura coberturaReportFile: 'coverage.xml'
            export CODECOV_TOKEN="5521a705-1363-4ec7-bcb2-6e4c748edb26
            sh bash <(curl -s https://codecov.io/bash)
                      }
}
    
    }
}
