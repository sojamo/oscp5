import oscP5.*;
import netP5.*;

OscP5 osc;
NetAddress addr;

void setup() {
  osc = new OscP5(this, 12000);
  addr = new NetAddress("127.0.0.1", 12000);
}


void draw() {
}

void keyPressed() {
  switch(key) {
    case('1'):
    osc.send(addr, "/test", 1, 2, 3);
    break;
    case('2'):
    OscBundle bundle = new OscBundle();
    bundle.add(new OscMessage("/m/1", 1));
    bundle.add(new OscMessage("/m/2", 2));
    osc.send(addr, bundle);
    break;
  }
}

void oscEvent(OscBundle theBundle) {
  println("received a bundle", theBundle);
  for (OscMessage m : theBundle.get()) {
    oscEvent(m); /* forward each message inside the bundle to oscEvent(OscMessage) */
  }
}

void oscEvent(OscMessage theMessage) {
  println("received a message", theMessage);
}