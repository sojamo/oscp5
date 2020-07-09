/**
 * OSC SendReceive example
 *
 * sends an OSC Message on mouseReleased to itself.
 *
 * for more details see https://github.com/sojamo/oscp5
 * and the wiki at https://github.com/sojamo/oscp5/wiki
 *
 * 2020
 */

import oscP5.*;

OscP5 osc;
NetAddress remote;
int bg, fg;

void setup() {
  size(800, 400);
  osc = new OscP5(this, 12000);
  remote = new NetAddress("127.0.0.1", 12000);
}

void draw() {
  background(bg);
}

void mouseReleased() {
  int c = frameCount%255;
  OscMessage msg = new OscMessage("/bg", c);
  osc.send(remote, msg);
}

void oscEvent(OscMessage m) {
  if (m.matches("/bg", "i")) {
    bg = m.getIntAt(0);
  }
}
