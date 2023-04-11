package io.github.heavypunk.controller
package client

import java.time.OffsetTime
import io.github.heavypunk.controller.client.server.ControllerServerClient

trait ControllerClient {
    val servers: ControllerServerClient
}

class CommonControllerClient(
    serversClient: ControllerServerClient
) extends ControllerClient {

  override val servers: ControllerServerClient = serversClient
}
