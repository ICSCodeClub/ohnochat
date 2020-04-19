pipeline { 
    agent linux-2
    tools { 
        maven 'Maven 3.3.9' 
        jdk 'jdk8' 
    }
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
