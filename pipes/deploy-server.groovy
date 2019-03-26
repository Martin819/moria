buildJobNumber = -1

node {
   stage('Run Build job') {
       buildJob = build job: "build-server"
   }
   stage('Copy artifact') {
       buildJobNumber = buildJob.getNumber()
       if (buildJobNumber > -1) {
           buildJobResult = buildJob.getResult()
           if (buildJobResult.equals("SUCCESS")) {
               println(buildJobNumber)
               copyArtifacts excludes: '*.txt, *.xml', filter: 'target/*.jar', fingerprintArtifacts: true, projectName: 'build-server', selector: specific("${buildJobNumber}")
           } else {
               println("[ERROR] Build job failed! Actual result: ${buildJob.getResult()}")
           }
       } else {
           println("[ERROR] Could not retrieve build job number!")
       }
   }
   stage('Deploy') {
       sshPublisher(publishers: [sshPublisherDesc(configName: 'moria-cloud-3', transfers: [sshTransfer(cleanRemote: false, excludes: '', execCommand: 'systemctl start deploy-server.service', execTimeout: 120000, flatten: false, makeEmptyDirs: false, noDefaultExcludes: false, patternSeparator: '[, ]+', remoteDirectory: 'server', remoteDirectorySDF: false, removePrefix: '', sourceFiles: 'target/*.jar')], usePromotionTimestamp: false, useWorkspaceInPromotion: false, verbose: true)])
   }
}