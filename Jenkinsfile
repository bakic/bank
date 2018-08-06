pipeline { 
    agent any 
    stages {
        stage('Compile') { 
            steps { 
				withMaven(maven: 'maven_3_5_4'){
					bat 'mvn clean compile'
				}  
            }
        }
        stage('Test'){
            steps {
                withMaven(maven: 'maven_3_5_4'){
					bat 'mvn test'
				}  
            }
        }
        stage('Deploy') {
            steps {
                withMaven(maven: 'maven_3_5_4'){
					bat 'mvn install'
				}  
            }
        }
    }
}