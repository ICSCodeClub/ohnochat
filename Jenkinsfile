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
    
    }
}
