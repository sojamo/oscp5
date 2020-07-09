/**
 * OscSendReceive example
 *
 * sends an OSC Message to itself when mouseReleased 
 *
 * for more details see https://github.com/sojamo/oscp5
 * and the wiki at https://github.com/sojamo/oscp5/wiki
 *
 * 2020
 */

import oscP5.*;

OscP5 osc;
NetAddress remote;
int bg;

void setup() {
  size(800, 400);
  osc = new OscP5(this, 12000);
  remote = new NetAddress("127.0.0.1", 12000);
  
}

void draw() {
  background(bg);
}

void mouseReleased() {
  OscMessage msg = new OscMessage("/bg");
  msg.add(frameCount%255);
  osc.send(remote, msg);
}

void oscEvent(OscMessage m) {
  
  // lets see what kind of message we received
  println(m.getAddress(),m.getTypetag());
  
  // check if the message matches an expected 
  // address (/bg) and typtag (i for integer)
  
  if (m.matches("/bg", "i")) {
    // get the first argument as Integer
    // and assign to bg
    bg = m.getIntAt(0);
    // other types are float, String, True, False, etc.
    // for a list of available types, check 
    // https://github.com/sojamo/oscp5/wiki
  }
}
