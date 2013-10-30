/**
 * oscP5plug
 * example shows how to link an osc message to a function of an object.
 *
 * by Andreas Schlegel, 2013
 * www.sojamo.de/libraries/oscP5
 *
 */
 
import oscP5.*;
import netP5.*;
  
OscP5 osc;
NetAddress receiver;

void setup() {
  size(400,400);
  
  /* start oscP5, listening for incoming messages at port 12000 */
  osc = new OscP5(this,12000);
  
  /* we will send osc message to ourself */
  receiver = new NetAddress("127.0.0.1",12000);
  
  /* plug function test of this (the main sketch object) to osc message /test */
  osc.plug(this, "test" , "/test");
}

// expects an osc packet with address pattern /test and typetag f
void test(float f) {
  println("got a test "+f);
  s0 = f; /* transfer received value to local variable */
}

float s0,s1;
void draw() {
  background(0);
  s1 += (s0-s1)* 0.05; 
  ellipse( width/2 , height/2 , s1 , s1 );
}

void mousePressed() {
  /* send an osc message to receiver */
  osc.send(receiver, "/test" , random( 100 , 400 ) ); 
}

