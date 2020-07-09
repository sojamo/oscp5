/**
 * OSC Bundle example
 *
 * sends an OSC Bundle with 2 OSC Messages and a Timetag
 * to itself on mouseReleased. 
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
  noStroke();
  fill(fg);
  rect(100, 100, width-200, height-200);
}

void mouseReleased() {
  int c = frameCount%255;
  OscMessage m0 = new OscMessage("/bg", c);
  OscMessage m1 = new OscMessage("/fg", 255 - c);
  
  OscBundle bundle = new OscBundle();
  OscTimetag t = new OscTimetag().setFutureTimeMillis(1000);
  
  bundle.setTimetag(t);
  bundle.add(m0, m1);
  osc.send(remote,bundle);
  
}

void oscEvent(OscMessage m) {
  println(m);
  if (m.matches("/bg", "i")) {
    bg = m.getIntAt(0);
  } else if (m.matches("/fg", "i")) {
    fg = m.getIntAt(0);
  }
}
