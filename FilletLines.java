/*
* Class that implements a special case Fillet 2 lines command
* Flat Line to Angled Line _/ 
*/
package auto;

public class FilletLines extends dxf12objects {

  public double absFlatXstr = 0;
  public double absFlatYstr = 0;
  public double incLineMeetX = 0; 
  public double incLineMeetY = 0; 

  public double incAngXend = 0; 
  public double incAngYend = 0; 
  
  public double radius = 3;
  public String dir = "L";
  
  /**
   * @return dxf - DXF snipit of 2 Lines & Arc
   */
  public String fillet(){
  /* Deal with Incremental VALUES
   * Fillet 2 Lines with an Arc  
   */ 
      if(dir == "R") { // Right Side
          this.Xaxis = -1;
      }
    double rAry[] = {0, 0, 0, 0};
    double ctrX = 0;
    double ctrY = 0;
      dxf = ""; // ensure zero vales
      rAry = this.findFilletPoints(incAngXend, incAngYend, radius);
      double flatXend = incLineMeetX - rAry[3];
    double flatYend = incLineMeetY;       
      double angXstr = rAry[1] *  this.Xaxis; // incLineMeetX + (rAry[1] - rAry[3]);
      double angYstr = 0;
      if(dir == "R") {
        angYstr = (radius + rAry[0]) * this.Yaxis; // DONT KNOW WHY! DONT UNDERSTAND          
      } else {
        angYstr = (radius - rAry[0]) * this.Yaxis;  // incLineMeetY + (radius - rAry[0]);   
      }
      this.xabs = absFlatXstr;
      this.yabs = absFlatYstr;
    this.Line(flatXend, flatYend, "CUT"); // ******* Flat Section Slot Line *****************
      ctrX = this.xabs;
          ctrY = this.yabs + (radius * this.Yaxis);                   
          
      if(dir == "R") { // Right Side
            if(this.Yaxis == -1) { // Flap ARC DOWN
                this.arc2(ctrX, ctrY, radius, 90, 180+rAry[2], angXstr, angYstr, "CUT");              
            } else { // Flap / Arc UP
                this.arc2(ctrX, ctrY, radius, 180-rAry[2], 270, angXstr, angYstr, "CUT");             
            }  
      } else { // Left Side
            if(this.Yaxis == -1) { // Flap ARC DOWN
                this.arc2(ctrX, ctrY, radius, 0+rAry[2], 90, angXstr, angYstr, "CUT");  
            } else { // Flap / Arc UP
                this.arc2(ctrX, ctrY, radius, 270, 360-rAry[2], angXstr, angYstr, "CUT"); // Angles should be 270 & 360-rAry[2] not 0
            }  
      } // Which Side

        //  System.out.println(dir + this.Yaxis + " " + rAry[0] + " " + rAry[1] + " " + rAry[2] + " " + rAry[3]); 
          if(dir == "R") {
            incAngXend = incAngXend + rAry[1] - rAry[3];
            incAngYend = incAngYend - (radius + rAry[0]);  // DONT KNOW WHY! DONT UNDERSTAND 
          } else {
            incAngXend = incAngXend - rAry[1] + rAry[3];
            incAngYend = incAngYend - (radius - rAry[0]);
          }
      this.Line(incAngXend *  this.Xaxis, incAngYend, "CUT"); // ******* Angled Line regen *****************    
      return dxf;     
    } // fillet
    

  /**
  * 
  * @param angLineX 
  * @param angLineY
  * @param rad Radius
  * @return 0 - Op(X), 1 - Adj(Y), 2 - Angle of Arc hit point, 3 - Flat side cut-back 
  */
  public double[] findFilletPoints(double angLineX, double angLineY, double rad) {
    /* Finds Fillet points of an Arc on angled line, when filleting 2 lines
     * Incremental movements expected
    * TG - 26/08/2016
    */
    double rtnAry[] = {0, 0, 0, 0};
    
    double lineAngle = Math.toDegrees(Math.atan2(angLineX, angLineY)); 
    rtnAry[0] = rad * Math.sin(Math.toRadians(lineAngle)); // Opposite (X)
    rtnAry[1] = rad * Math.cos(Math.toRadians(lineAngle)); // Adjacent (Y)
    rtnAry[2] = lineAngle; // Angle of Line / Angle of Arc hit point () 
    if (lineAngle > 45) {
      rtnAry[0] = rad - rtnAry[0];
      // Adjacent = IS OK
    }       
    rtnAry[3] = rad * Math.tan(Math.toRadians( (90 - (rtnAry[2] * this.Xaxis )) / 2)); // Flat side cut-back

    return rtnAry;
  } // findFilletPoints   


  public double findBendXonArc(double bofst, double rad) {
      /* We would know the Y offset of a bend in an offset bend style flap
      * We need the X value from the start of the Slot Arc to where the bend
      * Hits the Slot Arc
      */
      double oppst = rad - bofst; 
      double ang = oppst / rad;      
      ang = (90 - Math.toDegrees(Math.asin(ang)) ) / 2; // We know the Top triangle, we want the Btm. Since it's an equilateral /2 
      
      return bofst / Math.tan(Math.toRadians(ang));
  } // 
  
} // class
