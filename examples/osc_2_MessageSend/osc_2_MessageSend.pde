/**
 * oscP5
 * MessageSend example
 *
 * sends an OSC Message to itself on mouseReleased. 
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
  OscMessage m = new OscMessage("/bg");
  m.add(frameCount%255);
  osc.send(remote, m);
}

void oscEvent(OscMessage m) {
  println(m);
  if (m.matches("/bg", "i")) {
    bg = m.getIntAt(0);
  }
}
