pipeline {

    agent any
    stages {
       stage('Test'){
          steps{
            script {
              try {
                bat 'node -v'
                bat 'npm prune'
                bat 'npm install'
                bat 'npm test'
              } catch (Exception e) {
                  echo 'Exception occurred: ' + e.toString()
                  mail body: "Jenkins - Build Error: ${env.BUILD_URL}" ,
                        from: 'suresha@ilink-systems.com.com',
                        replyTo: 'sureshkumar_a@outlook.com',
                        subject: 'Jenkins - Build failed',
                        to: 'sureshkumar_a@outlook.com'
                      throw e
              }
            }
          }
    }

       stage('Cleanup'){
         steps{
          echo 'prune and cleanup'
          bat 'npm prune'
          bat 'rm node_modules -rf'

          mail body: 'Jenkins - Build Successful',
              from: 'suresha@ilink-systems.com.com',
              replyTo: 'sureshkumar_a@outlook.com',
              subject: 'Jenkins - Build Passed',
              to: 'sureshkumar_a@outlook.com'
         }
      }
  }
}