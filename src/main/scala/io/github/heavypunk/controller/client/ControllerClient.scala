package io.github.heavypunk.controller
package client

import java.time.OffsetTime
import io.github.heavypunk.controller.client.server.ControllerServerClient
import io.github.heavypunk.controller.client.state.ControllerStateClient
import io.github.heavypunk.controller.client.files.ControllerFilesClient

trait ControllerClient {
    val servers: ControllerServerClient
    val state: ControllerStateClient
    val files: ControllerFilesClient
}

class CommonControllerClient(
    serversClient: ControllerServerClient,
    stateClient: ControllerStateClient,
    filesClient: ControllerFilesClient,
) extends ControllerClient {

    override val state: ControllerStateClient = stateClient
    override val servers: ControllerServerClient = serversClient
    override val files: ControllerFilesClient = filesClient
}
