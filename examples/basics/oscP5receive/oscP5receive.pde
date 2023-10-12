/**
 * oscP5parse 
 * example shows how to receive and extract values from an osc message 
 * via the oscEvent(OscMessage) method.
 *
 * by Andreas Schlegel, 2013
 * www.sojamo.de/libraries/oscP5
 *
 */
 
import oscP5.*;
import netP5.*;
  
OscP5 oscP5;

NetAddress receiver;

void setup() {
  
  size(400,400);

  /* start oscP5, listening for incoming messages at port 12000 */
  
  oscP5 = new OscP5( this , 12000 );
  
  /* receiver is a NetAddress. a NetAddress takes 2 parameters,
   * an ip address and a port number. receiver is used as parameter in
   * oscP5.send() when sending an osc message to another process, computer,
   * device, etc. see below. for testing purposes the listening port
   * and the port of the remote location address are the same, hence this 
   * program will send messages back to itself.
   */
   
  receiver = new NetAddress( "127.0.0.1" , 12000 );
}


float x0,x1;
float y0,y1;
float r0,r1;

void draw() {
  background(0);
  x1 += (x0-x1)* 0.05;
  y1 += (y0-y1)* 0.05;
  r1 += (r0-r1)* 0.05;
  translate(x1 , y1 );
  rotate( r1 );
  rect( 0 , 0 , 100 , 100 );  
}

void mousePressed() {
  /* send a message on mouse pressed */
  oscP5.send( receiver , "/change" , random(100,300) , random(100,300) , random(-PI,PI) );  
}


/* by default incoming osc message are forwarded to the oscEvent method. */

void oscEvent( OscMessage m ) {
  print( "Received an osc message" );
  print( ", address pattern: " + m.getAddress( ) );
  print( ", typetag: " + m.getTypetag( ) );
  print( ", from IP: " + m.getIP( ) );
  if(m.getAddress( ).equals("/change") && m.getTypetag().equals("fff")) {
    /* transfer receivd values to local variables */
    x0 = m.floatValue(0);
    y0 = m.floatValue(1);
    r0 = m.floatValue(2);
  }
  println();
}
