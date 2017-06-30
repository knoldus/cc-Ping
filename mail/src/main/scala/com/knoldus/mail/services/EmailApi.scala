package com.knoldus.mail.services

import java.util.Properties
import javax.mail.internet.{InternetAddress, MimeMessage}
import javax.mail.{Address, Message, Session}

import com.ping.models.EmailInfo

trait EmailApi extends {

  private val host: String = "smtp.gmail.com"
  private val protocol: String = "smtp"

  private val port: String = "587"
  private val starttls: String = "true"

  def send(emailInfo: EmailInfo, emailId: String, password: String): Option[Int] = {
    try {
      val props = new Properties
      props.put("mail.smtp.port", port)
      props.setProperty("mail.transport.protocol", protocol)
      props.setProperty("mail.smtp.starttls.enable", starttls)
      props.setProperty("mail.host", host)
      props.setProperty("mail.smtp.user", emailId)
      props.setProperty("mail.smtp.password", password)
      props.setProperty("mail.smtp.auth", "true")

      val session = Session.getDefaultInstance(props)
      val msg = new MimeMessage(session)
      val recipientAddress: Array[Address] = (emailInfo.to map { recipient => new InternetAddress(recipient) }).toArray
      val ccAddress: Array[Address] = (emailInfo.cc map { recipient => new InternetAddress(recipient) }).toArray
      val bccAddress: Array[Address] = (emailInfo.bcc map { recipient => new InternetAddress(recipient) }).toArray

      msg.setFrom(new InternetAddress(emailId))
      msg.addRecipients(Message.RecipientType.TO, recipientAddress)
      msg.addRecipients(Message.RecipientType.CC, ccAddress)
      msg.addRecipients(Message.RecipientType.BCC, bccAddress)
      msg.setSubject(emailInfo.subject)
      msg.setContent(emailInfo.content, "text/html")
      val transport = session.getTransport(protocol)
      transport.connect(host, emailId, password)
      transport.sendMessage(msg, msg.getAllRecipients)
      Some(emailInfo.to.length)
    } catch {
      case exception: Exception =>
        println("Error found while sending mail: ", exception)
        None
    }
  }
}

object EmailApiImpl extends EmailApi

