pipeline {

    agent any
    stages {
       stage('Test'){
          steps{
            script {
                bat 'node -v'
                bat 'npm prune'
                bat 'npm install'
                bat 'npm test'
                script {
                  def mailRecipients = 'sureshkumar_a@outlook.com'
                  def jobName = currentBuild.fullDisplayName
                  emailext body: '''${SCRIPT, template="groovy-html.template"}''',
                  mimeType: 'text/html',
                  subject: "[Jenkins] ${jobName}",
                  to: "${mailRecipients}",
                  replyTo: "${mailRecipients}",
                  recipientProviders: [[$class: 'CulpritsRecipientProvider']]
              }
            }
        }
    }

       stage('Cleanup'){
         steps{
          echo 'prune and cleanup'
          bat 'npm prune'
         }
      }
  }
}