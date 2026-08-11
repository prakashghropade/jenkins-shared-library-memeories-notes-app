
def call (Map config = [:]) {

    def status = config.status ?: currentBuild.currentResult
    def recipient = config.recipient

    emailtext(

        to: recipient
        subject: "${status}: ${env.JOB_NAME} #${env.BUILD_NUMBER}"
        body: """
            <h2>Jenkins Build Notification</h2>

            <p><b>Job:</b> #${env.JOB_NAME}</p>
            <p><b>Build:</b> #${env.BUILD_NUMBER}</p>
            <p><b>Status:</b> ${status}</p>
            <p><b>Branch:</b> ${env.BRANCH_NAME} ?: 'N/A'}</p>
            <p><b>Build URL:</b> ${env.BUILD_URL}</p>
        """,

        mimeType: 'text/html'

    )

}