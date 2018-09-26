package com.ljwm.gecko.im.packet;

import lombok.Data;
import org.tio.core.intf.Packet;

@Data
public class TioPacket extends Packet {

  private String body;
}
