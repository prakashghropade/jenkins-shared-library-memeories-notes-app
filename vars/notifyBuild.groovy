
def call (Map config = [:]) {

    def status = config.status ?: currentBuild.currentResult
    def recipient = config.recipient

    mail(

        to: recipient,
        subject: "${status}: ${env.JOB_NAME} #${env.BUILD_NUMBER}",
        body: """
                    CI Pipeline completed successfully.

                    Job: ${env.JOB_NAME}    
                    Build: ${env.BUILD_NUMBER}
                    Status: SUCCESS
                    Branch: ${env.BRANCH_NAME}
                    Build URL: ${env.BUILD_URL}
                    """,
    )

}