import oscP5.*;
import netP5.*;

OscP5 osc;
NetAddress receiver;

void setup() {
  size(400, 400);

  osc = new OscP5(this, 12000);
  receiver = new NetAddress("127.0.0.1", 12000);
  osc.plug(this, "test", "/test");
}

void test(float f) {
  println("got a test "+f);
  s0 = f;
}

float s0, s1;
void draw() {
  background(0);
  s1 += (s0-s1)* 0.05; 
  ellipse( width/2, height/2, s1, s1 );
}

void mousePressed() {
  osc.send(receiver, "/test", random( 100, 400 ) );
}

