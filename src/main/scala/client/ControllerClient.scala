package client

import java.time.OffsetTime
import client.server.ControllerServerClient

trait ControllerClient {
    val servers: ControllerServerClient
}
