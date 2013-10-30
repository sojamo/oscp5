import oscP5.*;
import netP5.*;

OscP5 osc;

NetAddress receiver;

float r,g,b;

void setup() {
  size( 400 , 400 );
  
  osc = new OscP5( this , 12000 );
  receiver = new NetAddress( "127.0.0.1" , 12000 );
}

void draw() {
  background( r , g , b );
}

void mousePressed() {
  osc.send( receiver , "/test" , random( 255 ) , random( 255 ) , random( 255 ) );  
}

void oscEvent( OscMessage m ) {
  if( m.getAddress().equals( "/test") ) {
    r = m.floatValue( 0 );
    g = m.floatValue( 1 );
    b = m.floatValue( 2 );
  }  
}


