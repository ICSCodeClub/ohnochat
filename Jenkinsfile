pipeline { 
    agent ('master')
    stages { 
        stage('test') { 
            steps { 
               echo 'test with maven' 
               sh '''
               git clone https://github.com/gitcloneguy/ohnochat.git
               mvn install -Dmaven.javadoc.skip=true -B -V
               mvn package
               '''
            }
        }
    }
}
