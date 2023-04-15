package io.github.heavypunk.controller
package client

import java.time.OffsetTime
import io.github.heavypunk.controller.client.server.ControllerServerClient
import io.github.heavypunk.controller.state.ControllerStateClient

trait ControllerClient {
    val servers: ControllerServerClient
    val state: ControllerStateClient
}

class CommonControllerClient(
    serversClient: ControllerServerClient,
    stateClient: ControllerStateClient,
) extends ControllerClient {

    override val state: ControllerStateClient = stateClient
    override val servers: ControllerServerClient = serversClient
}
