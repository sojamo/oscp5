import oscP5.*;
import netP5.*;
  
OscP5 osc;
NetAddress receiver;

void setup() {
  
  size( 400 , 400 );
  
  osc = new OscP5( this , 12000 );
  receiver = new NetAddress( "127.0.0.1" , 12000 );
  
  osc.addListener( new OscEventListener() {
    public void oscEvent(OscMessage m) {
      println("got a message : "+ m.getAddress() );
    }
  });
  
}


void draw() {
  background(0);  
}

void mousePressed() {
  osc.send( receiver , "/test" , 1 , 2 , 3 );
}



