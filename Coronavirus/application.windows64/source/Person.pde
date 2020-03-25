class Person {
  private int id;
  private String condition;
  private int age;
  private float radius, pos_x, pos_y, x_speed, y_speed;
  
  private int xdirection = 1;  // Left or Right
  private int ydirection = 1;  // Top to Bottom
  
  //Condition type
  private String conditiontype; //Can be 'mild', 'fatal'
  private boolean hasUnderlyingCondition;
  private boolean isCured; //Default value false
  
  //Color
  private int colorR, colorG, colorB;
  
  //Create empty constructor
  Person(){
  }
  
  //Healthy person constructor
  Person(int id, String condition, int age, float pos_x, float pos_y, float radius, float x_speed, float y_speed){
    this.id = id;
    this.condition = condition;
    this.age = age;
    this.pos_x = pos_x;
    this.pos_y = pos_y;
    this.radius = radius;
    this.x_speed = x_speed;
    this.y_speed = y_speed;
  }
  
  //Infected person constructor
  Person(int id, String condition, String condType, boolean hasUnderlyingCond, int age, float pos_x, float pos_y, float radius, float x_speed, float y_speed, boolean cured){
    this.id = id;
    this.condition = condition;
    this.conditiontype = condType;
    this.hasUnderlyingCondition = hasUnderlyingCond;
    this.age = age;
    this.pos_x = pos_x;
    this.pos_y = pos_y;
    this.radius = radius;
    this.x_speed = x_speed;
    this.y_speed = y_speed;
    this.isCured = cured;
  }
  
  
  void changeColorRGB(int newColorR, int newColorG, int newColorB){
    this.colorR = newColorR;
    this.colorG = newColorG;
    this.colorB = newColorB;
  }
  
  void changeColor(int newColor){
    this.colorR = newColor;
    this.colorG = newColor;
    this.colorB = newColor;
  }
 
  
  void move(){
    //this.pos_x += this.x_speed;
    //this.pos_y += this.y_speed;
    //if (this.pos_x > width - this.radius || this.pos_x < this.radius) {
    //  this.x_speed = -this.x_speed;
    //}
    //if (this.pos_y > height - this.radius || this.pos_y < this.radius) {
    //  this.y_speed = -this.y_speed;
    //}
    
    //Update the position of the shape
    this.pos_x = this.pos_x + ( this.x_speed * (xdirection + random(-1.1,1.1)) );
    this.pos_y = this.pos_y + ( this.y_speed * (ydirection + random(-1.1,1.1)) );
    
    // Test to see if the shape exceeds the boundaries of the screen
    // If it does, reverse its direction by multiplying by -1
    if (this.pos_x > width - this.radius || this.pos_x < this.radius) {
      xdirection *= -1;
      //xdirection *= random(-1.1,1.1);
      //this.pos_x += random(-1.1,1.1);
    }
    if (this.pos_y > height - this.radius || this.pos_y < this.radius) {
      ydirection *= -1;
      //ydirection *= random(-1.1,1.1);
      //this.pos_y += random(-1.1,1.1); 
    }
  }
  
  boolean isNearAnotherPerson(Person p){
    boolean isNear = false;
    if( dist(this.pos_x, this.pos_y, p.getPosX(), p.getPosY()) > this.radius + p.getRadius() ){
      isNear = true;
    }
    return isNear;
  }
  
  //Check if person is healthy
  boolean isHealthy(){
    boolean healthy = false;
     if(this.condition == "healthy"){
         healthy = true;
     }
     
     return healthy;
  }
  
  //Check if person is healthy
  boolean isInfected(){
    boolean infected = false;
     if(this.condition == "infected"){
         infected = true;
     }
     
     return infected;
  }
  
  void display(){
    fill(this.colorR, this.colorG, this.colorB);
    ellipse(this.pos_x, this.pos_y, this.radius*2, this.radius*2);
  }
  
  //Updates
  void updateHealth(String condition, String condType, boolean undCond) {
   this.condition = condition;
   this.conditiontype = condType;
   this.hasUnderlyingCondition = undCond;
  }
  void updateHealth(String condition, String condType) {
   this.condition = condition;
   this.conditiontype = condType;
  }
  
  //Update on cured
  void updateCured(){
    this.condition = "healthy";
    this.isCured = true;
  }
  
  
  
  ////////////////////////////////////////////////////
  //Setters
  ////////////////////////////////////////////////////
  //Set id
  void setId(int id) {
    this.id = id;
  }
  
  //Set condition
  void setCondition(String condition){
   this.condition = condition; 
  }
  
  void setConditionType (String condType) {
    this.conditiontype = condType;
  }
  
  void setUnderLyingCondition(boolean undCond) {
    this.hasUnderlyingCondition = undCond;
  }
  
  //Set age
  void setAge(int age){
    this.age = age;
  }
  
  //Set pos_x
  void setPosX(float x) {
    this.pos_x = x;
  }
  
  //Set pos_y
  void setPosY(float y) {
    this.pos_y = y;
  }
  
  //Set radius
  void setRadius(float r) {
    this.radius = r;
  }
  
  //Set x_speed
  void setXSpeed(float speed) {
    this.x_speed = speed;
  }
  
  //Set y_speed
  void setYSpeed(float speed) {
    this.y_speed = speed;
  }
  
  
  ////////////////////////////////////////////////////
  //Getters
  ////////////////////////////////////////////////////
  //Get condition
  String getCondition(){
   return this.condition;
  }
  
  String getConditionType() {
    return this.conditiontype;
  }
  
  boolean getUnderLyingCondition() {
    return this.hasUnderlyingCondition;
  }
  
  //Get age
  int getAge(){
    return this.age;
  }
  
  //Get pos_x
  float getPosX() {
    return this.pos_x;
  }
  
  //Get pos_y
  float getPosY() {
    return this.pos_y;
  }
  
  //Get radius
  float getRadius() {
    return this.radius;
  }
  
  //Get x_speed
  float getXSpeed() {
    return this.x_speed;
  }
  
  //Get y_speed
  float getYSpeed() {
    return this.y_speed;
  }
  
  int getId(){
    return this.id;
  }
  
}
