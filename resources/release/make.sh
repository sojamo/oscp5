cd $HOME/Documents/workspace/oscP5/target/classes
jar cf ../oscP5.jar .
cp ../oscP5.jar $HOME/Documents/Processing3/libraries/oscP5/library
echo "oscP5 compiled on $(date)"