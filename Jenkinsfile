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
               git clone https://github.com/ICSCodeClub/ohnochat.git
               cd ohnochat
               mvn clean package
               mvn test
               '''
            }
        }
    
    }
}
