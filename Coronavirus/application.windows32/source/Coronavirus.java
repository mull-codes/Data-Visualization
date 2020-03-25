import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class Coronavirus extends PApplet {

//Coronavirus simulation
//The purpose is to analyse social distance of each person and simulate desease over time
//Tracking:
//Heathly
//Effected
//Cured

//Number of people in this scene
int totalPopulation = 100;
int totalInfected = 0;
int totalCured = 0;

//Initialise a list of people
Person[] people;

//Infected person
Person infectedPerson;

//Healthy person
Person healthyPerson;

//Person radius
float radius = 5;
float speed_x = 0.8f, speed_y = 0.2f;

//Default color
int defaultColor = 255;


//Simulation time tracking
int timerStart = 0;
int offset;

int mill;
int minutes;
int seconds;
int hundredths;

boolean stopped = false;
boolean continued = false;


//Default position settings
float randX;
float randY;

public void setup() {
  //size(displayWidth, displayWidth);
  
  
  
  ///////////////////////////////////////////////////////////////////
  //TEST DATA
  ///////////////////////////////////////////////////////////////////
  //float randX = newRandom(random((0+radius), (width - radius)), "x");
  //float randY = newRandom( random((0+radius), (height - radius)), "y");
  
  ////println("PosX: " + randX + " | PosY: " + randY );
  
  //infectedPerson = new Person("infected", 55, width-40, height-40, radius, speed_x, speed_y);
  //infectedPerson.changeColor(defaultColor);
  //healthyPerson = new Person("infected", 55, ((width/2) + 5), ((height/2) + 5), radius, speed_x, speed_y);
  //healthyPerson.changeColor(defaultColor);
  ///////////////////////////////////////////////////////////////////
  
  //Initialize people array
  people = new Person[totalPopulation];
  //Populate people
  for(int i = 0; i < totalPopulation; i++){
    //Get random x co-ordinate
    randX = newRandom(random((0+radius), (width - radius)), "x");
    //Get random y co-ordinate
    randY = newRandom( random((0+radius), (height - radius)), "y");
    //Check if i = 0, we have the first element in the array, infect this person as main source of the virus
    if(i == 0){
      //Initialize infected person
      people[i] = new Person(i, "infected", "mild", true, 55, randX, randY, radius, speed_x, speed_y, false);
      //Change infected person's color to red
      people[i].changeColorRGB(255,0,0); //255,0,0 = red for infected
      //Increase the infected person counter by 1
      totalInfected += 1;
    }else{
      //Initialize everyone else
      people[i] = new Person(i, "healthy", 55, randX, randY, radius, speed_x, speed_y);
      //Set everyone else's color to white
      people[i].changeColor(defaultColor);
    }
    
  }
  
}

public void draw() {
  //Draw the default board background color to black
  background(0);
  
  //Display simulation time
  displaySimulationTime();
  
  //Display developer information
  displayDeveloperInfo();
  
  //Reset total infected, for re-counting
  totalInfected = 0;
  //Count infected people
  for(int i = 0; i < totalPopulation; i++){
    //Check if this person is infected
    if(people[i].isInfected() == true){
      //Increase the infected counter by 1
      totalInfected += 1;
      fill(255,0,0);
      textSize(14);
      //Display text above infected person
      text("Infected", people[i].getPosX(), people[i].getPosY() - 10);
    }
  }
  
  //Display the virus stats
  displayStats();
  
  ///////////////////////////////////////////////////////////////////
  //TEST DATA
  ///////////////////////////////////////////////////////////////////
  //infectedPerson.move();
  //infectedPerson.display();
  ////println("PosX: " + infectedPerson.getPosX() + " | PosY: " + infectedPerson.getPosY());
  
  //healthyPerson.move();
  //healthyPerson.display();
  
  ////Get distance of infected and healthy person
  //float distance = dist( infectedPerson.getPosX(), infectedPerson.getPosY(), healthyPerson.getPosX(), healthyPerson.getPosY() );
  //if( distance < infectedPerson.getRadius() + healthyPerson.getRadius() ){
  //    infectedPerson.changeColorRGB(220,20,60);
  //    healthyPerson.changeColorRGB(0,255,0);
  //}
  ///////////////////////////////////////////////////////////////////
  
  //Loop through everyone in the simulation and move them
  for(int i = 0; i < totalPopulation; i++){
    //Checks if it reaches a wall if so then the counter reverses
    people[i].move();
    //Displays the dot represnting the person
    people[i].display();
    
    //Check for collision with another person
    //if(i < totalPopulation - 1){
    //  people[i].collidedWith(people[i+1]);
    //}
    
    //Loop second time to compare people
    //When getting distance [i] is our subject [j] is our target
    for(int j = 1; j < totalPopulation; j++){
      //Check to make sure the subject and target aren't the same
      if( people[i].getId() != people[j].getId() ){
        //Get distance of person a and b
        float distance = dist(people[i].getPosX(), people[i].getPosY(), people[j].getPosX(), people[j].getPosY());
        //Assume getRadius = 5 + 5 + 10 = 20 feet or let's assume this is the safety distance provided by the HSE
        if( distance < (people[i].getRadius() + people[j].getRadius() + 10) ){
          //Check if this person is infected
          if(people[i].isInfected()){
            //Change the person's color to red
            people[j].changeColorRGB(255,0,0);
            //Update the eprson's health to infected
            //For second parameter we can check to see what kind of condition the person affected has based on his/her condition we can assign reasonable condition for this newly infected person and for underlying condition we can pick that randomly
            people[j].updateHealth("infected", "mild");
          }
        }
      }
    }
  }
}


//Display stats
public void displayStats(){
 fill(255,255,255);
 textSize(20);
 text("Coronavirus Simulator", 20, 25);
 
 textSize(14);
 
 text("Total Population: " + totalPopulation, 20, 55);
 
 text("Total Infected: ", 20, 70);
 fill(255,0,0);
 text(totalInfected, 130, 70);
 
 fill(255,255,255);
 text("Total Cured: " + totalCured, 20, 85);
}


//Display developer information
public void displayDeveloperInfo(){
  fill(255,255,255);
  textSize(14);
  text("Developed by: Nazmul Hasan", 20, height - 120);
  text("GitHub: https://github.com/mull-codes", 20, height - 100);
}


//Simulation timer
public void displaySimulationTime(){
  if(!stopped) {
    mill=(millis()-timerStart);
    if(continued) mill += offset;
    
    seconds = mill / 1000;
    minutes = seconds / 60;
    seconds = seconds % 60;
    hundredths = mill / 10 % 100;
  }
  
  fill(255,255,255);
  textSize(20);
  text(nf(minutes,2,0)+":"+nf(seconds,2,0)+":"+nf(hundredths,2,0), 350,25);
}


//Generate random within the border
public float newRandom(float rand, String type){
  float result = rand;
  if(type == "x"){
    while(result > width) {
      result = random((0+radius), (width - radius));
    }
  }else if(type == "y"){
    while(result > height) {
      result = random((0+radius), (height - radius));
    }
  }
  return result;
}




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
  
  
  public void changeColorRGB(int newColorR, int newColorG, int newColorB){
    this.colorR = newColorR;
    this.colorG = newColorG;
    this.colorB = newColorB;
  }
  
  public void changeColor(int newColor){
    this.colorR = newColor;
    this.colorG = newColor;
    this.colorB = newColor;
  }
 
  
  public void move(){
    //this.pos_x += this.x_speed;
    //this.pos_y += this.y_speed;
    //if (this.pos_x > width - this.radius || this.pos_x < this.radius) {
    //  this.x_speed = -this.x_speed;
    //}
    //if (this.pos_y > height - this.radius || this.pos_y < this.radius) {
    //  this.y_speed = -this.y_speed;
    //}
    
    //Update the position of the shape
    this.pos_x = this.pos_x + ( this.x_speed * (xdirection + random(-1.1f,1.1f)) );
    this.pos_y = this.pos_y + ( this.y_speed * (ydirection + random(-1.1f,1.1f)) );
    
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
  
  public boolean isNearAnotherPerson(Person p){
    boolean isNear = false;
    if( dist(this.pos_x, this.pos_y, p.getPosX(), p.getPosY()) > this.radius + p.getRadius() ){
      isNear = true;
    }
    return isNear;
  }
  
  //Check if person is healthy
  public boolean isHealthy(){
    boolean healthy = false;
     if(this.condition == "healthy"){
         healthy = true;
     }
     
     return healthy;
  }
  
  //Check if person is healthy
  public boolean isInfected(){
    boolean infected = false;
     if(this.condition == "infected"){
         infected = true;
     }
     
     return infected;
  }
  
  public void display(){
    fill(this.colorR, this.colorG, this.colorB);
    ellipse(this.pos_x, this.pos_y, this.radius*2, this.radius*2);
  }
  
  //Updates
  public void updateHealth(String condition, String condType, boolean undCond) {
   this.condition = condition;
   this.conditiontype = condType;
   this.hasUnderlyingCondition = undCond;
  }
  public void updateHealth(String condition, String condType) {
   this.condition = condition;
   this.conditiontype = condType;
  }
  
  //Update on cured
  public void updateCured(){
    this.condition = "healthy";
    this.isCured = true;
  }
  
  
  
  ////////////////////////////////////////////////////
  //Setters
  ////////////////////////////////////////////////////
  //Set id
  public void setId(int id) {
    this.id = id;
  }
  
  //Set condition
  public void setCondition(String condition){
   this.condition = condition; 
  }
  
  public void setConditionType (String condType) {
    this.conditiontype = condType;
  }
  
  public void setUnderLyingCondition(boolean undCond) {
    this.hasUnderlyingCondition = undCond;
  }
  
  //Set age
  public void setAge(int age){
    this.age = age;
  }
  
  //Set pos_x
  public void setPosX(float x) {
    this.pos_x = x;
  }
  
  //Set pos_y
  public void setPosY(float y) {
    this.pos_y = y;
  }
  
  //Set radius
  public void setRadius(float r) {
    this.radius = r;
  }
  
  //Set x_speed
  public void setXSpeed(float speed) {
    this.x_speed = speed;
  }
  
  //Set y_speed
  public void setYSpeed(float speed) {
    this.y_speed = speed;
  }
  
  
  ////////////////////////////////////////////////////
  //Getters
  ////////////////////////////////////////////////////
  //Get condition
  public String getCondition(){
   return this.condition;
  }
  
  public String getConditionType() {
    return this.conditiontype;
  }
  
  public boolean getUnderLyingCondition() {
    return this.hasUnderlyingCondition;
  }
  
  //Get age
  public int getAge(){
    return this.age;
  }
  
  //Get pos_x
  public float getPosX() {
    return this.pos_x;
  }
  
  //Get pos_y
  public float getPosY() {
    return this.pos_y;
  }
  
  //Get radius
  public float getRadius() {
    return this.radius;
  }
  
  //Get x_speed
  public float getXSpeed() {
    return this.x_speed;
  }
  
  //Get y_speed
  public float getYSpeed() {
    return this.y_speed;
  }
  
  public int getId(){
    return this.id;
  }
  
}
  public void settings() {  fullScreen(); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "Coronavirus" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
