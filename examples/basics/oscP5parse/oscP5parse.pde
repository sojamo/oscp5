import oscP5.*;
import netP5.*;
  
OscP5 osc;
NetAddress receiver;

void setup() {
  size( 400 , 400 );
  osc = new OscP5( this , 12000 );
  receiver = new NetAddress( "127.0.0.1" , 12000 );

}


void draw() {
  background(0);  
}

void mousePressed() {
  osc.send( receiver , "/test" , 1 , 2.0 , "three" , new byte[] {0x00 , 0x10 , 0x20 }, true , false , 100);
}


void oscEvent( OscMessage m) {
  
  if( m.getAddress().equals("/test") ) {
    
    int     v0 = m.intValue( 0 );
    float   v1 = m.floatValue( 1 );
    String  v2 = m.stringValue( 2 );
    byte[]  v3 = m.bytesValue( 3 );
    boolean v4 = m.booleanValue( 4 );
    boolean v5 = m.booleanValue( 5 );
    int     v6 = m.intValue( 6 );
    
    println( "Address\t" + m.getAddress() );
    println( "Ffrom IP: " + m.getIP( ) );
    println( "Typetag\t" + m.getTypetag() );
    println( "Arguments\t" + v0 +" , "+ v1 +" , "+ v2  +" , "+ v3 +" , "+ v4 +" , "+ v5 +" , "+v6);
    
    for( Object o : m.getArguments() ) {
      println( o.getClass().getSimpleName() + "\t" + o );
    }
    println();
    
  }
}
