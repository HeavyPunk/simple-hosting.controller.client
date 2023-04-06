package io.github.heavypunk.controller
package client

import java.time.OffsetTime
import io.github.heavypunk.controller.client.server.ControllerServerClient

trait ControllerClient {
    val servers: ControllerServerClient
}
