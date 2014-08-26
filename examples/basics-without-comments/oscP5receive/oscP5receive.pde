import oscP5.*;
import netP5.*;
  
OscP5 osc;

NetAddress receiver;

void setup() {
  size(400,400);
  osc = new OscP5( this , 12000 );
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
  osc.send( receiver , "/change" , random(100,300) , random(100,300) , random(-PI,PI) );  
}

void oscEvent( OscMessage m ) {
  print( "Received an osc message" );
  print( ", address pattern: " + m.getAddress( ) );
  print( ", typetag: " + m.getTypetag( ) );
  if(m.getAddress( ).equals("/change") && m.getTypetag().equals("fff")) {
    /* transfer receivd values to local variables */
    x0 = m.floatValue(0);
    y0 = m.floatValue(1);
    r0 = m.floatValue(2);
  }
  println();
}


