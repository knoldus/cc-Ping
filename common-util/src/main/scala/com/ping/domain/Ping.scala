package com.ping.domain

import com.ping.models.RDPingLogView
import org.joda.time.DateTime

case class Ping(
                 slack: Option[PingSlack] = None,
                 mail: Option[PingEmail] = None,
                 message: Option[TwilioMessage] = None
               )


case class PingResponse(
                         slack: Option[RDPingLogView],
                         mail: Option[RDPingLogView],
                         message: Option[RDPingLogView]
                       )

case class TwilioMessage(
                        to: String,
                        text: String
                      )

case class PingEmail(
                      to: List[String],
                      cc: List[String],
                      bcc: List[String],
                      subject: String,
                      content: String
                    )

case class PingSlack(
                      channelId: String,
                      message: String
                    )


object MessageType {
  val mail = "mail"
  val slack = "slack"
  val message = "phone_mgs"
}

object PingStatus {
  val initiated = "initiated"
  val pending = "pending"
  val inProgress = "in_progress"
  val sent = "sent"
  val delivered = "delivered"
  val failed = "failed"
}


case class PingLog(
                     uuid: String,
                     messageType: String,
                     message: String,
                     to: String,
                     sentAt: DateTime,
                     status: String = PingStatus.initiated
                   )