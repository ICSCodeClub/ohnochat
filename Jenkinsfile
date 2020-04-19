pipeline {
  agent any

  stages{
    stage('Check run PoC') { 
      steps {
        withCredentials([usernamePassword(credentialsId: 'githubapp-jenkins', usernameVariable: 'GITHUB_APP', passwordVariable: 'GITHUB_JWT_TOKEN')]) {
        sh '''
        curl -H "Content-Type: application/json" -H "Accept: application/vnd.github.antiope-preview+json" -H "authorization: Bearer ${GITHUB_JWT_TOKEN}" -d '{ "name": "check_run", "head_sha": "'${GIT_COMMIT}'", "status": "in_progress", "external_id": "42", "started_at": "2020-03-05T11:14:52Z", "output": { "title": "Check run from Jenkins!", "summary": "This is a check run which has been generated from Jenkins as GitHub App", "text": "...and that iss awesome"}}' https://github.xyz.com/api/v3/repos/ojacques/jenkins-with-githubapp/check-runs
        '''
        }
      }
    }
  }
}
